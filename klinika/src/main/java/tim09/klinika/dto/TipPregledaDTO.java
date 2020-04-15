package tim09.klinika.dto;

import tim09.klinika.model.TipPregleda;

public class TipPregledaDTO {

	private Long id;
	private String naziv;
	private String opis;
	private StavkaCenovnikaDTO stavkaCenovnika;

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

}
