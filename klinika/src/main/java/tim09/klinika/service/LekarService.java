package tim09.klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.model.Korisnik;
import tim09.klinika.model.Lekar;
import tim09.klinika.repository.KorisnikRepository;

@Service
public class LekarService {

	@Autowired
	private KorisnikRepository korisnikRepository;
	
	public List<Lekar> nadjiSveLekare() {
		return korisnikRepository.nadjiSveLekare();
	}
	
	public Lekar save(Lekar lekar) {
		return korisnikRepository.save(lekar);
	}

}
