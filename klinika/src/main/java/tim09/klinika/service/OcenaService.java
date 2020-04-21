package tim09.klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.model.Ocena;
import tim09.klinika.repository.OcenaRepository;

@Service
public class OcenaService {

	@Autowired
	private OcenaRepository ocenaRepository;

	public Ocena findOne(Long id) {
		return ocenaRepository.findById(id).orElseGet(null);
	}

	public List<Ocena> findAll() {
		return ocenaRepository.findAll();
	}

	public Ocena save(Ocena ocena) {
		return ocenaRepository.save(ocena);
	}

	public void remove(Long id) {
		ocenaRepository.deleteById(id);
	}
}
