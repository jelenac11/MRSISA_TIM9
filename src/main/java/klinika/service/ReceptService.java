package klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import klinika.dto.ReceptDTO;
import klinika.model.MedSestra;
import klinika.model.Recept;
import klinika.repository.ReceptRepository;

@Service
public class ReceptService {

	@Autowired
	private ReceptRepository receptRepository;

	@Autowired
	private MedSestraService medSestraService;

	public Recept findOne(Long id) {
		return receptRepository.findById(id).orElseGet(null);
	}

	public List<Recept> findAll() {
		return receptRepository.findAll();
	}

	public Recept save(Recept recept) {
		return receptRepository.save(recept);
	}

	public void remove(Long id) {
		receptRepository.deleteById(id);
	}

	public List<Recept> findByMedSestraIdIsNull() {
		return receptRepository.findByMedSestraIdIsNull();
	}
	
	// Metoda kojom medicinska sestra overava recepte
	@Transactional(readOnly = false)
	public boolean overi(ReceptDTO receptDTO) {
		Authentication trenutniKorisnik = SecurityContextHolder.getContext().getAuthentication();
		MedSestra ulogovan = medSestraService.findByEmail(trenutniKorisnik.getName());

		Recept r = findOne(receptDTO.getId());
		if(r.getMedSestra()==null) {
		r.setMedSestra(ulogovan);
		receptRepository.save(r);
		return true;
		}
		else {
			return false;
		}
	}
}
