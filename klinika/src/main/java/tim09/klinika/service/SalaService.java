package tim09.klinika.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.dto.TipPregledaDTO;
import tim09.klinika.model.Sala;
import tim09.klinika.repository.SalaRepository;

@Service
public class SalaService {

	@Autowired
	private SalaRepository salaRepository;

	@Autowired
	private FormatDatumaService datumService;

	public Sala findByBroj(int broj) {
		return salaRepository.findByBrojAndAktivan(broj,true);
	}

	public Sala findOne(Long id) {
		return salaRepository.findByIdAndAktivan(id, true);
	}

	public List<Sala> findAll() {
		return salaRepository.findAllByAktivan(true);
	}

	public Sala save(Sala sala) {
		return salaRepository.save(sala);
	}

	public boolean remove(Long id) {
		Sala s=findOne(id);
		if(s!=null) {
			if(!salaRepository.findBySalaIdAndVreme(id,new Date().getTime()).isEmpty()) {
				s.setAktivan(false);
				save(s);
				
				return true;
			}
		}
		return false;
	}

	public List<Sala> findByIdKlinikaAndVreme(Long klinikaId, long datumiVreme, long trajanje) {
		return salaRepository.findByIdKlinikaAndVreme(klinikaId, datumiVreme, trajanje);
	}
}
