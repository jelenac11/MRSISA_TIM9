package tim09.klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.model.Klinika;
import tim09.klinika.model.MedSestra;
import tim09.klinika.repository.MedSestraRepository;

@Service
public class MedSestraService {

	@Autowired
	private MedSestraRepository medSestraRepository;

	public List<MedSestra> findAllByKlinika(Klinika k) {
		return medSestraRepository.findAllByKlinika(k);
	}
	
	public MedSestra findOne(Long id) {
		return medSestraRepository.findById(id).orElseGet(null);
	}

	public List<MedSestra> findAll() {
		return medSestraRepository.findAll();
	}

	public MedSestra save(MedSestra medSestra) {
		return medSestraRepository.save(medSestra);
	}

	public void remove(Long id) {
		medSestraRepository.deleteById(id);
	}
}
