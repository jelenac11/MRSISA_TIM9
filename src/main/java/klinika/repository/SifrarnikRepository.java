package klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import klinika.model.Sifrarnik;

public interface SifrarnikRepository extends JpaRepository<Sifrarnik, Long> {

	Sifrarnik findByTipSifrarnika(String tip);
}
