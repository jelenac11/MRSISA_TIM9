package tim09.klinika.dto;

public class OcenaKlinikeDTO extends OcenaDTO {

	private KlinikaDTO klinika;

	public OcenaKlinikeDTO() {

	}

	public OcenaKlinikeDTO(long id, int vrednost, PacijentDTO ocenjivac, KlinikaDTO klinika) {
		super(id, vrednost, ocenjivac);
		this.klinika = klinika;
	}

	public KlinikaDTO getKlinika() {
		return klinika;
	}

	public void setKlinika(KlinikaDTO klinika) {
		this.klinika = klinika;
	}
}
