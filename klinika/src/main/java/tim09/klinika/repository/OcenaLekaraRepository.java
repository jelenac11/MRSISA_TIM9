package tim09.klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim09.klinika.model.OcenaLekara;

public interface OcenaLekaraRepository extends JpaRepository<OcenaLekara, Long> {

	OcenaLekara findByOcenjivacIdAndLekarId(Long id, long lekarId);
	
}
