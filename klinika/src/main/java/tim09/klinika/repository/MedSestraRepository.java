package tim09.klinika.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tim09.klinika.model.Klinika;
import tim09.klinika.model.MedSestra;

public interface MedSestraRepository extends JpaRepository<MedSestra, Long> {

	List<MedSestra> findAllByKlinika(Klinika k);
}
