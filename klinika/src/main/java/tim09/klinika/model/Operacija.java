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
public class Operacija {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="operacija_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="lekar_id")
	private Lekar lekar;
	
	@ManyToOne
	@JoinColumn(name = "pacijent_id")
	private Pacijent pacijent;
	
	@ManyToOne
	@JoinColumn(name = "sala_id")
	private Sala sala;
	
	@Column(name = "vreme",nullable = false)
	private long vreme;

	@ManyToOne
	@JoinColumn(name = "klinika_id")
	private Klinika klinika;
	
	public Operacija() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public long getVreme() {
		return vreme;
	}

	public void setVreme(long vreme) {
		this.vreme = vreme;
	}
}
