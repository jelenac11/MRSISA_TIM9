package tim09.klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim09.klinika.model.Pregled;

public interface PregledRepository extends JpaRepository<Pregled, Long> {

}
