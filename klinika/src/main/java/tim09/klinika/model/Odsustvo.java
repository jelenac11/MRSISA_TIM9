package tim09.klinika.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Odsustvo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private MedicinskoOsoblje podnosilac;
	
	@Column(name = "pocetak",nullable = false)
	private Date pocetak;
	
	@Column(name = "kraj",nullable = false)
	private Date kraj;
	
	@Column(name = "odgovoreno",nullable = false)
	private boolean odgovoreno;
	
	@Column(name = "odobreno",nullable = true)
	private boolean odobreno;

	@Column(name = "obrazlozenje",nullable=true)
	private String obrazlozenje;
	
	public Odsustvo() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MedicinskoOsoblje getPodnosilac() {
		return podnosilac;
	}

	public void setPodnosilac(MedicinskoOsoblje podnosilac) {
		this.podnosilac = podnosilac;
	}

	public Date getPocetak() {
		return pocetak;
	}

	public void setPocetak(Date pocetak) {
		this.pocetak = pocetak;
	}

	public Date getKraj() {
		return kraj;
	}

	public void setKraj(Date kraj) {
		this.kraj = kraj;
	}
	

	public boolean isOdgovoreno() {
		return odgovoreno;
	}

	public void setOdgovoreno(boolean odgovoreno) {
		this.odgovoreno = odgovoreno;
	}

	public boolean isOdobreno() {
		return odobreno;
	}

	public void setOdobreno(boolean odobreno) {
		this.odobreno = odobreno;
	}

	public String getObrazlozenje() {
		return obrazlozenje;
	}

	public void setObrazlozenje(String obrazlozenje) {
		this.obrazlozenje = obrazlozenje;
	}
	
}
