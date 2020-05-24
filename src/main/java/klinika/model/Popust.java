package klinika.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Popust implements Serializable {

	private static final long serialVersionUID = -6250597383419757589L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "popust_id")
	private Long id;

	@Column(name = "pocetak", nullable = false)
	private long pocetak;

	@Column(name = "kraj", nullable = false)
	private long kraj;

	@Column(name = "procenat", nullable = false)
	private double procenat;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stavkaCenovnika_id")
	private StavkaCenovnika stavkaCenovnika;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "klinika_id")
	private Klinika klinika;

	public Popust() {
		super();
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

	public StavkaCenovnika getStavkaCenovnika() {
		return stavkaCenovnika;
	}

	public void setStavkaCenovnika(StavkaCenovnika stavkaCenovnika) {
		this.stavkaCenovnika = stavkaCenovnika;
	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}

}
