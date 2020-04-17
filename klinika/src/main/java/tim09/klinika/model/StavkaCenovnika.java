package tim09.klinika.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
@Entity
public class StavkaCenovnika {

	@Id
	@GeneratedValue(generator="gen2")
	@Column(name="stavkaCenovnika_id")
	@GenericGenerator(name="gen2", strategy="foreign",parameters=@Parameter(name="property", value="tipPregleda"))
	private Long id;
	
	@OneToOne
    @PrimaryKeyJoinColumn
	private TipPregleda tipPregleda;
	
	@Column(name = "cena")
	private double cena;
	
	@OneToMany(mappedBy = "stavkaCenovnika", cascade = CascadeType.ALL)
	private Set<Popust> popusti;

	@ManyToOne
	@JoinColumn(name = "cenovnik_id")
	private Cenovnik cenovnik;
	
	public StavkaCenovnika() {

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
	
}
