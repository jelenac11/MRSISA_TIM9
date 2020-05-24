package klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import klinika.model.KlinickiCentar;

public interface KlinickiCentarRepository extends JpaRepository<KlinickiCentar, Long> {

}
