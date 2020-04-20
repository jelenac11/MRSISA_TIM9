package tim09.klinika.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("PA")
public class Pacijent extends Korisnik {

	@OneToOne(mappedBy = "pacijent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private ZdravstveniKarton karton;

	@OneToMany(mappedBy = "pacijent", cascade = CascadeType.ALL)
	private Set<Pregled> pregledi;

	@OneToMany(mappedBy = "pacijent", cascade = CascadeType.ALL)
	private Set<Operacija> operacije;

	public Pacijent() {

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
}
