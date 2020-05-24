package klinika.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import klinika.model.TokenPotvrdeOperacije;
import klinika.repository.TokenPotvrdeOperacijeRepository;

@Service
public class TokenPotvrdeOperacijeService {

	@Autowired
	private TokenPotvrdeOperacijeRepository tokenPotvrdeOperacijeRepository;

	public TokenPotvrdeOperacije findByToken(String token) {
		return tokenPotvrdeOperacijeRepository.findByToken(token);
	}

	public void saveToken(TokenPotvrdeOperacije token) {
		tokenPotvrdeOperacijeRepository.save(token);
	}

	public void deleteById(long token) {
		tokenPotvrdeOperacijeRepository.deleteById(token);
	}
}
