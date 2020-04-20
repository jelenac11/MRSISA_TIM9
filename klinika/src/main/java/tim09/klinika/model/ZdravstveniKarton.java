package tim09.klinika.model;

import tim09.klinika.dto.IzvestajDTO;
import tim09.klinika.dto.PacijentDTO;
import tim09.klinika.model.Izvestaj;

import java.util.ArrayList;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
public class ZdravstveniKarton {

	@Id
	@Column(name = "zdravstveniKarton_id")
	private Long id;

	@OneToMany(mappedBy = "zdravstveniKarton", cascade = CascadeType.ALL)
	private Set<Izvestaj> bolesti;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pacijent_id")
	@MapsId
	private Pacijent pacijent;

	@Column(name = "visina")
	private double visina;

	@Column(name = "tezina")
	private double tezina;

	@Column(name = "dioptrija")
	private double dioptrija;

	@Column(name = "krvna_grupa")
	private String krvnaGrupa;

	public ZdravstveniKarton() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Izvestaj> getBolesti() {
		return bolesti;
	}

	public void setBolesti(Set<Izvestaj> bolesti) {
		this.bolesti = bolesti;
	}

}
