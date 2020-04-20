package tim09.klinika.dto;

import java.util.ArrayList;

import tim09.klinika.model.TipPregleda;

public class TipPregledaDTO {

	private Long id;
	private String naziv;
	private String opis;
	private StavkaCenovnikaDTO stavkaCenovnika;
	private KlinikaDTO klinika;
	private ArrayList<LekarDTO> lekari;
	private ArrayList<PregledDTO> pregledi;

	public TipPregledaDTO() {

	}

	public TipPregledaDTO(TipPregleda os) {
		// TODO Auto-generated constructor stub
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

	public StavkaCenovnikaDTO getStavkaCenovnika() {
		return stavkaCenovnika;
	}

	public void setStavkaCenovnika(StavkaCenovnikaDTO stavkaCenovnika) {
		this.stavkaCenovnika = stavkaCenovnika;
	}

	public KlinikaDTO getKlinika() {
		return klinika;
	}

	public void setKlinika(KlinikaDTO klinika) {
		this.klinika = klinika;
	}

	public ArrayList<LekarDTO> getLekari() {
		return lekari;
	}

	public void setLekari(ArrayList<LekarDTO> lekari) {
		this.lekari = lekari;
	}

	public ArrayList<PregledDTO> getPregledi() {
		return pregledi;
	}

	public void setPregledi(ArrayList<PregledDTO> pregledi) {
		this.pregledi = pregledi;
	}

}
