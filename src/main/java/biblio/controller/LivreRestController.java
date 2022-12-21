package biblio.controller;

import biblio.entity.Livre;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/rest/livres")
@CrossOrigin(maxAge = 3600)
public class LivreRestController extends BaseRestController {
		
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Livre>> getLivreAll() {
		return new ResponseEntity<List<Livre>>(
				biblio.getLivreRepository().findAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces=MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<?> getLivre( @PathVariable Integer id) {
		Livre l  = biblio.getLivreRepository().findById(id).orElse(null);
		return l != null ?
				new ResponseEntity<Livre>(l, HttpStatus.OK)
				:new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, 
			     produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<?> createLivre(@RequestBody @Valid Livre livre, Errors errors) { 
		//If error, just return a 400 bad request, along with the error message
		if (errors.hasErrors()) {
			return validationNotOk(errors);
		}
		int id = biblio.ajouterLivre(livre);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(id).toUri());
		return new ResponseEntity<>(livre, httpHeaders, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, 
			                     produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<?> updateLivre(@PathVariable Integer id, @RequestBody @Valid Livre livre, Errors errors) {
		//If error, just return a 400 bad request, along with the error message
		if (errors.hasErrors()) {
			return validationNotOk(errors);
		}
		biblio.modifierLivre(livre);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteLivre(@PathVariable Integer id) {
			if(!biblio.getLivreRepository().existsById(id))
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			biblio.retirerLivre(id);
			return  new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
