package tim09.klinika.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.repository.LekarRepository;
import tim09.klinika.repository.PacijentRepository;
import tim09.klinika.dto.PacijentDTO;
import tim09.klinika.model.Klinika;
import tim09.klinika.model.Lekar;
import tim09.klinika.model.Pacijent;

@Service
public class PacijentService {

	@Autowired
	private PacijentRepository pacijentRepository;
	
	@Autowired
	private LekarRepository lekarRepository;

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

	public List<PacijentDTO> findByKlinikaID(Long id) {
		Lekar l=lekarRepository.findById(id).orElseGet(null);
		List<PacijentDTO> pacijentiDTO=new ArrayList<PacijentDTO>();
		if(l==null) {
			return pacijentiDTO;
		}
		Klinika k=l.getKlinika();
		List<Pacijent> pacijenti=pacijentRepository.findbyKlinikaId(k.getId());
		for(Pacijent pacijent:pacijenti) {
			pacijentiDTO.add(new PacijentDTO(pacijent));
		}
		return pacijentiDTO;
	}
}
