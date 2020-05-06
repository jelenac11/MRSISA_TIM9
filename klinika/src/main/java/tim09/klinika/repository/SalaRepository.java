package tim09.klinika.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import tim09.klinika.model.Sala;

public interface SalaRepository extends JpaRepository<Sala, Long> {

	Sala findByBroj(int broj);
}