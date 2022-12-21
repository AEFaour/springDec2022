package biblio.config;

import biblio.entity.Adherent;
import biblio.entity.Emprunt;
import biblio.entity.Livre;
import biblio.repository.AdherentRepository;
import biblio.repository.EmpruntRepository;
import biblio.repository.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource(value="classpath:/spring/biblio.properties")
public class BiblioConfiguration {

    //@Autowired
    //LivreRepository livreRepo;

    @Bean
    @Profile("dev")
    public String initDB( LivreRepository livreRepo, AdherentRepository adherentRepo, EmpruntRepository empruntRepo) {

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

        Livre l3 = livreRepo.save(new Livre("Réglez-lui son compte !",1949, "Frédéric Dard"));
        livreRepo.save(new Livre("Tintin au Tibet",1960, "Hergé"));

        empruntRepo.save(new Emprunt(l1,ad1));
        empruntRepo.save(new Emprunt(l3,ad1));
        empruntRepo.save(new Emprunt(l2,ad2));
        return "done";
    }
}
