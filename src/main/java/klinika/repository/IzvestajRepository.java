package klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import klinika.model.Izvestaj;

public interface IzvestajRepository extends JpaRepository<Izvestaj, Long> {

}
