package klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import klinika.model.Ocena;

public interface OcenaRepository extends JpaRepository<Ocena, Long> {

}
