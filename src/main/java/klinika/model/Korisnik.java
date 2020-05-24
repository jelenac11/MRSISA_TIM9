package klinika.model;

import java.util.Collection;
import java.util.List;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "korisnici")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tip", discriminatorType = DiscriminatorType.STRING)
public class Korisnik implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "korisnik_id")
	private Long id;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "lozinka", nullable = false)
	private String lozinka;

	@Column(name = "ime", nullable = false)
	private String ime;

	@Column(name = "prezime", nullable = false)
	private String prezime;

	@Column(name = "adresa", nullable = false)
	private String adresa;

	@Column(name = "grad", nullable = false)
	private String grad;

	@Column(name = "drzava", nullable = false)
	private String drzava;

	@Column(name = "broj_telefona", nullable = false)
	private String brojTelefona;

	@Column(name = "verifikovan")
	private boolean verifikovan;

	@Column(name = "aktiviran")
	private boolean aktiviran;

	@Column(name = "promenjena_lozinka")
	private boolean promenjenaLozinka;

	@Column(name = "poslednja_promena_lozinke")
	private Long poslednjaPromenaLozinke;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "autoriteti_korisnika", joinColumns = @JoinColumn(name = "korisnik_id", referencedColumnName = "korisnik_id"), inverseJoinColumns = @JoinColumn(name = "autoritet_id", referencedColumnName = "id"))
	private List<Autoritet> autoriteti;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "klinickiCentar_id")
	private KlinickiCentar klinickiCentar;

	public Korisnik() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.setPoslednjaPromenaLozinke(System.currentTimeMillis());
		this.lozinka = lozinka;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getDrzava() {
		return drzava;
	}

	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}

	public String getBrojTelefona() {
		return brojTelefona;
	}

	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}

	public boolean isVerifikovan() {
		return verifikovan;
	}

	public void setVerifikovan(boolean verifikovan) {
		this.verifikovan = verifikovan;
	}

	public boolean isAktiviran() {
		return aktiviran;
	}

	public void setAktiviran(boolean aktiviran) {
		this.aktiviran = aktiviran;
	}

	public boolean isPromenjenaLozinka() {
		return promenjenaLozinka;
	}

	public void setPromenjenaLozinka(boolean promenjenaLozinka) {
		this.promenjenaLozinka = promenjenaLozinka;
	}

	public KlinickiCentar getKlinickiCentar() {
		return klinickiCentar;
	}

	public void setKlinickiCentar(KlinickiCentar klinickiCentar) {
		this.klinickiCentar = klinickiCentar;
	}

	public Long getPoslednjaPromenaLozinke() {
		return poslednjaPromenaLozinke;
	}

	public void setPoslednjaPromenaLozinke(long l) {
		this.poslednjaPromenaLozinke = l;
	}

	public List<Autoritet> getAutoriteti() {
		return autoriteti;
	}

	public void setAutoriteti(List<Autoritet> autoriteti) {
		this.autoriteti = autoriteti;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.autoriteti;
	}

	@Override
	public String getPassword() {
		return this.lozinka;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
