package klinika.dto;

import klinika.model.Popust;

public class PopustDTO {

	private Long id;
	private String tipPregleda;
	private long pocetak;
	private long kraj;
	private double procenat;

	public PopustDTO() {

	}

	public PopustDTO(Popust p) {
		this.id = p.getId();
		this.tipPregleda = p.getStavkaCenovnika().getTipPregleda().getNaziv();
		this.pocetak = p.getPocetak();
		this.kraj = p.getKraj();
		this.procenat = p.getProcenat();
	}

	public String getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(String tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getPocetak() {
		return pocetak;
	}

	public void setPocetak(long pocetak) {
		this.pocetak = pocetak;
	}

	public long getKraj() {
		return kraj;
	}

	public void setKraj(long kraj) {
		this.kraj = kraj;
	}

	public double getProcenat() {
		return procenat;
	}

	public void setProcenat(double procenat) {
		this.procenat = procenat;
	}
}
