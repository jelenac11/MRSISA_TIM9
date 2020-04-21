package tim09.klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.model.KlinickiCentar;
import tim09.klinika.repository.KlinickiCentarRepository;

@Service
public class KlinickiCentarService {
	
	@Autowired
	private KlinickiCentarRepository klinickiCentarRepository;
	
	public KlinickiCentar findOne(Long id) {
		return klinickiCentarRepository.findById(id).orElseGet(null);
	}

	public List<KlinickiCentar> findAll() {
		return klinickiCentarRepository.findAll();
	}
	
	public KlinickiCentar save(KlinickiCentar klinickiCentar) {
		return klinickiCentarRepository.save(klinickiCentar);
	}

	public void remove(Long id) {
		klinickiCentarRepository.deleteById(id);
	}
}

