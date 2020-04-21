package tim09.klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim09.klinika.model.Izvestaj;

public interface IzvestajRepository extends JpaRepository<Izvestaj, Long> {

}
