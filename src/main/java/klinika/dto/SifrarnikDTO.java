package klinika.dto;

import java.util.ArrayList;
import java.util.List;

import klinika.model.Sifrarnik;
import klinika.model.StavkaSifrarnika;

public class SifrarnikDTO {

	private Long id;
	private String naziv;
	private String tipSifrarnika;
	private List<StavkaSifrarnikaDTO> stavke;

	public SifrarnikDTO() {

	}

	public SifrarnikDTO(Sifrarnik s) {
		this.id = s.getId();
		this.naziv = s.getNaziv();
		this.tipSifrarnika = s.getTipSifrarnika();
		this.stavke = new ArrayList<>();
		for (StavkaSifrarnika ss : s.getStavke()) {
			this.stavke.add(new StavkaSifrarnikaDTO(ss));
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getTipSifrarnika() {
		return tipSifrarnika;
	}

	public void setTipSifrarnika(String tipSifrarnika) {
		this.tipSifrarnika = tipSifrarnika;
	}

	public List<StavkaSifrarnikaDTO> getStavke() {
		return stavke;
	}

	public void setStavke(List<StavkaSifrarnikaDTO> stavke) {
		this.stavke = stavke;
	}
}
