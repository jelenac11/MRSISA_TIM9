package tim09.klinika.dto;

import java.util.ArrayList;

public class SalaDTO {

	private Long id;
	private int broj;
	private String naziv;
	private ArrayList<PregledDTO> pregledi;
	private ArrayList<OperacijaDTO> operacije;

	public SalaDTO() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getBroj() {
		return broj;
	}

	public void setBroj(int broj) {
		this.broj = broj;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
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
