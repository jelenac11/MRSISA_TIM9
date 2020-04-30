package tim09.klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.repository.PacijentRepository;

import tim09.klinika.model.Pacijent;

@Service
public class PacijentService {

	@Autowired
	private PacijentRepository pacijentRepository;

	public Pacijent findByJbo(String jbo) {
		return pacijentRepository.findByJbo(jbo);
	}
	
	public List<Pacijent> nadjiRegZahteve(){
		return pacijentRepository.nadjiRegZahteve();
	}
	
	public Pacijent findOne(Long id) {
		return pacijentRepository.findById(id).orElseGet(null);
	}

	public List<Pacijent> findAll() {
		return pacijentRepository.findAll();
	}
	
	public Pacijent save(Pacijent pacijent) {
		return pacijentRepository.save(pacijent);
	}

	public void remove(Long id) {
		pacijentRepository.deleteById(id);
	}
}
