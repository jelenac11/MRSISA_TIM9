package tim09.klinika.dto;

import java.util.ArrayList;

public class PacijentDTO extends KorisnikDTO {

	private ZdravstveniKartonDTO karton;
	private ArrayList<PregledDTO> pregledi;
	private ArrayList<OperacijaDTO> operacije;

	public PacijentDTO() {

	}

	public ZdravstveniKartonDTO getKarton() {
		return karton;
	}

	public void setKarton(ZdravstveniKartonDTO karton) {
		this.karton = karton;
	}

	public ArrayList<PregledDTO> getPregledi() {
		return pregledi;
	}

	public void setPregledi(ArrayList<PregledDTO> pregledi) {
		this.pregledi = pregledi;
	}

	public ArrayList<OperacijaDTO> getOperacije() {
		return operacije;
	}

	public void setOperacije(ArrayList<OperacijaDTO> operacije) {
		this.operacije = operacije;
	}
}
