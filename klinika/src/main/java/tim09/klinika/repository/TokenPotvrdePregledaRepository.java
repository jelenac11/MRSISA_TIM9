package tim09.klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim09.klinika.model.TokenPotvrdePregleda;

public interface TokenPotvrdePregledaRepository extends JpaRepository<TokenPotvrdePregleda, Long>  {

	TokenPotvrdePregleda findByToken(String token);
	
}
