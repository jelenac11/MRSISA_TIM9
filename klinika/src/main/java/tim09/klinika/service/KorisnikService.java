package tim09.klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.model.Korisnik;
import tim09.klinika.repository.KorisnikRepository;

@Service
public class KorisnikService {
	
	@Autowired
	private KorisnikRepository korisnikRepository;
	
	public Korisnik findOne(Long id) {
		return korisnikRepository.findById(id).orElseGet(null);
	}

	public List<Korisnik> findAll() {
		return korisnikRepository.findAll();
	}
	
	public Korisnik save(Korisnik korisnik) {
		return korisnikRepository.save(korisnik);
	}

	public void remove(Long id) {
		korisnikRepository.deleteById(id);
	}
}
