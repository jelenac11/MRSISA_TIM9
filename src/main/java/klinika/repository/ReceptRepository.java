package klinika.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import klinika.model.Recept;

public interface ReceptRepository extends JpaRepository<Recept, Long> {

	List<Recept> findByMedSestraIdIsNull();

}
