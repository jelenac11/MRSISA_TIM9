package tim09.klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim09.klinika.model.OcenaKlinike;

public interface OcenaKlinikeRepository extends JpaRepository<OcenaKlinike, Long> {

	OcenaKlinike findByOcenjivacIdAndKlinikaId(Long id, long klinikaId);

}
