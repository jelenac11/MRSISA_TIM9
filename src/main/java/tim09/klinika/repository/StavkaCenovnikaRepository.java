package tim09.klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim09.klinika.model.StavkaCenovnika;
import tim09.klinika.model.TipPregleda;

public interface StavkaCenovnikaRepository extends JpaRepository<StavkaCenovnika, Long> {

	StavkaCenovnika findByTipPregleda(TipPregleda tp);
}
