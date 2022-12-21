package biblio.repository;

import biblio.entity.Adherent;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;



@RunWith(SpringRunner.class)
//@ContextConfiguration(classes=SpringConfigTestRepository.class)
@DataJpaTest  //spécifique pour le test des repositories JPA
@ActiveProfiles("testJPA")
public class TestAdherentRepository {

	@Autowired
	AdherentRepository repository;
	int nbAdherent;
	
	@BeforeTransaction
	public  void before() {
		nbAdherent =  repository.findAll().size();
	}
		
	
	@Test
	@Transactional
	public  void repositoryTest() throws RuntimeException {
		//staticRepository = repository;
		Adherent ad1 = new Adherent("Jean" ,"Dupond", "0234567812", "jean.dupont.@yahoo.fr");
		Adherent ad2 = new Adherent("Jacques", "Durant",  "0223674512", "jacques.durant@free.fr");
		Adherent ad3 = new Adherent("Bernadette", "Martin",  "0138792012", "m.bernadette@gmail.com");
		ad1 = repository.save(ad1);
		ad2 = repository.save(ad2);
		ad3 = repository.save(ad3);
		
		Assert.assertEquals("JeanDupond0234567812", 
				repository.findById(ad1.getId()).orElse(null).getNom() + 
				repository.findById(ad1.getId()).orElse(null).getPrenom() + 
				repository.findById(ad1.getId()).orElse(null).getTel());
		
		repository.deleteById(ad1.getId());
		Assert.assertEquals(nbAdherent + 2, repository.findAll().size());
		Assert.assertFalse(repository.isPresent(new Adherent("Dupond", "Paul", "0234567812", "jean.dupont.@yahoo.fr")));
		Assert.assertTrue(repository.isPresent(new Adherent("Bernadette", "Martin", "0138792012", "m.bernadette@gmail.com")));
		
	}
	
	@AfterTransaction
	public  void after() {
		Assert.assertEquals(nbAdherent, repository.findAll().size());
	}
	
}
