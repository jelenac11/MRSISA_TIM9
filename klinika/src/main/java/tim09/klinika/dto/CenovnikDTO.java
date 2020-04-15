package tim09.klinika.dto;

import java.util.ArrayList;

import tim09.klinika.model.Cenovnik;

public class CenovnikDTO {

	private Long id;
	private ArrayList<StavkaCenovnikaDTO> stavke;
	
	public CenovnikDTO() {
		
	}
	
	public CenovnikDTO(Cenovnik cenovnik) {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ArrayList<StavkaCenovnikaDTO> getStavke() {
		return stavke;
	}
	public void setStavke(ArrayList<StavkaCenovnikaDTO> stavke) {
		this.stavke = stavke;
	}
}
