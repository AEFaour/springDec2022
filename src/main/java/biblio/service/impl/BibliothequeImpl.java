package biblio.service.impl;

import biblio.aop.Monitorable;
import biblio.entity.Adherent;
import biblio.entity.Emprunt;
import biblio.entity.Livre;
import biblio.repository.AdherentRepository;
import biblio.repository.EmpruntRepository;
import biblio.repository.LivreRepository;
import biblio.service.Bibliotheque;
import biblio.service.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;



//@Component  annotations dérivées 
// @Repository  --> couche persistance ....
// @Service --> couche métier ....
// @Controller --> couche web

//
//@Component

@Service
@Transactional
@Monitorable
public class BibliothequeImpl implements Bibliotheque {
	int maxLivreIdentique;
	int maxEmpruntAdherent;
	
	@Autowired LivreRepository livreRepo; 
	@Autowired AdherentRepository adherentRepo; 
	@Autowired EmpruntRepository empruntRepo; 
	
	@Autowired public BibliothequeImpl(
		@Value("${maxLivreIdentique}") Integer maxLivreIdentique,  
		@Value("${maxEmpruntAdherent}") Integer maxEmpruntAdherent) {
		this.maxLivreIdentique = maxLivreIdentique; 
		this.maxEmpruntAdherent = maxEmpruntAdherent;
	}
	
	@Override
	//@Monitorable
	public int ajouterLivre(Livre livre)  {
		//RG : maxLivreIdentique ?
		if(livreRepo.getCountLivreIdentique(livre) == maxLivreIdentique ) 
			throw new BusinessException("BibliothequeImpl.ajouterLivre", null);
		livreRepo.save(livre);
		return livre.getId();
	}


	@Override
	public int getMaxEmpruntAdherent() { 
		return maxEmpruntAdherent;
	}
	
	@Override
	public int getMaxLivreIdentique() {
		return maxLivreIdentique;
	}
	
	


	@Override
	@Monitorable
	public void retirerLivre(int idLivre) {
		//RG : livre vacant ?
		if(empruntRepo.getEmpruntEnCoursByLivre(idLivre) != null) 
			throw new BusinessException("BibliothequeImpl.retirerLivre", null); 
		//détruire d'abord les anciens emprunts puis le live ....
		empruntRepo.deleteAll(empruntRepo.getAllByLivre(idLivre));
		livreRepo.deleteById(idLivre);
	}

	@Override
	@Monitorable
	public int ajouterAdherent(Adherent adherent) {
		//RG est déjà Present ?
		if(adherentRepo.isPresent(adherent))
			throw new BusinessException("BibliothequeImpl.ajouterAdherent", null); 
		adherent = adherentRepo.save(adherent);
		return adherent.getId();
	}


	@Override
	public void retirerAdherent(int idAdherent) {
		//RG : adherent vacant ?
		if(empruntRepo.getEmpruntsEnCoursByAdherent(idAdherent).size() > 0)
			throw new BusinessException("BibliothequeImpl.retirerAdherent", null); 
		//détruire d'abord les anciens emprunts puis l'adhérent ....
		empruntRepo.deleteAll(empruntRepo.getAllByAdherent(idAdherent));
		adherentRepo.deleteById(idAdherent);	
	}

	@Override
	public void emprunterLivre(int idLivre, int idAdherent) {
		//RG : maxEmpruntAdherent ?
		if( empruntRepo.getEmpruntsEnCoursByAdherent(idAdherent).size() == maxEmpruntAdherent)
			throw new BusinessException("BibliothequeImpl.emprunterLivre", null); 
		//RG : livre déjà emprunté !
		if(empruntRepo.getEmpruntEnCoursByLivre(idLivre) != null)
			throw new BusinessException("BibliothequeImpl.emprunterLivre", null); 

		empruntRepo.save(new Emprunt(livreRepo.findById(idLivre).orElse(null), adherentRepo.findById(idAdherent).orElse(null)));
	}
	
	@Override
	public void restituerLivre(int idLivre, int idAdherent) {
		// RG : un emprunt doit existé avec le couple idLivre/idAdherent
		Emprunt emprunt = empruntRepo.getEmpruntEnCoursByLivre(idLivre);
		if(emprunt == null || emprunt.getAdherent().getId() != idAdherent)
			throw new BusinessException("BibliothequeImpl.restituerLivre", null);  /// A finir ...
		emprunt.setFin(new Date());
		empruntRepo.save(emprunt);
	}
	
	@Override
	public int modifierLivre(Livre livre) {
		if(!livreRepo.findById(livre.getId()).isPresent()) 
			throw new BusinessException("BibliothequeImpl.modifierLivre", null);
		livreRepo.save(livre);
		return livre.getId();
	}

	@Override
	public int modifierAdherent(Adherent adherent) {
		if(!adherentRepo.findById(adherent.getId()).isPresent()) 
			throw new BusinessException("BibliothequeImpl.modifierAdherent", null);
		adherentRepo.save(adherent);
		return adherent.getId();
	}

	@Override
	public void transfererEmprunt(int idAdherentPrecedent, int idLivre,
			int idAdherentSuivant) {
		restituerLivre(idLivre, idAdherentPrecedent);
		emprunterLivre(idLivre, idAdherentSuivant);	
	}

	@Override
	public LivreRepository getLivreRepository() {
		
		// TODO Auto-generated method stub
		return livreRepo;
	}

	@Override
	public AdherentRepository getAdherentRepository() {
		// TODO Auto-generated method stub
		return adherentRepo;
	}

	@Override
	public EmpruntRepository getEmpruntRepository() {
		// TODO Auto-generated method stub
		return empruntRepo;
	}

	@Override
	public void setMaxLivreIdentique( Integer value ) {
		maxLivreIdentique = value;
	}
	@Override
	public void setMaxEmpruntAdherent( Integer value ) {
		maxEmpruntAdherent = value;
	}

	@Override
	public List<Livre> getLivreAll() {
		// TODO Auto-generated method stub
		return livreRepo.findAll();
	}

}
