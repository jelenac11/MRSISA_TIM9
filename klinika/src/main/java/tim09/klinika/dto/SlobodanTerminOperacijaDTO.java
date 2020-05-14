package tim09.klinika.dto;

import java.util.ArrayList;

public class SlobodanTerminOperacijaDTO {

	private long idAdmina;
	private long datumiVreme;
	private SalaDTO sala;
	private ArrayList<LekarDTO> lekari;
	private int trajanje;
	private long operacijaId;

	public SlobodanTerminOperacijaDTO() {

	}

	public SlobodanTerminOperacijaDTO(long idAdmina, long datumiVreme, SalaDTO sala, ArrayList<LekarDTO> lekari,
			int trajanje) {
		this.idAdmina = idAdmina;
		this.datumiVreme = datumiVreme;
		this.sala = sala;
		this.lekari = lekari;
		this.trajanje = trajanje;
	}

	public long getIdAdmina() {
		return idAdmina;
	}

	public void setIdAdmina(long idAdmina) {
		this.idAdmina = idAdmina;
	}

	public long getDatumiVreme() {
		return datumiVreme;
	}

	public void setDatumiVreme(long datumiVreme) {
		this.datumiVreme = datumiVreme;
	}

	public SalaDTO getSala() {
		return sala;
	}

	public void setSala(SalaDTO sala) {
		this.sala = sala;
	}

	public ArrayList<LekarDTO> getLekari() {
		return lekari;
	}

	public void setLekari(ArrayList<LekarDTO> lekari) {
		this.lekari = lekari;
	}

	public int getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}

	public long getOperacijaId() {
		return operacijaId;
	}

	public void setOperacijaId(long operacijaId) {
		this.operacijaId = operacijaId;
	}
	
}
