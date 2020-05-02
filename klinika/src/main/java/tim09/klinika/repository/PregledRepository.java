package tim09.klinika.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tim09.klinika.model.Pregled;

public interface PregledRepository extends JpaRepository<Pregled, Long> {

	public List<Pregled> findByTipPregledaIdAndOtkazanAndVremeGreaterThan(long id,boolean otkazano,long vreme);
}
