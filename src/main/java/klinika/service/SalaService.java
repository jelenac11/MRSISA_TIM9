package klinika.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	public Sala findByNaziv(String naziv) {
		return salaRepository.findByNaziv(naziv);
	}

	public Sala findOne(Long id) {
		return salaRepository.findByIdAndAktivan(id, true);
	}

	public List<Sala> findAll() {
		return salaRepository.findAllByAktivan(true);
	}
	@Transactional(readOnly = false)
	public Sala save(Sala sala) {
		return salaRepository.save(sala);
	}

	// Metoda za uklanjanje sale
	@Transactional(readOnly = false)
	public boolean remove(Long id) {
		Sala s = findOne(id);
		if (s != null && !imaLiSale(id)) {
			s.setAktivan(false);
			salaRepository.save(s);
			return true;
		}
		return false;
	}

	private boolean imaLiSale(Long id) {
		return salaRepository.findBySalaIdAndVreme(id, new Date().getTime()).isEmpty();
	}

	public List<Sala> findByIdKlinikaAndVreme(Long klinikaId, long datumiVreme) {
		return salaRepository.findByIdKlinikaAndVreme(klinikaId, datumiVreme, 3600000);
	}

	// Metoda za izmenu sale
	@Transactional(readOnly = false)
	public boolean update(SalaDTO salaDTO, Sala sala) {
		Sala s = salaRepository.findById(salaDTO.getId()).orElseGet(null);
		if (!salaRepository.findBySalaIdAndVreme(salaDTO.getId(), new Date().getTime()).isEmpty()) {
			Sala postojiNaziv = null;
			if (!salaDTO.getNaziv().equals(sala.getNaziv())) {
				postojiNaziv = salaRepository.findByNazivAndAktivan(salaDTO.getNaziv(), true);
			}
			if (postojiNaziv == null) {
				s.setNaziv(salaDTO.getNaziv());
				s.setBroj(salaDTO.getBroj());
				salaRepository.save(s);
				return true;
			}
		}
		return false;
	}

	public Sala findByBrojZaSve(int broj, boolean b) {
		return salaRepository.findByBroj(broj);
	}
}
