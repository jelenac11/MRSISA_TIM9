package tim09.klinika.dto;

import java.util.ArrayList;
import java.util.List;

import tim09.klinika.model.Lekar;
import tim09.klinika.model.TipPregleda;

public class LekarDTO extends MedicinskoOsobljeDTO {

	private List<TipPregledaDTO> specijalnosti;
	
	public LekarDTO() {

	}

	public LekarDTO(Lekar l) {
		super(l);
		this.specijalnosti = new ArrayList<TipPregledaDTO>();
		for (TipPregleda tp : l.getSpecijalnosti()) {
			this.specijalnosti.add(new TipPregledaDTO(tp));
		}
	}

	public List<TipPregledaDTO> getSpecijalnosti() {
		return specijalnosti;
	}

	public void setSpecijalnosti(List<TipPregledaDTO> specijalnosti) {
		this.specijalnosti = specijalnosti;
	}

}
