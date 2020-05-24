package klinika.dto;

import klinika.model.Sala;

public class SalaDTO {

	private Long id;
	private int broj;
	private String naziv;

	public SalaDTO() {

	}

	public SalaDTO(Sala sala) {
		if (sala == null) {
			return;
		}
		this.id = sala.getId();
		this.broj = sala.getBroj();
		this.naziv = sala.getNaziv();
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

}
