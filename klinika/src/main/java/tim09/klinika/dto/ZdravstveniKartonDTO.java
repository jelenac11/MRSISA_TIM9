package tim09.klinika.dto;

import java.util.ArrayList;

public class ZdravstveniKartonDTO {

	private Long id;
	private PacijentDTO pacijent;
	private ArrayList<IzvestajDTO> bolesti;
	private double visina;
	private double tezina;
	private double dioptrija;
	private String krvnaGrupa;

	public ZdravstveniKartonDTO() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PacijentDTO getPacijent() {
		return pacijent;
	}

	public void setPacijent(PacijentDTO pacijent) {
		this.pacijent = pacijent;
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

	public ArrayList<IzvestajDTO> getBolesti() {
		return bolesti;
	}

	public void setBolesti(ArrayList<IzvestajDTO> bolesti) {
		this.bolesti = bolesti;
	}
}
