package klinika.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import klinika.model.Lekar;
import klinika.model.Operacija;

public class OperacijaDTO {

	private Long id;
	private List<LekarDTO> lekari;
	private PacijentDTO pacijent;
	private SalaDTO sala;
	private Date vreme;
	private long vreme2;
	private KlinikaDTO klinika;

	public OperacijaDTO() {

	}

	public OperacijaDTO(Operacija o) {
		this.id = o.getId();
		this.lekari = new ArrayList<>();
		for (Lekar l : o.getLekari()) {
			this.lekari.add(new LekarDTO(l));
		}
		this.sala = new SalaDTO(o.getSala());
		this.vreme = new Date(o.getVreme());
		this.vreme2 = o.getVreme();
		this.klinika = new KlinikaDTO(o.getKlinika());
		this.pacijent = new PacijentDTO(o.getPacijent());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<LekarDTO> getLekari() {
		return lekari;
	}

	public void setLekari(List<LekarDTO> lekari) {
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

	public long getVreme2() {
		return vreme2;
	}

	public void setVreme2(long vreme2) {
		this.vreme2 = vreme2;
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
