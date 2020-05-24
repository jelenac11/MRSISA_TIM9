package klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import klinika.model.StavkaSifrarnika;

public interface StavkaSifrarnikaRepository extends JpaRepository<StavkaSifrarnika, Long> {

	StavkaSifrarnika findBySifra(String sifra);
}
