package klinika.dto;

import klinika.model.OcenaKlinike;

public class OcenaKlinikeDTO extends OcenaDTO {

	private KlinikaDTO klinika;

	public OcenaKlinikeDTO() {

	}

	public OcenaKlinikeDTO(long id, int vrednost, PacijentDTO ocenjivac, KlinikaDTO klinika) {
		super(id, vrednost, ocenjivac);
		this.klinika = klinika;
	}

	public OcenaKlinikeDTO(OcenaKlinike o) {
		this.klinika = new KlinikaDTO(o.getKlinika());
		this.setId(o.getId());
		this.setOcenjivac(new PacijentDTO(o.getOcenjivac()));
		this.setVrednost(o.getVrednost());
	}

	public KlinikaDTO getKlinika() {
		return klinika;
	}

	public void setKlinika(KlinikaDTO klinika) {
		this.klinika = klinika;
	}
}
