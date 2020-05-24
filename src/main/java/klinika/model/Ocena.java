package klinika.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ocene")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tip", discriminatorType = DiscriminatorType.STRING)
public class Ocena {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ocena_id")
	private Long id;

	@Column(name = "vrednost", nullable = false)
	private int vrednost;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ocenjivac_id")
	private Pacijent ocenjivac;

	public Ocena() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVrednost() {
		return vrednost;
	}

	public void setVrednost(int vrednost) {
		this.vrednost = vrednost;
	}

	public Pacijent getOcenjivac() {
		return ocenjivac;
	}

	public void setOcenjivac(Pacijent ocenjivac) {
		this.ocenjivac = ocenjivac;
	}
}
