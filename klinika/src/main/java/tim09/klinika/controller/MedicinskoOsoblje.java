package tim09.klinika.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim09.klinika.dto.MedicinskoOsobljeDTO;
import tim09.klinika.model.Lekar;
import tim09.klinika.model.MedSestra;
import tim09.klinika.service.AutoritetService;
import tim09.klinika.service.KorisnikService;
import tim09.klinika.service.LekarService;
import tim09.klinika.service.MedSestraService;

@RestController
@RequestMapping(value = "zaposleni")
public class MedicinskoOsoblje {

	@Autowired
	private LekarService lekarService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private MedSestraService medSestraService;
	
	@Autowired
	private AutoritetService autoritetService;
	
	@GetMapping(value = "/ucitajSve")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<MedicinskoOsobljeDTO>> ucitajSveZaposlene() {
		List<Lekar> lekari = lekarService.findAll();
		List<MedSestra> medicinskeSestre = medSestraService.findAll();
		List<MedicinskoOsobljeDTO> zaposleni = new ArrayList<>();
		
		for (Lekar l : lekari) {
			zaposleni.add(new MedicinskoOsobljeDTO(l));
		}
		
		
		for (MedSestra m : medicinskeSestre) {
			zaposleni.add(new MedicinskoOsobljeDTO(m));
		}
		return new ResponseEntity<>(zaposleni, HttpStatus.OK);
	}
	
}
