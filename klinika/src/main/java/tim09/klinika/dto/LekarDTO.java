package tim09.klinika.dto;

import java.util.ArrayList;
import java.util.List;

import tim09.klinika.model.Lekar;
import tim09.klinika.model.TipPregleda;

public class LekarDTO extends MedicinskoOsobljeDTO {

	private List<TipPregledaDTO> specijalnosti;
	private double prosecnaOcena;
	private boolean slobodan;
	
	public LekarDTO() {

	}

	public LekarDTO(Lekar l) {
		super(l);
		if(l==null) {
			return;
		}
		this.specijalnosti = new ArrayList<TipPregledaDTO>();
		for (TipPregleda tp : l.getSpecijalnosti()) {
			this.specijalnosti.add(new TipPregledaDTO(tp));
		}
		this.prosecnaOcena = 1;
		this.slobodan = true;
	}

	public List<TipPregledaDTO> getSpecijalnosti() {
		return specijalnosti;
	}

	public void setSpecijalnosti(List<TipPregledaDTO> specijalnosti) {
		this.specijalnosti = specijalnosti;
	}

	public double getProsecnaOcena() {
		return prosecnaOcena;
	}

	public void setProsecnaOcena(double prosecnaOcena) {
		this.prosecnaOcena = prosecnaOcena;
	}

	public boolean isSlobodan() {
		return slobodan;
	}

	public void setSlobodan(boolean slobodan) {
		this.slobodan = slobodan;
	}

}
