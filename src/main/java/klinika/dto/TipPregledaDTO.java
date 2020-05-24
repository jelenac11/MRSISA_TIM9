package klinika.dto;

import klinika.model.TipPregleda;

public class TipPregledaDTO {

	private Long id;
	private String naziv;
	private String opis;
	private String klinika;
	private double cena;

	public TipPregledaDTO() {

	}

	public TipPregledaDTO(TipPregleda os) {
		this.id = os.getId();
		this.naziv = os.getNaziv();
		this.opis = os.getOpis();
		this.klinika = os.getKlinika().getNaziv();
		this.cena = os.getStavkaCenovnika().getCena();
	}

	public TipPregledaDTO(long id, String naziv, String opis, double cena) {
		this.id = id;
		this.naziv = naziv;
		this.opis = opis;
		this.cena = cena;
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

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getKlinika() {
		return klinika;
	}

	public void setKlinika(String klinika) {
		this.klinika = klinika;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

}
