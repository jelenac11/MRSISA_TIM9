package klinika.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import klinika.model.TokenPotvrdePregleda;
import klinika.repository.TokenPotvrdePregledaRepository;

@Service
public class TokenPotvrdePregledaService {

	@Autowired
	private TokenPotvrdePregledaRepository tokenPotvrdePregledaRepository;

	public TokenPotvrdePregleda findByToken(String token) {
		return tokenPotvrdePregledaRepository.findByToken(token);
	}

	public void saveToken(TokenPotvrdePregleda token) {
		tokenPotvrdePregledaRepository.save(token);
	}

	public void deleteById(long token) {
		tokenPotvrdePregledaRepository.deleteById(token);
	}

}