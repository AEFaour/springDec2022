package biblio.repository;

import biblio.entity.Emprunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface EmpruntRepository extends JpaRepository<Emprunt, Integer> {
	
	@Query("select case when count(e) = 1 then true else false end from Emprunt e "
				+ "where e.livre.id = :idLivre and e.fin = null")
	public boolean estEmprunte(@Param("idLivre") int idLivre);
	
	@Modifying
	@Query("delete from Emprunt e where e.adherent.id = :idAdherent")
	@Transactional
	public void deleteEmpruntsByAdherent(@Param("idAdherent") int idAdherent);
	
	@Query("select e from Emprunt e where e.livre.id = :idLivre and e.fin is null")
	public Emprunt getEmpruntEnCoursByLivre(@Param("idLivre")int idLivre);
	
	
	@Query("select e from Emprunt e join fetch e.livre join fetch e.adherent where e.livre.id = :idLivre")
	public List<Emprunt> getAllByLivre(@Param("idLivre") int idLivre);
	
	@Query("select e from Emprunt e join fetch e.livre join fetch e.adherent where e.adherent.id = :idAdherent")
	public List<Emprunt> getAllByAdherent(@Param("idAdherent") int idAdherent);
	
	@Query("select e from Emprunt e join fetch e.livre join fetch e.adherent "
				+ "where e.adherent.id = :idAdherent and e.fin is null")
	public List<Emprunt> getEmpruntsEnCoursByAdherent(@Param("idAdherent") int idAdherent);
			
			
}
