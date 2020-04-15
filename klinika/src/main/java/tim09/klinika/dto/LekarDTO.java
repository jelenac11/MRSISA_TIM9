package tim09.klinika.dto;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class LekarDTO extends MedicinskoOsobljeDTO {

	private ArrayList<TipPregledaDTO> specijalnosti;
	private ArrayList<OperacijaDTO> operacije;
	private ArrayList<PregledDTO> pregledi;

	public LekarDTO() {

	}

	public ArrayList<TipPregledaDTO> getSpecijalnosti() {
		return specijalnosti;
	}

	public void setSpecijalnosti(ArrayList<TipPregledaDTO> specijalnosti) {
		this.specijalnosti = specijalnosti;
	}

	public ArrayList<OperacijaDTO> getOperacije() {
		return operacije;
	}

	public void setOperacije(ArrayList<OperacijaDTO> operacije) {
		this.operacije = operacije;
	}

	public ArrayList<PregledDTO> getPregledi() {
		return pregledi;
	}

	public void setPregledi(ArrayList<PregledDTO> pregledi) {
		this.pregledi = pregledi;
	}
}
