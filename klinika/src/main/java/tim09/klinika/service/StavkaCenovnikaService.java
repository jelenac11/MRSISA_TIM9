package tim09.klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.model.StavkaCenovnika;
import tim09.klinika.repository.StavkaCenovnikaRepository;

@Service
public class StavkaCenovnikaService {

	@Autowired
	private StavkaCenovnikaRepository stavkaCenovnikaRepository;

	public StavkaCenovnika findOne(Long id) {
		return stavkaCenovnikaRepository.findById(id).orElseGet(null);
	}

	public List<StavkaCenovnika> findAll() {
		return stavkaCenovnikaRepository.findAll();
	}

	public StavkaCenovnika save(StavkaCenovnika stavkaCenovnika) {
		return stavkaCenovnikaRepository.save(stavkaCenovnika);
	}

	public void remove(Long id) {
		stavkaCenovnikaRepository.deleteById(id);
	}
}
