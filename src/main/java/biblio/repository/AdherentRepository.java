
package biblio.repository;

import biblio.entity.Adherent;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdherentRepository extends JpaRepository<Adherent, Integer> {
	
	public default boolean isPresent(Adherent adherent) {
		return  exists(Example.of(adherent));
	}

}
