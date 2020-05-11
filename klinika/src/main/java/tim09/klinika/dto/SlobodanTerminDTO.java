package tim09.klinika.dto;

public class SlobodanTerminDTO {

	private long idAdmina;
	private long datumiVreme;
	private TipPregledaDTO tipPregleda;
	private SalaDTO sala;
	private LekarDTO lekar;
	private int trajanje;
	
	public SlobodanTerminDTO() {
		super();
	}
	public SlobodanTerminDTO(long idAdmina, long datumiVreme, TipPregledaDTO tipPregleda, SalaDTO sala,
			LekarDTO lekar,int trajanje) {
		super();
		this.idAdmina = idAdmina;
		this.datumiVreme = datumiVreme;
		this.tipPregleda = tipPregleda;
		this.sala = sala;
		this.lekar = lekar;
		this.trajanje=trajanje;
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
	public TipPregledaDTO getTipPregleda() {
		return tipPregleda;
	}
	public void setTipPregleda(TipPregledaDTO tipPregleda) {
		this.tipPregleda = tipPregleda;
	}
	public SalaDTO getSala() {
		return sala;
	}
	public void setSala(SalaDTO sala) {
		this.sala = sala;
	}
	public LekarDTO getLekar() {
		return lekar;
	}
	public void setLekar(LekarDTO lekar) {
		this.lekar = lekar;
	}
	public int getTrajanje() {
		return trajanje;
	}
	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}
}
