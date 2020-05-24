package klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import klinika.model.ZdravstveniKarton;

public interface ZdravstveniKartonRepository extends JpaRepository<ZdravstveniKarton, Long> {

}
