package klinika.dto;

import klinika.model.StavkaSifrarnika;

public class StavkaSifrarnikaDTO {

	private Long id;
	private String sifra;
	private String naziv;
	private String tipSifre;

	public StavkaSifrarnikaDTO() {

	}

	public StavkaSifrarnikaDTO(StavkaSifrarnika s) {
		this.id = s.getId();
		this.sifra = s.getSifra();
		this.naziv = s.getNaziv();
		this.tipSifre = s.getTipSifre();
	}

	public String getTipSifre() {
		return tipSifre;
	}

	public void setTipSifre(String tipSifre) {
		this.tipSifre = tipSifre;
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

}
