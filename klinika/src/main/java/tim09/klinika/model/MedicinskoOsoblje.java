package tim09.klinika.model;

import java.util.Set;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class MedicinskoOsoblje extends Korisnik {

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Klinika klinika;
	
	@OneToMany(mappedBy = "podnosilac", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Odsustvo> odsustva;
	
	@Column(name="pocetakRadnogVremena",nullable = false)
	private Date pocetakRadnogVremena;
	
	@Column(name="krajRadnogVremena",nullable = false)
	private Date krajRadnogVremena;

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}
	
	public Date getPocetakRadnogVremena() {
		return pocetakRadnogVremena;
	}

	public void setPocetakRadnogVremena(Date pocetakRadnogVremena) {
		this.pocetakRadnogVremena = pocetakRadnogVremena;
	}

	public Date getKrajRadnogVremena() {
		return krajRadnogVremena;
	}

	public void setKrajRadnogVremena(Date krajRadnogVremena) {
		this.krajRadnogVremena = krajRadnogVremena;
	}

	public MedicinskoOsoblje() {

	}

	public Set<Odsustvo> getOdsustva() {
		return odsustva;
	}

	public void setOdsustva(Set<Odsustvo> odsustva) {
		this.odsustva = odsustva;
	}
	
	
}
