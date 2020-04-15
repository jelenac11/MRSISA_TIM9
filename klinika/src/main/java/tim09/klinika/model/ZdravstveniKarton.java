package tim09.klinika.model;

import tim09.klinika.model.Izvestaj;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


@Entity
public class ZdravstveniKarton {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany
	@JoinColumn(name = "zdravstveniKarton_id" )
	private Set<Izvestaj> bolesti;

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
