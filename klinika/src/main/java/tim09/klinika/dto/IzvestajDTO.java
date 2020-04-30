package tim09.klinika.dto;

import java.util.ArrayList;

import tim09.klinika.model.Izvestaj;
import tim09.klinika.model.Recept;

public class IzvestajDTO {

	private Long id;
	private String opis;
	private StavkaSifrarnikaDTO dijagnoza;
	private ArrayList<ReceptDTO> recepti;

	public IzvestajDTO() {

	}
	
	public IzvestajDTO(Izvestaj i) {
		this.id = i.getId();
		this.opis = i.getOpis();
		this.dijagnoza = new StavkaSifrarnikaDTO(i.getDijagnoza());
		this.recepti = new ArrayList<ReceptDTO>();
		for (Recept r : i.getRecepti()) {
			this.recepti.add(new ReceptDTO(r));
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public StavkaSifrarnikaDTO getDijagnoza() {
		return dijagnoza;
	}

	public void setDijagnoza(StavkaSifrarnikaDTO dijagnoza) {
		this.dijagnoza = dijagnoza;
	}

	public ArrayList<ReceptDTO> getRecepti() {
		return recepti;
	}

	public void setRecepti(ArrayList<ReceptDTO> recepti) {
		this.recepti = recepti;
	}
}
