package klinika.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tokeni_operacija")
public class TokenPotvrdeOperacije {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String token;

	@OneToOne(targetEntity = Operacija.class, fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "operacija_id")
	private Operacija operacija;

	public TokenPotvrdeOperacije() {

	}

	public TokenPotvrdeOperacije(Long id, String token, Operacija operacija) {
		this.id = id;
		this.token = token;
		this.operacija = operacija;
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

	public Operacija getOperacija() {
		return operacija;
	}

	public void setOperacija(Operacija operacija) {
		this.operacija = operacija;
	}
}
