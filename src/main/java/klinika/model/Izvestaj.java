package klinika.model;

import java.util.Set;

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
public class Izvestaj {

	@Id
	@Column(name = "izvestaj_id")
	private Long id;

	@Column(name = "opis", unique = false, nullable = true)
	private String opis;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pregled_id")
	@MapsId
	private Pregled pregled;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stavkaSifrarnika_id")
	private StavkaSifrarnika dijagnoza;

	@OneToMany(mappedBy = "izvestaj")
	private Set<Recept> recepti;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "zdravstveniKarton_id")
	private ZdravstveniKarton zdravstveniKarton;

	public Izvestaj() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Pregled getPregled() {
		return pregled;
	}

	public void setPregled(Pregled pregled) {
		this.pregled = pregled;
	}

	public StavkaSifrarnika getDijagnoza() {
		return dijagnoza;
	}

	public void setDijagnoza(StavkaSifrarnika dijagnoza) {
		this.dijagnoza = dijagnoza;
	}

	public Set<Recept> getRecepti() {
		return recepti;
	}

	public void setRecepti(Set<Recept> recepti) {
		this.recepti = recepti;
	}

	public ZdravstveniKarton getZdravstveniKarton() {
		return zdravstveniKarton;
	}

	public void setZdravstveniKarton(ZdravstveniKarton zdravstveniKarton) {
		this.zdravstveniKarton = zdravstveniKarton;
	}
}
