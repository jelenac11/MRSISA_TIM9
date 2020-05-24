package klinika.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("MO")
public class MedicinskoOsoblje extends Korisnik {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "klinika_id")
	private Klinika klinika;

	@OneToMany(mappedBy = "podnosilac", cascade = CascadeType.ALL)
	private Set<Odsustvo> odsustva;

	@Column(name = "pocetakRadnogVremena")
	private long pocetakRadnogVremena;

	@Column(name = "krajRadnogVremena")
	private long krajRadnogVremena;

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}

	public long getPocetakRadnogVremena() {
		return pocetakRadnogVremena;
	}

	public void setPocetakRadnogVremena(long pocetakRadnogVremena) {
		this.pocetakRadnogVremena = pocetakRadnogVremena;
	}

	public long getKrajRadnogVremena() {
		return krajRadnogVremena;
	}

	public void setKrajRadnogVremena(long krajRadnogVremena) {
		this.krajRadnogVremena = krajRadnogVremena;
	}

	public MedicinskoOsoblje() {
		super();
	}

	public Set<Odsustvo> getOdsustva() {
		return odsustva;
	}

	public void setOdsustva(Set<Odsustvo> odsustva) {
		this.odsustva = odsustva;
	}

}
