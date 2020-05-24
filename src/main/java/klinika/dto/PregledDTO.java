package klinika.dto;

import java.util.Date;

import klinika.model.Pregled;

public class PregledDTO {

	private Long id;
	private LekarDTO lekar;
	private PacijentDTO pacijent;
	private TipPregledaDTO tipPregleda;
	private IzvestajDTO izvestaj;
	private SalaDTO sala;
	private Date vreme;
	private boolean otkazan;
	private KlinikaDTO klinika;
	private boolean zauzet;
	private long trajanje;
	private long vreme2;

	public PregledDTO() {

	}

	public PregledDTO(Pregled os) {
		this.id = os.getId();
		this.lekar = new LekarDTO(os.getLekar());
		this.pacijent = new PacijentDTO(os.getPacijent());
		this.tipPregleda = new TipPregledaDTO(os.getTipPregleda());
		this.sala = new SalaDTO(os.getSala());
		this.vreme = new Date(os.getVreme());
		this.otkazan = os.isOtkazan();
		this.klinika = new KlinikaDTO(os.getKlinika());
		this.zauzet = os.isZauzet();
		this.trajanje = os.getTrajanje();
		this.vreme2 = os.getVreme();
	}

	public LekarDTO getLekar() {
		return lekar;
	}

	public void setLekar(LekarDTO lekar) {
		this.lekar = lekar;
	}

	public PacijentDTO getPacijent() {
		return pacijent;
	}

	public void setPacijent(PacijentDTO pacijent) {
		this.pacijent = pacijent;
	}

	public TipPregledaDTO getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(TipPregledaDTO tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public IzvestajDTO getIzvestaj() {
		return izvestaj;
	}

	public void setIzvestaj(IzvestajDTO izvestaj) {
		this.izvestaj = izvestaj;
	}

	public SalaDTO getSala() {
		return sala;
	}

	public void setSala(SalaDTO sala) {
		this.sala = sala;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getVreme() {
		return vreme;
	}

	public void setVreme(Date vreme) {
		this.vreme = vreme;
	}

	public boolean isOtkazan() {
		return otkazan;
	}

	public void setOtkazan(boolean otkazan) {
		this.otkazan = otkazan;
	}

	public KlinikaDTO getKlinika() {
		return klinika;
	}

	public void setKlinika(KlinikaDTO klinika) {
		this.klinika = klinika;
	}

	public boolean isZauzet() {
		return zauzet;
	}

	public void setZauzet(boolean zauzet) {
		this.zauzet = zauzet;
	}

	public long getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(long trajanje) {
		this.trajanje = trajanje;
	}

	public long getVreme2() {
		return vreme2;
	}

	public void setVreme2(long vreme2) {
		this.vreme2 = vreme2;
	}

}
