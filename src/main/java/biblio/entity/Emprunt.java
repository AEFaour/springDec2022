package biblio.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Emprunt {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_EMPRUNT")
	Integer id; 
	
	@Temporal( TemporalType.DATE)
	Date debut;
	
	@Temporal( TemporalType.DATE)
	Date fin = null;
	
	//uni-directional many-to-one association to Livre
    @ManyToOne
	@JoinColumn(name="LIVRE_ID")
	Livre livre;
	
	//uni-directional many-to-one association to Adherent
    @ManyToOne
	@JoinColumn(name="ADHERENT_ID")
	Adherent adherent;
	
	public Emprunt(Livre livre, Adherent adherent, Date debut, Date fin) {
		this.livre = livre;
		this.adherent = adherent;
		this.debut = debut;
		this.fin = fin;
	}	
	
	public Emprunt(Livre livre, Adherent adherent) {
		this.livre = livre;
		this.adherent = adherent;
		this.debut = new Date();
	}
	
	public Emprunt() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDebut() {
		return debut;
	}

	public void setDebut(Date debut) {
		this.debut = debut;
	}

	public Date getFin() {
		return fin;
	}

	public void setFin(Date fin) {
		this.fin = fin;
	}

	public Livre getLivre() {
		return livre;
	}

	public void setLivre(Livre livre) {
		this.livre = livre;
	}

	public Adherent getAdherent() {
		return adherent;
	}

	public void setAdherent(Adherent adherent) {
		this.adherent = adherent;
	}

	@Override
	public String toString() {
		return "id=" + id + ", debut=" + debut + ", fin=" + fin 
				+ ", livre=" + getLivre().titre + ", adherent=" + getAdherent().getNom();
	}
	
	
}
