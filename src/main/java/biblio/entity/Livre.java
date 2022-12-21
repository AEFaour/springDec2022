package biblio.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Livre {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_LIVRE")
	Integer id; 
	@NotNull
	@Length(min = 2,max = 50)
	String titre;
	@Range(min = 1900, max = 2020)
	int parution;
	@NotNull
	@Length(min = 2,max = 20)
	String auteur;
	//constructeurs + getter et setter + hashCode et equals

	
	public Livre(String titre, int parution, String auteur) {
		this.titre = titre;
		this.parution = parution;
		this.auteur = auteur;
	}
	
	public Livre() {}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}


	public int getParution() {
		return parution;
	}

	public void setParution(int parution) {
		this.parution = parution;
	}

	public String getAuteur() {
		return auteur;
	}
	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}
	@Override
	public String toString() {
		return "id="+ id +" titre=" + titre + " auteur=" + auteur + " anneeParution=" + parution;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((auteur == null) ? 0 : auteur.hashCode());
		result = prime * result + ((titre == null) ? 0 : titre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Livre other = (Livre) obj;
		if (auteur == null) {
			if (other.auteur != null)
				return false;
		} else if (!auteur.equals(other.auteur))
			return false;
		if (titre == null) {
			if (other.titre != null)
				return false;
		} else if (!titre.equals(other.titre))
			return false;
		return true;
	}
	
}
