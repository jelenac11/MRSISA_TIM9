package tim09.klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import tim09.klinika.dto.ReceptDTO;
import tim09.klinika.model.Korisnik;
import tim09.klinika.model.MedSestra;
import tim09.klinika.model.Recept;
import tim09.klinika.repository.ReceptRepository;

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

	public boolean overi(ReceptDTO receptDTO) {
		Authentication trenutniKorisnik = SecurityContextHolder.getContext().getAuthentication();
		MedSestra ulogovan = medSestraService.findByEmail(trenutniKorisnik.getName());

		Recept r = findOne(receptDTO.getId());
		r.setMedSestra(ulogovan);
		receptRepository.save(r);
		return true;
	}
}
