package klinika.dto;

public class PredefinisaniDTO {

	private long datum;
	private int sala;
	private LekarDTO lekar;
	private TipPregledaDTO tip;
	private double cena;
	private double popust;
	private KorisnikDTO pacijent;
	private KlinikaDTO lokacija;

	public PredefinisaniDTO() {

	}

	public PredefinisaniDTO(long datum, int sala, LekarDTO lekar, TipPregledaDTO tip, double cena, double popust,
			KorisnikDTO pacijent, KlinikaDTO lokacija) {
		super();
		this.datum = datum;
		this.sala = sala;
		this.lekar = lekar;
		this.tip = tip;
		this.cena = cena;
		this.popust = popust;
		this.pacijent = pacijent;
		this.lokacija = lokacija;
	}

	public long getDatum() {
		return datum;
	}

	public void setDatum(long datum) {
		this.datum = datum;
	}

	public int getSala() {
		return sala;
	}

	public void setSala(int sala) {
		this.sala = sala;
	}

	public LekarDTO getLekar() {
		return lekar;
	}

	public void setLekar(LekarDTO lekar) {
		this.lekar = lekar;
	}

	public TipPregledaDTO getTip() {
		return tip;
	}

	public void setTip(TipPregledaDTO tip) {
		this.tip = tip;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public double getPopust() {
		return popust;
	}

	public void setPopust(double popust) {
		this.popust = popust;
	}

	public KorisnikDTO getPacijent() {
		return pacijent;
	}

	public void setPacijent(KorisnikDTO pacijent) {
		this.pacijent = pacijent;
	}

	public KlinikaDTO getLokacija() {
		return lokacija;
	}

	public void setLokacija(KlinikaDTO lokacija) {
		this.lokacija = lokacija;
	}
}
