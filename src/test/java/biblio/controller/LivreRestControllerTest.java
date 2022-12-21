package biblio.controller;

import biblio.entity.Livre;
import biblio.service.Bibliotheque;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("TestController")
public class LivreRestControllerTest {

    @Autowired private MockMvc mockMvc;
    private Livre livre;

    @Autowired private Bibliotheque biblio;
    @Autowired ObjectMapper mapper;

    @Before
    public  void setup() throws Exception {
        livre = new Livre("Stupeur et tremblements",1999, "Amélie Nothomb");
        biblio.ajouterLivre(livre);
    }
    
    @Test
    public void getLivreTest() throws Exception {
    	mockMvc.perform(get("/rest/livres/{id}", livre.getId())
    			.accept(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(livre.getId())))
                .andExpect(jsonPath("$.auteur", is(livre.getAuteur())));
    	mockMvc.perform(get("/rest/adherents/"+ livre.getId()*10))
        	   	.andExpect(status().isNotFound());            
    }
    // ... autres méthodes de test ...
}






