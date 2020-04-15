package tim09.klinika.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import javax.persistence.JoinColumn;

@Entity
public class Lekar extends MedicinskoOsoblje {
	
	@ManyToMany
	@JoinTable(
	  name = "specijalizovan", 
	  joinColumns = @JoinColumn(name = "lekar_id",referencedColumnName = "id"), 
	  inverseJoinColumns = @JoinColumn(name = "tipPregleda_id", referencedColumnName = "id"))
	private Set<TipPregleda> specijalnosti;
	
	@OneToMany(mappedBy = "lekar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Operacija> operacije;
	
	
	@OneToMany(mappedBy = "lekar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Pregled> pregledi;

	public Lekar() {

	}

	public Set<TipPregleda> getSpecijalnosti() {
		return specijalnosti;
	}

	public void setSpecijalnosti(Set<TipPregleda> specijalnosti) {
		this.specijalnosti = specijalnosti;
	}

	public Set<Operacija> getOperacije() {
		return operacije;
	}

	public void setOperacije(Set<Operacija> operacije) {
		this.operacije = operacije;
	}

	public Set<Pregled> getPregledi() {
		return pregledi;
	}

	public void setPregledi(Set<Pregled> pregledi) {
		this.pregledi = pregledi;
	}

}
