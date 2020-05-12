package tim09.klinika.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.dto.RadniKalendarDTO;
import tim09.klinika.model.Operacija;
import tim09.klinika.repository.OperacijaRepository;

@Service
public class OperacijaService {

	@Autowired
	private OperacijaRepository operacijaRepository;

	public Operacija findOne(Long id) {
		return operacijaRepository.findById(id).orElseGet(null);
	}

	public List<Operacija> findAll() {
		return operacijaRepository.findAll();
	}

	public Operacija save(Operacija operacija) {
		return operacijaRepository.save(operacija);
	}

	public void remove(Long id) {
		operacijaRepository.deleteById(id);
	}
	
	public List<RadniKalendarDTO> kreirajRadniKalendar(Long id, long time) {
		List<Operacija> operacije=operacijaRepository.findBySalaIdAndVremeAfter(id,time);
		List<RadniKalendarDTO> kalendar=new ArrayList<RadniKalendarDTO>();
		for(Operacija operacija:operacije) {
			kalendar.add(new RadniKalendarDTO(operacija.getVreme(), operacija.getVreme()+3600000, "Operacija"));
		}
		return kalendar;
	}
}
