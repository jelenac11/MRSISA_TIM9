package klinika.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class StavkaCenovnika implements Serializable {

	private static final long serialVersionUID = 737638372497240177L;

	@Id
	@Column(name = "stavkaCenovnika_id")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipPregleda_id")
	@MapsId
	private TipPregleda tipPregleda;

	@Column(name = "cena", nullable = false)
	private double cena;

	@OneToMany(mappedBy = "stavkaCenovnika", cascade = CascadeType.ALL)
	private Set<Popust> popusti;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cenovnik_id")
	private Cenovnik cenovnik;

	public StavkaCenovnika() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipPregleda getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(TipPregleda tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public Set<Popust> getPopusti() {
		return popusti;
	}

	public void setPopusti(Set<Popust> popusti) {
		this.popusti = popusti;
	}

	public Cenovnik getCenovnik() {
		return cenovnik;
	}

	public void setCenovnik(Cenovnik cenovnik) {
		this.cenovnik = cenovnik;
	}

}
