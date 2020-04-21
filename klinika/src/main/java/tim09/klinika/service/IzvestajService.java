package tim09.klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.model.Izvestaj;
import tim09.klinika.repository.IzvestajRepository;

@Service
public class IzvestajService {

	@Autowired
	private IzvestajRepository izvestajRepository;

	public Izvestaj findOne(Long id) {
		return izvestajRepository.findById(id).orElseGet(null);
	}

	public List<Izvestaj> findAll() {
		return izvestajRepository.findAll();
	}

	public Izvestaj save(Izvestaj izvestaj) {
		return izvestajRepository.save(izvestaj);
	}

	public void remove(Long id) {
		izvestajRepository.deleteById(id);
	}
}
