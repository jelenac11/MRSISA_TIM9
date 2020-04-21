package tim09.klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim09.klinika.model.TipPregleda;

public interface TipPregledaRepository extends JpaRepository<TipPregleda, Long> {

}
