package biblio.repository;

import biblio.entity.Livre;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.AfterTransaction;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
//@ContextConfiguration(classes=SpringConfigTestRepository.class)
//@Transactional
@DataJpaTest
@ActiveProfiles("testJPA")
public class TestLivreRepository {
	
	@Autowired LivreRepository repository;
	
	@AfterTransaction
	public void after() {
		Assert.assertEquals(0, repository.findAll().size());
	}
	
	@Test
	public void saveUpdateDeleteTest() {
		Livre livre   = new Livre("Stupeur et tremblements",1999, "Amééélie Nothomb");
		livre = repository.save(livre);
		livre = repository.findById(livre.getId()).orElse(null);
		livre.setAuteur("Amélie Nothomb");
		repository.saveAndFlush(livre);
		Assert.assertEquals("Amélie Nothomb", repository.findById(livre.getId()).orElse(null).getAuteur());

		//repository.deleteAll(repository.findAll());
	}
	
	@Test
	public  void getCountLivreIdentiqueTest() {
		
		repository.save(new Livre("Stupeur et tremblements",1999, "Amélie Nothomb"));
		repository.save(new Livre("L'étranger",1942, "Albert Camus"));
		repository.save(new Livre("Réglez-lui son compte !",1949, "Frédéric Dard"));
		repository.save(new Livre("Tintin au Tibet",1960, "Hergé"));
		repository.save(new Livre("Tintin au Tibet",1960, "Hergé"));
		
		
		Assert.assertEquals(5, repository.count());
		Assert.assertEquals(1, repository.getCountLivreIdentique(new Livre("Stupeur et tremblements",1999, "Amélie Nothomb")));
		Assert.assertEquals(2, repository.getCountLivreIdentique(new Livre("Tintin au Tibet",1960, "Hergé")));

		//repository.deleteAll(repository.findAll());
	}
	
	@Test
	public void getLivreFromRangeParutionTest() {
		repository.save(new Livre("Stupeur et tremblements",1999, "Amélie Nothomb"));
		repository.save(new Livre("L'étranger",1942, "Albert Camus"));
		repository.save(new Livre("Réglez-lui son compte !",1949, "Frédéric Dard"));
		repository.save(new Livre("Tintin au Tibet",1960, "Hergé"));
		repository.save(new Livre("Tintin au Tibet",1960, "Hergé"));
		
		Assert.assertEquals(2,repository.getLivreFromRangeParution(1940, 1950).size());
		//repository.deleteAll(repository.findAll());
	}
}
