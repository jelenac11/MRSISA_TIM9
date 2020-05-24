package klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import klinika.model.TokenPotvrdePregleda;

public interface TokenPotvrdePregledaRepository extends JpaRepository<TokenPotvrdePregleda, Long> {

	TokenPotvrdePregleda findByToken(String token);

}
