package tim09.klinika.dto;

import java.util.ArrayList;

import tim09.klinika.model.Pacijent;

public class PacijentDTO extends KorisnikDTO {

	private ZdravstveniKartonDTO karton;
	private ArrayList<PregledDTO> pregledi;
	private ArrayList<OperacijaDTO> operacije;
	private String jbo;
	private String obrazlozenje;

	public PacijentDTO() {

	}
	
	public PacijentDTO(Pacijent p) {
		super(p);
		this.jbo = p.getJbo();
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

	public String getJbo() {
		return jbo;
	}

	public void setJbo(String jbo) {
		this.jbo = jbo;
	}

	public String getObrazlozenje() {
		return obrazlozenje;
	}

	public void setObrazlozenje(String obrazlozenje) {
		this.obrazlozenje = obrazlozenje;
	}
}
