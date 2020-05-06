package tim09.klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.model.StavkaSifrarnika;
import tim09.klinika.repository.StavkaSifrarnikaRepository;

@Service
public class StavkaSifrarnikaService {

	@Autowired
	private StavkaSifrarnikaRepository stavkaSifrarnikaRepository;

	public StavkaSifrarnika findBySifra(String sifra) {
		return stavkaSifrarnikaRepository.findBySifra(sifra);
	}
	public StavkaSifrarnika findOne(Long id) {
		return stavkaSifrarnikaRepository.findById(id).orElseGet(null);
	}

	public List<StavkaSifrarnika> findAll() {
		return stavkaSifrarnikaRepository.findAll();
	}

	public StavkaSifrarnika save(StavkaSifrarnika stavkaSifrarnika) {
		return stavkaSifrarnikaRepository.save(stavkaSifrarnika);
	}

	public void remove(Long id) {
		stavkaSifrarnikaRepository.deleteById(id);
	}
}
