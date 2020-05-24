package tim09.klinika.dto;

import tim09.klinika.model.AdminKlinike;

public class AdminKlinikeDTO extends KorisnikDTO {

	private String klinika;

	public AdminKlinikeDTO() {

	}

	public AdminKlinikeDTO(AdminKlinike admin) {
		super(admin);
		this.klinika = admin.getKlinika().getNaziv();
	}

	public String getKlinika() {
		return klinika;
	}

	public void setKlinika(String klinika) {
		this.klinika = klinika;
	}
}
