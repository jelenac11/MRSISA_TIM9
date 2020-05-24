package klinika.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class StavkaSifrarnika implements Serializable {

	private static final long serialVersionUID = -1681780736441266590L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "stavkaSifrarnika_id")
	private Long id;

	@Column(name = "sifra", unique = true, nullable = false)
	private String sifra;

	@Column(name = "naziv", nullable = false)
	private String naziv;

	@Column(name = "tipSifre", nullable = false)
	private String tipSifre;

	@OneToMany(mappedBy = "dijagnoza", cascade = CascadeType.ALL)
	private Set<Izvestaj> izvestaji;

	@OneToMany(mappedBy = "lek", cascade = CascadeType.ALL)
	private Set<Recept> recepti;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sifrarnik_id")
	private Sifrarnik sifrarnik;

	public StavkaSifrarnika() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getTipSifre() {
		return tipSifre;
	}

	public void setTipSifre(String tipSifre) {
		this.tipSifre = tipSifre;
	}

	public Set<Izvestaj> getIzvestaji() {
		return izvestaji;
	}

	public void setIzvestaji(Set<Izvestaj> izvestaji) {
		this.izvestaji = izvestaji;
	}

	public Set<Recept> getRecepti() {
		return recepti;
	}

	public void setRecepti(Set<Recept> recepti) {
		this.recepti = recepti;
	}

	public Sifrarnik getSifrarnik() {
		return sifrarnik;
	}

	public void setSifrarnik(Sifrarnik sifrarnik) {
		this.sifrarnik = sifrarnik;
	}
}
