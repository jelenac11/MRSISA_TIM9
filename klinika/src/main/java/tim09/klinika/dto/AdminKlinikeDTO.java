package tim09.klinika.dto;

import tim09.klinika.model.AdminKlinike;

public class AdminKlinikeDTO {

	private KlinikaDTO klinika;

	public AdminKlinikeDTO() {

	}

	public AdminKlinikeDTO(AdminKlinike admin) {
		// TODO Auto-generated constructor stub
	}

	public KlinikaDTO getKlinika() {
		return klinika;
	}

	public void setKlinika(KlinikaDTO klinika) {
		this.klinika = klinika;
	}
}
