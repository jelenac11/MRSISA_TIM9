package tim09.klinika.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Recept {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recept_id")
	private Long id;

	@Column(name = "opis", nullable = true)
	private String opis;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "medSestra_id")
	private MedSestra medSestra;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lek_id")
	private StavkaSifrarnika lek;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "izvestaj_id")
	private Izvestaj izvestaj;

	public Recept() {

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

	public MedSestra getMedSestra() {
		return medSestra;
	}

	public void setMedSestra(MedSestra medSestra) {
		this.medSestra = medSestra;
	}

	public StavkaSifrarnika getLek() {
		return lek;
	}

	public void setLek(StavkaSifrarnika lek) {
		this.lek = lek;
	}
}
