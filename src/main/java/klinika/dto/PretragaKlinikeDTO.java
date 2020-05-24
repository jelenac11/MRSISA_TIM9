package klinika.dto;

public class PretragaKlinikeDTO {

	private Long id;
	private Long datum;
	private String tipPregleda;

	public PretragaKlinikeDTO() {

	}

	public PretragaKlinikeDTO(Long id, Long datum, String tipPregleda) {
		super();
		this.id = id;
		this.datum = datum;
		this.tipPregleda = tipPregleda;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDatum() {
		return datum;
	}

	public void setDatum(long datum) {
		this.datum = datum;
	}

	public String getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(String tipPregleda) {
		this.tipPregleda = tipPregleda;
	}
}
