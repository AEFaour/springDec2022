package biblio.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import biblio.entity.Livre;
import biblio.repository.LivreRepository;
import biblio.service.Bibliotheque;

@WebMvcTest(value=LivreRestController.class,
        excludeAutoConfiguration = {
        SecurityAutoConfiguration.class,
        ManagementWebSecurityAutoConfiguration.class})  //idem @DataJpaTest
@ActiveProfiles("TestController")
public class LivreRestControllerTestUnitaire {
	

	@Autowired  private MockMvc mockMvc;
    private Livre livre;

    @MockBean private Bibliotheque biblio;
    @MockBean private LivreRepository livreRepo;
  
    @Autowired ObjectMapper mapper;
    

    @BeforeEach
    public  void setup() throws Exception {
    	livre = new Livre("Stupeur et tremblements",1999, "Amélie Nothomb");
        livre.setId(1);
    }
    
    @Test
    public void getLivreTest() throws Exception {
    	when(biblio.getLivreRepository()).thenReturn(livreRepo);
    	when(livreRepo.findById(1)).thenReturn(Optional.of(livre));
    	
    	mockMvc.perform(get("/rest/livres/{id}", livre.getId())
    			.accept(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(livre.getId())))
                .andExpect(jsonPath("$.auteur", is(livre.getAuteur())));
    	mockMvc.perform(get("/rest/livres/"+ livre.getId()*10))
        	   	.andExpect(status().isNotFound());            
    }
    // ... autres méthodes de test ...
    
    @Test
    public void createLivreTest() throws Exception {
    	when(biblio.ajouterLivre(livre)).thenReturn(1);

    	String livreJson = mapper.writeValueAsString(new Livre());
    	mockMvc.perform(post("/rest/livres")
    				.contentType(MediaType.APPLICATION_JSON)
    				.accept(MediaType.APPLICATION_JSON_VALUE)
    				.content(livreJson))
    			.andDo(print())
                .andExpect(status().isBadRequest());

    	livreJson = mapper.writeValueAsString(livre);

    	mockMvc.perform(post("/rest/livres")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(livreJson))
			.andDo(print())
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", "http://localhost/rest/livres/1"))
            .andExpect(jsonPath("$.id", is(livre.getId())))
            .andExpect(jsonPath("$.auteur", is(livre.getAuteur())));



    }
}






