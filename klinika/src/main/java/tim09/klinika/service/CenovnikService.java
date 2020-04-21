package tim09.klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.model.Cenovnik;
import tim09.klinika.repository.CenovnikRepository;

@Service
public class CenovnikService {

	@Autowired
	private CenovnikRepository cenovnikRepository;

	public Cenovnik findOne(Long id) {
		return cenovnikRepository.findById(id).orElseGet(null);
	}

	public List<Cenovnik> findAll() {
		return cenovnikRepository.findAll();
	}

	public Cenovnik save(Cenovnik cenovnik) {
		return cenovnikRepository.save(cenovnik);
	}

	public void remove(Long id) {
		cenovnikRepository.deleteById(id);
	}
}
