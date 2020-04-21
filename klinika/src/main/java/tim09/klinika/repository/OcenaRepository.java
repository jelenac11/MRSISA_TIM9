package tim09.klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim09.klinika.model.Ocena;

public interface OcenaRepository extends JpaRepository<Ocena, Long> {

}
