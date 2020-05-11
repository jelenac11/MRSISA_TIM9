package tim09.klinika.dto;

public class PretragaLekaraDTO {

	private Long id;
	private Long datum;
	private String tipPregleda;
	private Long pacijent;
	private Long klinika;
	
	public PretragaLekaraDTO() {
		
	}

	public PretragaLekaraDTO(Long id, Long datum, String tipPregleda) {
		super();
		this.id = id;
		this.datum = datum;
		this.tipPregleda = tipPregleda;
	}
	
	public PretragaLekaraDTO(Long id, Long datum, String tipPregleda, Long pacijent, Long klinika) {
		super();
		this.id = id;
		this.datum = datum;
		this.tipPregleda = tipPregleda;
		this.pacijent = pacijent;
		this.klinika = klinika;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDatum() {
		return datum;
	}
	public void setDatum(Long datum) {
		this.datum = datum;
	}
	public String getTipPregleda() {
		return tipPregleda;
	}
	public void setTipPregleda(String tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public Long getPacijent() {
		return pacijent;
	}

	public void setPacijent(Long pacijent) {
		this.pacijent = pacijent;
	}

	public Long getKlinika() {
		return klinika;
	}

	public void setKlinika(Long klinika) {
		this.klinika = klinika;
	}
}
