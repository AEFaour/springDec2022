package biblio;

import biblio.entity.Adherent;
import biblio.entity.Emprunt;
import biblio.entity.Livre;
import biblio.repository.AdherentRepository;
import biblio.repository.EmpruntRepository;
import biblio.repository.LivreRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@SpringBootApplication
//@Configuration   //
@SpringBootConfiguration
@ComponentScan //(excludeFilters = {@org.springframework.context.annotation.ComponentScan.Filter(type = org.springframework.context.annotation.FilterType.CUSTOM, classes = {org.springframework.boot.context.TypeExcludeFilter.class}),@org.springframework.context.annotation.ComponentScan.Filter(type = org.springframework.context.annotation.FilterType.CUSTOM, classes = {org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter.class})})
@EnableAutoConfiguration //(exclude = {
		//SecurityAutoConfiguration.class,
		//ManagementWebSecurityAutoConfiguration.class})  //??

public class BiblioSpringbootApplication {

	public static void main(String[] args) {


		ApplicationContext spring = SpringApplication.run(BiblioSpringbootApplication.class, args);
	/*
	new AnnotationConfigApplicationContext(BiblioSpringbootApplication.class)
		LivreRepository livreRepo = spring.getBean(LivreRepository.class);
        AdherentRepository adherentRepo = spring.getBean(AdherentRepository.class);
        EmpruntRepository empruntRepo = spring.getBean(EmpruntRepository.class);

        empruntRepo.deleteAll(empruntRepo.findAll());
		livreRepo.deleteAll(livreRepo.findAll());
		adherentRepo.deleteAll(adherentRepo.findAll());

		Adherent ad1 = new Adherent("Dupond", "Jean", "0234567812", "jean.dupont.@yahoo.fr");
 		Adherent ad2 = new Adherent("Durant", "Jacques", "0223674512", "jacques.durant@free.fr");
 		Adherent ad3 = new Adherent("Martin", "Bernadette", "0138792012", "m.bernadette@gmail.com");

		ad1= adherentRepo.save(ad1);
		ad2= adherentRepo.save(ad2);
		ad3= adherentRepo.save(ad3);

 		Livre l1 = new Livre("Stupeur et tremblements",1999, "Amélie Nothomb");
 		Livre l2 = new Livre("L'étranger",1942, "Albert Camus");

		 l1= livreRepo.save(l1);
		l2= livreRepo.save(l2);


		livreRepo.save(new Livre("Réglez-lui son compte !",1949, "Frédéric Dard"));
		livreRepo.save(new Livre("Tintin au Tibet",1960, "Hergé"));

 		empruntRepo.save(new Emprunt(l1,ad1));
		empruntRepo.save(new Emprunt(l2,ad2));
		*/
	}

}
