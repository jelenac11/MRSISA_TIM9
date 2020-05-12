package tim09.klinika.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tokeni_pregled")
public class TokenPotvrdePregleda {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String token;

	@OneToOne(targetEntity = Pregled.class, fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "pregled_id")
	private Pregled pregled;
	

	public TokenPotvrdePregleda() {
		super();
	}

	public TokenPotvrdePregleda(Long id, String token, Pregled pregled) {
		super();
		this.id = id;
		this.token = token;
		this.pregled = pregled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Pregled getPregled() {
		return pregled;
	}

	public void setPregled(Pregled pregled) {
		this.pregled = pregled;
	}
}
