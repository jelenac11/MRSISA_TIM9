package tim09.klinika.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Recept {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "opis",nullable = true)
	private String opis;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private MedSestra medSestra;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private StavkaSifrarnika lek;

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
