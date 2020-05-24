package klinika.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import klinika.dto.SalaDTO;
import klinika.model.Sala;
import klinika.repository.SalaRepository;

@Service
public class SalaService {

	@Autowired
	private SalaRepository salaRepository;

	public Sala findByBroj(int broj) {
		return salaRepository.findByBrojAndAktivan(broj, true);
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
		Sala s = findOne(id);
		if (s != null && !imaLiSale(id, new Date().getTime())) {
			s.setAktivan(false);
			save(s);
			return true;
		}
		return false;
	}

	private boolean imaLiSale(Long id, long time) {
		return salaRepository.findBySalaIdAndVreme(id, new Date().getTime()).isEmpty();
	}

	public List<Sala> findByIdKlinikaAndVreme(Long klinikaId, long datumiVreme) {
		return salaRepository.findByIdKlinikaAndVreme(klinikaId, datumiVreme, 3600000);
	}

	public boolean update(SalaDTO salaDTO, Sala sala) {
		if (!salaRepository.findBySalaIdAndVreme(salaDTO.getId(), new Date().getTime()).isEmpty()) {
			Sala postojiNaziv = null;
			if (!salaDTO.getNaziv().equals(sala.getNaziv())) {
				postojiNaziv = salaRepository.findByNazivAndAktivan(salaDTO.getNaziv(), true);
			}
			if (postojiNaziv == null) {
				Sala s = salaRepository.findById(salaDTO.getId()).orElseGet(null);
				s.setNaziv(salaDTO.getNaziv());
				s.setBroj(salaDTO.getBroj());
				salaRepository.save(s);
				return true;
			}
		}
		return false;
	}
}
