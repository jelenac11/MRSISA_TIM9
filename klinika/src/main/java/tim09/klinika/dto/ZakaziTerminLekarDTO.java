package tim09.klinika.dto;

public class ZakaziTerminLekarDTO {

	private long pacijent;
	private long lekar;
	private long datumiVreme;
	private long trajanje;
	private TipPregledaDTO tipPregleda;
	
	public ZakaziTerminLekarDTO(long pacijent, long lekar, long datumiVreme, long trajanje,
			TipPregledaDTO tipPregleda) {
		super();
		this.pacijent = pacijent;
		this.lekar = lekar;
		this.datumiVreme = datumiVreme;
		this.trajanje = trajanje;
		this.tipPregleda = tipPregleda;
	}
	public ZakaziTerminLekarDTO() {
		super();
	}
	public long getPacijent() {
		return pacijent;
	}
	public void setPacijent(long pacijent) {
		this.pacijent = pacijent;
	}
	public long getLekar() {
		return lekar;
	}
	public void setLekar(long lekar) {
		this.lekar = lekar;
	}
	public long getDatumiVreme() {
		return datumiVreme;
	}
	public void setDatumiVreme(long datumiVreme) {
		this.datumiVreme = datumiVreme;
	}
	public long getTrajanje() {
		return trajanje;
	}
	public void setTrajanje(long trajanje) {
		this.trajanje = trajanje;
	}
	public TipPregledaDTO getTipPregleda() {
		return tipPregleda;
	}
	public void setTipPregleda(TipPregledaDTO tipPregleda) {
		this.tipPregleda = tipPregleda;
	}
}
