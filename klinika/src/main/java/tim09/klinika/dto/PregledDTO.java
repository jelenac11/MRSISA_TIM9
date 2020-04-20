package tim09.klinika.dto;

import java.util.Date;

import tim09.klinika.model.Pregled;

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

	public PregledDTO() {

	}

	public PregledDTO(Pregled os) {
		// TODO Auto-generated constructor stub
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
}
