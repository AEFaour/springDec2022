package biblio.repository;

import biblio.entity.Livre;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface LivreRepository extends JpaRepository<Livre, Integer> {

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void deleteById( Integer integer );

	@Override
	@RestResource(exported = false)
	void deleteAll();

	public default long getCountLivreIdentique( Livre l) {
		return count(Example.of(l));
	}	
	
	@Query("select l from Livre l where parution >= :min and parution <= :max")
	public List<Livre> getLivreFromRangeParution(
			@Param("min")int min, 
			@Param("max")int max);
}


