package tim09.klinika.dto;

import java.util.ArrayList;

import tim09.klinika.model.Cenovnik;
import tim09.klinika.model.StavkaCenovnika;

public class CenovnikDTO {

	private Long id;
	private ArrayList<StavkaCenovnikaDTO> stavke;
	
	public CenovnikDTO() {
		
	}
	
	public CenovnikDTO(Cenovnik c) {
		this.id = c.getId();
		this.stavke = new ArrayList<StavkaCenovnikaDTO>();
		for (StavkaCenovnika sc : c.getStavke()) {
			this.stavke.add(new StavkaCenovnikaDTO(sc));
		}
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
