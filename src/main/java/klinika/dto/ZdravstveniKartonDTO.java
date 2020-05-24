package klinika.dto;

import java.util.ArrayList;
import java.util.List;

import klinika.model.Izvestaj;
import klinika.model.ZdravstveniKarton;

public class ZdravstveniKartonDTO {

	private Long id;
	private List<IzvestajDTO> bolesti;
	private double visina;
	private double tezina;
	private double dioptrija;
	private String krvnaGrupa;

	public ZdravstveniKartonDTO() {

	}

	public ZdravstveniKartonDTO(ZdravstveniKarton z) {
		this.id = z.getId();
		this.visina = z.getVisina();
		this.tezina = z.getTezina();
		this.dioptrija = z.getDioptrija();
		this.krvnaGrupa = z.getKrvnaGrupa();
		this.bolesti = new ArrayList<>();
		for (Izvestaj i : z.getBolesti()) {
			this.bolesti.add(new IzvestajDTO(i));
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getVisina() {
		return visina;
	}

	public void setVisina(double visina) {
		this.visina = visina;
	}

	public double getTezina() {
		return tezina;
	}

	public void setTezina(double tezina) {
		this.tezina = tezina;
	}

	public double getDioptrija() {
		return dioptrija;
	}

	public void setDioptrija(double dioptrija) {
		this.dioptrija = dioptrija;
	}

	public String getKrvnaGrupa() {
		return krvnaGrupa;
	}

	public void setKrvnaGrupa(String krvnaGrupa) {
		this.krvnaGrupa = krvnaGrupa;
	}

	public List<IzvestajDTO> getBolesti() {
		return bolesti;
	}

	public void setBolesti(List<IzvestajDTO> bolesti) {
		this.bolesti = bolesti;
	}
}
