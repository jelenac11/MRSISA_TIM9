package tim09.klinika.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import tim09.klinika.model.TipSifre;

@Entity
public class StavkaSifrarnika {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "sifra",unique = true,nullable = false)
	private String sifra;
	
	@Column(name = "naziv",nullable = false)
	private String naziv;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private TipSifre tipSifre;

	public StavkaSifrarnika() {

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

	public TipSifre getTipSifre() {
		return tipSifre;
	}

	public void setTipSifre(TipSifre tipSifre) {
		this.tipSifre = tipSifre;
	}
}
