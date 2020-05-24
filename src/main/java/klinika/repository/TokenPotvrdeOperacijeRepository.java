package klinika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import klinika.model.TokenPotvrdeOperacije;

public interface TokenPotvrdeOperacijeRepository extends JpaRepository<TokenPotvrdeOperacije, Long> {

	TokenPotvrdeOperacije findByToken(String token);
}
