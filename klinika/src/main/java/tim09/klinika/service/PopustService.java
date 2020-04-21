package tim09.klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.model.Popust;
import tim09.klinika.repository.PopustRepository;

@Service
public class PopustService {
	
	@Autowired
	private PopustRepository popustRepository;
	
	public Popust findOne(Long id) {
		return popustRepository.findById(id).orElseGet(null);
	}

	public List<Popust> findAll() {
		return popustRepository.findAll();
	}
	
	public Popust save(Popust popust) {
		return popustRepository.save(popust);
	}

	public void remove(Long id) {
		popustRepository.deleteById(id);
	}
}
