package tim09.klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.model.Sala;
import tim09.klinika.repository.SalaRepository;

@Service
public class SalaService {

	@Autowired
	private SalaRepository salaRepository;

	public Sala findByBroj(int broj) {
		return salaRepository.findByBroj(broj);
	}
	
	public Sala findOne(Long id) {
		return salaRepository.findById(id).orElseGet(null);
	}

	public List<Sala> findAll() {
		return salaRepository.findAll();
	}

	public Sala save(Sala sala) {
		return salaRepository.save(sala);
	}

	public void remove(Long id) {
		salaRepository.deleteById(id);
	}
}
