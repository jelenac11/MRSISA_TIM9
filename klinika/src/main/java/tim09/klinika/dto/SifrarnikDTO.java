package tim09.klinika.dto;

import tim09.klinika.model.Sifrarnik;

public class SifrarnikDTO {

	private Long id;
	private String naziv;
	private String tipSifrarnika;

	public SifrarnikDTO() {

	}

	public SifrarnikDTO(Sifrarnik s) {
		this.id = s.getId();
		this.naziv = s.getNaziv();
		this.tipSifrarnika = s.getTipSifrarnika();
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
}
