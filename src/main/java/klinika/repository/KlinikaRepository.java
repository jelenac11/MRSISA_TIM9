package klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import klinika.model.Klinika;

public interface KlinikaRepository extends JpaRepository<Klinika, Long> {

	Klinika findByNaziv(String naziv);
}
