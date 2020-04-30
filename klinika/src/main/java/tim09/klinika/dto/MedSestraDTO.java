package tim09.klinika.dto;

import java.util.ArrayList;

import tim09.klinika.model.MedSestra;
import tim09.klinika.model.Recept;

public class MedSestraDTO extends MedicinskoOsobljeDTO {

	private ArrayList<ReceptDTO> recepti;

	public MedSestraDTO() {

	}

	public MedSestraDTO(MedSestra m) {
		super(m);
		/*this.recepti = new ArrayList<ReceptDTO>();
		for (Recept r : m.getRecepti()) {
			this.recepti.add(new ReceptDTO(r));
		}*/
	}

	public ArrayList<ReceptDTO> getRecepti() {
		return recepti;
	}

	public void setRecepti(ArrayList<ReceptDTO> recepti) {
		this.recepti = recepti;
	}
}
