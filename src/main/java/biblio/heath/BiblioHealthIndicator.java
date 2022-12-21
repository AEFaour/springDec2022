package biblio.heath;

import biblio.service.Bibliotheque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component("biblioHealthIndicator")
public class BiblioHealthIndicator implements HealthIndicator {

    @Autowired
    Bibliotheque biblio;

    @Override
    public Health health() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("maxLivreIdentique", biblio.getMaxLivreIdentique());
        map.put("maxEmpruntAdherent", biblio.getMaxEmpruntAdherent());
        map.put("nbAdherent", biblio.getAdherentRepository().findAll().size());
        map.put("nbLivre", biblio.getLivreRepository().findAll().size());
        return Health.status("ok").withDetails(map).build();
    }
}
