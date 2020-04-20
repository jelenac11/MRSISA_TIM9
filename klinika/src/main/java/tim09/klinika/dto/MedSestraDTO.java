package tim09.klinika.dto;

import java.util.ArrayList;

public class MedSestraDTO extends MedicinskoOsobljeDTO {

	private ArrayList<ReceptDTO> recepti;

	public MedSestraDTO() {

	}

	public ArrayList<ReceptDTO> getRecepti() {
		return recepti;
	}

	public void setRecepti(ArrayList<ReceptDTO> recepti) {
		this.recepti = recepti;
	}
}
