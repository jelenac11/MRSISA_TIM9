package tim09.klinika.dto;

import java.util.ArrayList;

public class SifrarnikDTO {

	private Long id;
	private ArrayList<StavkaSifrarnikaDTO> stavke;

	public SifrarnikDTO() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArrayList<StavkaSifrarnikaDTO> getStavke() {
		return stavke;
	}

	public void setStavke(ArrayList<StavkaSifrarnikaDTO> stavke) {
		this.stavke = stavke;
	}
}
