package biblio.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import biblio.entity.Adherent;



@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("TestController")
public class AdherentRestControllerTestValidation {

    @LocalServerPort
    int port;
    @Autowired TestRestTemplate restTemplate;
    private Adherent adherent;
    @Value("${server.servlet.context-path:}") String contextPath;
    @Value("${spring.mvc.servlet.path:}") String servletPath;
    String url;

    @BeforeEach
    public  void before() throws Exception {
        url="http://localhost:" + port + contextPath + servletPath + "/rest/adherents/";
        adherent = new Adherent("Durant", "Pascal", "0240563412", "pascal.durant@free.fr");
        adherent = restTemplate.postForEntity(url, adherent, Adherent.class).getBody();
        assertThat(restTemplate.getForObject(url, Object[].class).length).isNotNull();
    }
    
    @AfterEach
    public  void after() throws Exception {
    	int nbAdherent = restTemplate.getForObject(url, Object[].class).length;
       restTemplate.delete(url+adherent.getId());
       assertThat(restTemplate.getForObject(url, Object[].class).length).isEqualTo(nbAdherent-1);
    }
    
    @Test
    public void getAdherentTest() throws Exception {
    	adherent = restTemplate.getForObject(url+adherent.getId(), Adherent.class);
    	assertThat(adherent.getNom()).isEqualTo("Durant");
    	assertThat(adherent.getEmail()).isEqualTo("pascal.durant@free.fr");        
    }
    
    @Test
    public void updateAdherentTest() throws Exception {
    	adherent.setNom("Dupont"); 
    	restTemplate.put(url+adherent.getId(), adherent);
    	adherent = restTemplate.getForObject(url+adherent.getId(), Adherent.class);
    	assertThat(adherent.getNom()).isEqualTo("Dupont");    
    } 

}
