package klinika.dto;

public class RadniKalendarDTO {

	private long start;
	private long end;
	private String naziv;
	private long pacijentId;
	private String pacijent;
	private String tipPregleda;
	private String lekari;
	private String sala;

	public RadniKalendarDTO() {

	}

	public RadniKalendarDTO(long start, long end, String naziv, long pacijentId, String pacijent, String tipPregleda,
			String lekari, String sala) {
		this.start = start;
		this.end = end;
		this.naziv = naziv;
		this.pacijentId = pacijentId;
		this.pacijent = pacijent;
		this.tipPregleda = tipPregleda;
		this.lekari = lekari;
		this.sala = sala;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public long getPacijentId() {
		return pacijentId;
	}

	public void setPacijentId(long pacijentId) {
		this.pacijentId = pacijentId;
	}

	public String getPacijent() {
		return pacijent;
	}

	public void setPacijent(String pacijent) {
		this.pacijent = pacijent;
	}

	public String getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(String tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public String getLekari() {
		return lekari;
	}

	public void setLekari(String lekari) {
		this.lekari = lekari;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}
}
