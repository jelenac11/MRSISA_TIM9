package klinika.repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import klinika.model.Korisnik;

public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {

	Korisnik findByEmail(String email);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
	@Query("select k from Korisnik k where k.email=:email")
	public Korisnik findByEmailZaRegOdgovor(@Param("email") String email);
}
