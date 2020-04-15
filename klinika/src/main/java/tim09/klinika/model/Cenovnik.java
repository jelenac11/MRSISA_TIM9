package tim09.klinika.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


@Entity
public class Cenovnik {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	
	@OneToMany
	@JoinColumn(name="cenovnik_id")
	private Set<StavkaCenovnika> stavke;
	
	public Cenovnik() {
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Set<StavkaCenovnika> getStavke() {
		return stavke;
	}
	public void setStavke(Set<StavkaCenovnika> stavke) {
		this.stavke = stavke;
	}
}
