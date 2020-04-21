package tim09.klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.model.Recept;
import tim09.klinika.repository.ReceptRepository;

@Service
public class ReceptService {

	@Autowired
	private ReceptRepository receptRepository;

	public Recept findOne(Long id) {
		return receptRepository.findById(id).orElseGet(null);
	}

	public List<Recept> findAll() {
		return receptRepository.findAll();
	}

	public Recept save(Recept recept) {
		return receptRepository.save(recept);
	}

	public void remove(Long id) {
		receptRepository.deleteById(id);
	}
}
