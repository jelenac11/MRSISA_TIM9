package klinika.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("PA")
public class Pacijent extends Korisnik {

	private static final long serialVersionUID = 1L;

	@OneToOne(mappedBy = "pacijent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private ZdravstveniKarton karton;

	@OneToMany(mappedBy = "pacijent", cascade = CascadeType.ALL)
	private Set<Pregled> pregledi;

	@OneToMany(mappedBy = "pacijent", cascade = CascadeType.ALL)
	private Set<Operacija> operacije;

	@Column(name = "jbo", unique = true)
	private String jbo;

	public Pacijent() {
		super();
	}

	public ZdravstveniKarton getKarton() {
		return karton;
	}

	public void setKarton(ZdravstveniKarton karton) {
		this.karton = karton;
	}

	public Set<Pregled> getPregledi() {
		return pregledi;
	}

	public void setPregledi(Set<Pregled> pregledi) {
		this.pregledi = pregledi;
	}

	public Set<Operacija> getOperacije() {
		return operacije;
	}

	public void setOperacije(Set<Operacija> operacije) {
		this.operacije = operacije;
	}

	public String getJbo() {
		return jbo;
	}

	public void setJbo(String jbo) {
		this.jbo = jbo;
	}
}
