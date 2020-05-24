package klinika.dto;

import klinika.model.Recept;

public class ReceptDTO {

	private Long id;
	private StavkaSifrarnikaDTO lek;
	private String pacijent;
	private String lekar;
	private long datum;

	public ReceptDTO() {

	}

	public ReceptDTO(Recept r) {
		this.id = r.getId();
		this.lek = new StavkaSifrarnikaDTO(r.getLek());
		this.pacijent = r.getIzvestaj().getZdravstveniKarton().getPacijent().getIme() + " "
				+ r.getIzvestaj().getZdravstveniKarton().getPacijent().getPrezime();
		this.lekar = r.getIzvestaj().getPregled().getLekar().getIme() + " "
				+ r.getIzvestaj().getPregled().getLekar().getPrezime();
		this.datum = r.getIzvestaj().getPregled().getVreme();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StavkaSifrarnikaDTO getLek() {
		return lek;
	}

	public void setLek(StavkaSifrarnikaDTO lek) {
		this.lek = lek;
	}

	public String getPacijent() {
		return pacijent;
	}

	public void setPacijent(String pacijent) {
		this.pacijent = pacijent;
	}

	public String getLekar() {
		return lekar;
	}

	public void setLekar(String lekar) {
		this.lekar = lekar;
	}

	public long getDatum() {
		return datum;
	}

	public void setDatum(long datum) {
		this.datum = datum;
	}
}
