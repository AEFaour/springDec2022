package biblio.repository;

import biblio.entity.Adherent;
import biblio.entity.Emprunt;
import biblio.entity.Livre;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RunWith(SpringRunner.class)
//@ContextConfiguration(classes=SpringConfigTestRepository.class)
//@Transactional
@DataJpaTest
@ActiveProfiles("testJPA")
public class TestEmpruntRepository {

	@Autowired AdherentRepository adherentRepo; 
	@Autowired LivreRepository livreRepo;
	@Autowired EmpruntRepository empruntRepo; 
	
	Livre l1;
	Livre l2;
	Livre l3;
	
	Adherent ad1;
	Adherent ad2;
	Emprunt e1;
	
	@Before
	public void saveSomeLivresAndAdherents() {
	
		l1 = livreRepo.save(new Livre("L'étranger",1942, "Albert Camus"));
		l2 = livreRepo.save(new Livre("Tintin au Tibet",1952, "Hergé"));
		l3 = livreRepo.save(new Livre("Réglez-lui son compte !",1949, "Frédéric Dard"));
		
		ad1 = adherentRepo.save(new Adherent("Durant", "Pascal", "0240563412", "pascal.durant@free.fr"));
		ad2 = adherentRepo.save(new Adherent("Martin", "Jean", "0240992345", "jean.martin@laposte.fr"));
		
		e1 = empruntRepo.save(new Emprunt(l1,ad1));
		empruntRepo.save(new Emprunt(l2, ad1));
		empruntRepo.save(new Emprunt(l3, ad2));
	}
	
	
	@Test
	public  void saveUpdateDeleteTest() {
			
		Assert.assertEquals(3, empruntRepo.findAll().size());
		Assert.assertEquals(1, empruntRepo.getAllByLivre(l2.getId()).size());
		Assert.assertEquals(2, empruntRepo.getAllByAdherent(ad1.getId()).size());
		Assert.assertEquals(1, empruntRepo.getAllByAdherent(ad2.getId()).size());
		
		empruntRepo.delete(empruntRepo.findById(e1.getId()).orElse(null));
		Assert.assertEquals(2, empruntRepo.findAll().size());
		Assert.assertEquals(0, empruntRepo.getAllByLivre(l1.getId()).size());
		Assert.assertEquals(1, empruntRepo.getAllByAdherent(ad1.getId()).size());
	}
	
	@Test
	public  void otherMethodsTest() {
		
		Assert.assertTrue(empruntRepo.estEmprunte(l3.getId()));
		empruntRepo.deleteEmpruntsByAdherent(ad2.getId());
		Assert.assertFalse(empruntRepo.estEmprunte(l3.getId()));

		Assert.assertEquals(e1, empruntRepo.getEmpruntEnCoursByLivre(l1.getId())); 
		e1.setFin(new Date());
		empruntRepo.save(e1);
		Assert.assertNull(empruntRepo.getEmpruntEnCoursByLivre(l1.getId()));
		empruntRepo.save(new Emprunt(l1,ad2));
		Assert.assertEquals(2, empruntRepo.getAllByLivre(l1.getId()).size());
		Assert.assertEquals(1, empruntRepo.getEmpruntsEnCoursByAdherent(ad2.getId()).size());
		Assert.assertEquals(1, empruntRepo.getEmpruntsEnCoursByAdherent(ad1.getId()).size());
	
	}
	
}
