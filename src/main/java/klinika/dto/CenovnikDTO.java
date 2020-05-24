package klinika.dto;

import java.util.ArrayList;
import java.util.List;

import klinika.model.Cenovnik;
import klinika.model.StavkaCenovnika;

public class CenovnikDTO {

	private Long id;
	private List<StavkaCenovnikaDTO> stavke;

	public CenovnikDTO() {
		super();
	}

	public CenovnikDTO(Cenovnik c) {
		this.id = c.getId();
		this.stavke = new ArrayList<>();
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

	public List<StavkaCenovnikaDTO> getStavke() {
		return stavke;
	}

	public void setStavke(List<StavkaCenovnikaDTO> stavke) {
		this.stavke = stavke;
	}
}
