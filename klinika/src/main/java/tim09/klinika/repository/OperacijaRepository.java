package tim09.klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim09.klinika.model.Operacija;

public interface OperacijaRepository extends JpaRepository<Operacija, Long> {

}
