package klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import klinika.model.OcenaKlinike;

public interface OcenaKlinikeRepository extends JpaRepository<OcenaKlinike, Long> {

	OcenaKlinike findByOcenjivacIdAndKlinikaId(Long id, long klinikaId);

}
