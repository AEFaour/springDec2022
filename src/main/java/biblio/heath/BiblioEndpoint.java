package biblio.heath;


import biblio.service.Bibliotheque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "biblio")
public class BiblioEndpoint {

    @Autowired
    Bibliotheque biblio;

    @ReadOperation
    public Integer getMaxLivreIdentique(@Selector String property) {
        if(property.equals("maxLivreIdentique"))
            return biblio.getMaxLivreIdentique();
        return biblio.getMaxEmpruntAdherent();
    }

    @WriteOperation
    public void setBiblioProperty( @Selector String property, Integer value ) { //soit maxLivreIdentique soit maxEmpruntAdherent
        if(property.equals("maxLivreIdentique"))
            biblio.setMaxLivreIdentique(value);
        else
            biblio.setMaxEmpruntAdherent(value);
    }
}
