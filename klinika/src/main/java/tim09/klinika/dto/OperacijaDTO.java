package tim09.klinika.dto;

import java.util.ArrayList;
import java.util.Date;

import tim09.klinika.model.Operacija;

public class OperacijaDTO {

	private Long id;
	private ArrayList<LekarDTO> lekari;
	private PacijentDTO pacijent;
	private SalaDTO sala;
	private Date vreme;
	private KlinikaDTO klinika;

	public OperacijaDTO() {

	}


	public OperacijaDTO(Operacija os) {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArrayList<LekarDTO> getLekari() {
		return lekari;
	}


	public void setLekari(ArrayList<LekarDTO> lekari) {
		this.lekari = lekari;
	}


	public PacijentDTO getPacijent() {
		return pacijent;
	}

	public void setPacijent(PacijentDTO pacijent) {
		this.pacijent = pacijent;
	}

	public SalaDTO getSala() {
		return sala;
	}

	public void setSala(SalaDTO sala) {
		this.sala = sala;
	}

	public Date getVreme() {
		return vreme;
	}

	public void setVreme(Date vreme) {
		this.vreme = vreme;
	}


	public KlinikaDTO getKlinika() {
		return klinika;
	}


	public void setKlinika(KlinikaDTO klinika) {
		this.klinika = klinika;
	}
}
