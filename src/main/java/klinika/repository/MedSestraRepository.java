package klinika.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import klinika.model.Klinika;
import klinika.model.MedSestra;

public interface MedSestraRepository extends JpaRepository<MedSestra, Long> {

	List<MedSestra> findAllByKlinika(Klinika k);

	MedSestra findByEmail(String name);
}
