package tim09.klinika.model;

import java.util.Date;

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
public class Odsustvo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="odsustvo_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="korisnik_id")
	private MedicinskoOsoblje podnosilac;
	
	@Column(name = "pocetak",nullable = false)
	private long pocetak;
	
	@Column(name = "kraj",nullable = false)
	private long kraj;
	
	@Column(name = "odgovoreno",nullable = false)
	private boolean odgovoreno;
	
	@Column(name = "odobreno",nullable = true)
	private boolean odobreno;

	@Column(name = "obrazlozenje",nullable=true)
	private String obrazlozenje;
	
	@ManyToOne
	@JoinColumn(name = "klinika_id")
	private Klinika klinika;
	
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
