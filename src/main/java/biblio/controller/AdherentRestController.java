package biblio.controller;


import biblio.entity.Adherent;
import biblio.entity.Emprunt;
import biblio.entity.Livre;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/rest/adherents")
@CrossOrigin(maxAge = 3600)
public class AdherentRestController extends BaseRestController {
	
	
	///rest/adherents/2
	//CRUD 
	@GetMapping(value="/{id}") //httpMessageConverter --> lib Jackson
	public ResponseEntity<?> getAdherent(@PathVariable Integer id ) {
		Optional<Adherent> adherent   =  biblio.getAdherentRepository().findById(id);
		if(!adherent.isPresent())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(adherent.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> createAdherent(@RequestBody Adherent adherent) {
			int id = biblio.ajouterAdherent(adherent);
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setLocation(ServletUriComponentsBuilder
					.fromCurrentRequest().path("/{id}")
					.buildAndExpand(id).toUri());
			return  new ResponseEntity<>(adherent, httpHeaders, HttpStatus.CREATED);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> updateAdherent(@PathVariable Integer id,   @RequestBody Adherent adherent) {
			if(!biblio.getAdherentRepository().existsById(id))
				return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
			biblio.modifierAdherent(adherent);
			return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAdherent(@PathVariable Integer id) {
		biblio.retirerAdherent(id);
		return  new ResponseEntity<>(HttpStatus.OK);
	}


	
	@GetMapping(value="/{id}/short", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ShortAdherent>  getShortAdherent(@PathVariable Integer id ) {
		System.out.println("/adherent/{id}");
		ShortAdherent sa= new ShortAdherent(biblio.getAdherentRepository().findById(id).orElse(null));
		Link link  = WebMvcLinkBuilder.linkTo(AdherentRestController.class).slash(id).withRel("detail");
		sa.add(link);
		sa.add(WebMvcLinkBuilder.linkTo(AdherentRestController.class).slash("hateoas").withRel("list"));
		return new ResponseEntity<>(sa, HttpStatus.OK);
		
	}
	
	
	@GetMapping
	public ResponseEntity<List<Adherent>>  getAdherentAll() {
		System.out.println("/adherent");
		return new ResponseEntity<>(biblio.getAdherentRepository().findAll(), HttpStatus.OK);
	}
	
	@GetMapping(value="/hateoas", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CollectionModel<?>> showAdherentAll() {
		System.out.println("/hateoas");
		List<Adherent> adherents  = biblio.getAdherentRepository().findAll();
		List<EntityModel<Adherent>> models = new ArrayList<EntityModel<Adherent>>();
		for(Adherent a : adherents)
			models.add(EntityModel.of(a, WebMvcLinkBuilder.linkTo(AdherentRestController.class)
					.slash("/hateoas/"+a.getId()).withSelfRel()));
		CollectionModel<?> resources = CollectionModel.of(
				models,
				WebMvcLinkBuilder.linkTo(AdherentRestController.class)
				.slash("hateoas").withSelfRel());
	    return new ResponseEntity<>(resources, HttpStatus.OK);
	}
	
	@GetMapping(value="/hateoas/{id}")  //  /rest/adherents/hateoas/2
	public ResponseEntity<?> showAdherent(@PathVariable Integer id) {
		System.out.println("/adherent/"+id);
		Adherent adherent   =  biblio.getAdherentRepository().findById(id).get();
		if(adherent == null)
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		List<Link> ll = new ArrayList<>();
		for(Emprunt e : biblio.getEmpruntRepository().getAllByAdherent(adherent.getId()))
			ll.add(
					WebMvcLinkBuilder.linkTo(methodOn(LivreRestController.class).getLivre(e.getLivre().getId()))
							.withRel("livres"));
	   EntityModel<Adherent> resource = EntityModel.of(adherent,
			   WebMvcLinkBuilder.linkTo(AdherentRestController.class)
			   .slash("/hateoas/"+id).withSelfRel());
	   resource.add(
			   WebMvcLinkBuilder.linkTo(methodOn(AdherentRestController.class)
			 	.getAdherent(id))
			 	.withRel("adherent"));
	   resource.add(CollectionModel.of(ll));
	   return new ResponseEntity<>(resource, HttpStatus.OK);
	}

	public static class ShortAdherent extends RepresentationModel<ShortAdherent> {
	 
		@JsonProperty
		Integer id;
		@JsonProperty
		String nomPrenom;
	   public ShortAdherent(Adherent adherent) {
		   id = adherent.getId();
		   nomPrenom   = adherent.getNom() + " " + adherent.getPrenom();
	   }
	   public ShortAdherent() { }
	}
}
