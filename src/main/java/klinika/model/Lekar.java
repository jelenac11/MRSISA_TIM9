package klinika.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import javax.persistence.JoinColumn;

@Entity
@DiscriminatorValue("LE")
public class Lekar extends MedicinskoOsoblje {

	private static final long serialVersionUID = 1L;

	@ManyToMany
	@JoinTable(name = "specijalizovan", joinColumns = {
			@JoinColumn(name = "lekar_id", referencedColumnName = "korisnik_id") }, inverseJoinColumns = {
					@JoinColumn(name = "tipPregleda_id", referencedColumnName = "tipPregleda_id") })
	private Set<TipPregleda> specijalnosti;

	@ManyToMany(mappedBy = "lekari")
	private Set<Operacija> operacije;

	@OneToMany(mappedBy = "lekar", cascade = CascadeType.ALL)
	private Set<Pregled> pregledi;

	@OneToMany(mappedBy = "lekar", cascade = CascadeType.ALL)
	private Set<OcenaLekara> ocene;

	@Column(name = "aktivan")
	private boolean aktivan;

	@Column(name = "prosecna_ocena")
	private double prosecnaOcena;

	public Lekar() {
		super();
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

	public Set<OcenaLekara> getOcene() {
		return ocene;
	}

	public void setOcene(Set<OcenaLekara> ocene) {
		this.ocene = ocene;
	}

	public Set<Pregled> getPregledi() {
		return pregledi;
	}

	public void setPregledi(Set<Pregled> pregledi) {
		this.pregledi = pregledi;
	}

	public boolean isAktivan() {
		return aktivan;
	}

	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}

	public double getProsecnaOcena() {
		return this.prosecnaOcena;
	}

	public void setProsecnaOcena(double prosecnaOcena) {
		this.prosecnaOcena = prosecnaOcena;
	}

}
