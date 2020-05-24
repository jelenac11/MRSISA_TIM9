package klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import klinika.model.Klinika;
import klinika.model.MedSestra;
import klinika.repository.MedSestraRepository;

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

	public MedSestra findByEmail(String name) {
		return medSestraRepository.findByEmail(name);
	}
}
