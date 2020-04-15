package tim09.klinika.dto;

public class TipPregledaDTO {

	private Long id;
	private String naziv;
	private String opis;
	private StavkaCenovnikaDTO stavkaCenovnika;

	public TipPregledaDTO() {

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