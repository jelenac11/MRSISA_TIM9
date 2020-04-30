package tim09.klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim09.klinika.model.Klinika;

public interface KlinikaRepository extends JpaRepository<Klinika, Long> {

	Klinika findByNaziv(String naziv);
}
