package tim09.klinika.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.model.TokenPotvrdeOperacije;
import tim09.klinika.model.TokenPotvrdePregleda;
import tim09.klinika.repository.TokenPotvrdeOperacijeRepository;
import tim09.klinika.repository.TokenPotvrdePregledaRepository;

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
