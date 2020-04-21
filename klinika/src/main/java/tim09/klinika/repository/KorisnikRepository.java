package tim09.klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim09.klinika.model.Korisnik;

public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {

}
