package klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import klinika.model.StavkaCenovnika;
import klinika.model.TipPregleda;

public interface StavkaCenovnikaRepository extends JpaRepository<StavkaCenovnika, Long> {

	StavkaCenovnika findByTipPregleda(TipPregleda tp);
}
