package klinika.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import klinika.repository.LekarRepository;
import klinika.repository.MedSestraRepository;
import klinika.repository.PacijentRepository;
import klinika.dto.PacijentDTO;
import klinika.model.MedicinskoOsoblje;
import klinika.model.Autoritet;
import klinika.model.Klinika;
import klinika.model.Korisnik;
import klinika.model.Pacijent;

@Service
public class PacijentService {

	@Autowired
	private PacijentRepository pacijentRepository;

	@Autowired
	private LekarRepository lekarRepository;

	@Autowired
	private MedSestraRepository medSestraRepository;

	@Autowired
	private KorisnikService korisnikService;

	public Pacijent findByJbo(String jbo) {
		return pacijentRepository.findByJbo(jbo);
	}

	public List<Pacijent> nadjiRegZahteve() {
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

	// Metoda koja vraća sve pacijente odabrane klinike
	public List<PacijentDTO> findByKlinikaID(Long id) {
		Korisnik korisnik = korisnikService.findOne(id);
		Autoritet a = korisnik.getAutoriteti().get(0);

		MedicinskoOsoblje med = null;
		if (a.getAuthority().equals("ROLE_LEKAR")) {
			med = lekarRepository.findById(id).orElseGet(null);
		} else if (a.getAuthority().equals("ROLE_MED_SESTRA")) {
			med = medSestraRepository.findById(id).orElseGet(null);
		}

		List<PacijentDTO> pacijentiDTO = new ArrayList<>();
		if (med == null) {
			return pacijentiDTO;
		}
		Klinika k = med.getKlinika();
		List<Pacijent> pacijenti = pacijentRepository.findbyKlinikaId(k.getId());
		for (Pacijent pacijent : pacijenti) {
			pacijentiDTO.add(new PacijentDTO(pacijent));
		}
		return pacijentiDTO;
	}
}
