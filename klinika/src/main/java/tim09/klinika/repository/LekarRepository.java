package tim09.klinika.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tim09.klinika.model.Lekar;

public interface LekarRepository extends JpaRepository<Lekar, Long> {

}
