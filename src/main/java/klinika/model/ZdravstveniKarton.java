package klinika.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class ZdravstveniKarton {

	@Id
	@Column(name = "zdravstveniKarton_id")
	private Long id;

	@OneToMany(mappedBy = "zdravstveniKarton", cascade = CascadeType.ALL)
	private Set<Izvestaj> bolesti;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pacijent_id")
	@MapsId
	private Pacijent pacijent;

	@Column(name = "visina")
	private double visina;

	@Column(name = "tezina")
	private double tezina;

	@Column(name = "dioptrija")
	private double dioptrija;

	@Column(name = "krvna_grupa")
	private String krvnaGrupa;

	public ZdravstveniKarton() {

	}

	public ZdravstveniKarton(Pacijent pacijent, double visina, double tezina, double dioptrija, String krvnaGrupa) {
		this.bolesti = new HashSet<>();
		this.pacijent = pacijent;
		this.visina = visina;
		this.tezina = tezina;
		this.dioptrija = dioptrija;
		this.krvnaGrupa = krvnaGrupa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Izvestaj> getBolesti() {
		return bolesti;
	}

	public void setBolesti(Set<Izvestaj> bolesti) {
		this.bolesti = bolesti;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public double getVisina() {
		return visina;
	}

	public void setVisina(double visina) {
		this.visina = visina;
	}

	public double getTezina() {
		return tezina;
	}

	public void setTezina(double tezina) {
		this.tezina = tezina;
	}

	public double getDioptrija() {
		return dioptrija;
	}

	public void setDioptrija(double dioptrija) {
		this.dioptrija = dioptrija;
	}

	public String getKrvnaGrupa() {
		return krvnaGrupa;
	}

	public void setKrvnaGrupa(String krvnaGrupa) {
		this.krvnaGrupa = krvnaGrupa;
	}

}
