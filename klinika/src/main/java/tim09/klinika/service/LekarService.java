package tim09.klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.model.Lekar;
import tim09.klinika.repository.KorisnikRepository;
import tim09.klinika.repository.LekarRepository;

@Service
public class LekarService {

	@Autowired
	private LekarRepository lekarRepository;

	public Lekar findOne(Long id) {
		return lekarRepository.findById(id).orElseGet(null);
	}

	public List<Lekar> findAll() {
		return lekarRepository.findAll();
	}

	public Lekar save(Lekar lekar) {
		return lekarRepository.save(lekar);
	}

	public void remove(Long id) {
		lekarRepository.deleteById(id);
	}

}
