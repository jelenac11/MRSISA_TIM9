package klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import klinika.model.Korisnik;

public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {

	Korisnik findByEmail(String email);
}
