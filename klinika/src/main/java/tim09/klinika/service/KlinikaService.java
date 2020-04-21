package tim09.klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.model.Klinika;
import tim09.klinika.repository.KlinikaRepository;

@Service
public class KlinikaService {
	
	@Autowired
	private KlinikaRepository klinikaRepository;
	
	public Klinika findOne(Long id) {
		return klinikaRepository.findById(id).orElseGet(null);
	}

	public List<Klinika> findAll() {
		return klinikaRepository.findAll();
	}
	
	public Klinika save(Klinika klinika) {
		return klinikaRepository.save(klinika);
	}

	public void remove(Long id) {
		klinikaRepository.deleteById(id);
	}
}

