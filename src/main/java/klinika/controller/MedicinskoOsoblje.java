package klinika.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import klinika.dto.MedicinskoOsobljeDTO;
import klinika.dto.OdsustvoDTO;
import klinika.dto.RadniKalendarDTO;
import klinika.model.Korisnik;
import klinika.model.Lekar;
import klinika.model.MedSestra;
import klinika.service.KorisnikService;
import klinika.service.LekarService;
import klinika.service.MedSestraService;
import klinika.service.OdsustvoService;
import klinika.service.OperacijaService;
import klinika.service.PregledService;

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
	private PregledService pregledService;

	@Autowired
	private OperacijaService operacijaService;

	@Autowired
	private OdsustvoService odsustvoService;

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

	@GetMapping(value = "/dobaviRadniKalendar")
	@PreAuthorize("hasAnyRole('LEKAR', 'MED_SESTRA')")
	public ResponseEntity<List<RadniKalendarDTO>> dobaviRadniKalendar() {
		Authentication trenutniKorisnik = SecurityContextHolder.getContext().getAuthentication();
		Korisnik k = korisnikService.findByEmail(trenutniKorisnik.getName());
		if (k == null) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		List<RadniKalendarDTO> listOne = pregledService.kreirajRadniKalendarRadnika(k.getId());
		List<RadniKalendarDTO> listTwo = operacijaService.kreirajRadniKalendarRadnika(k.getId());
		List<RadniKalendarDTO> listThree = odsustvoService.kreirajRadniKalendarRadnika(k.getId());
		listOne.addAll(listTwo);
		listOne.addAll(listThree);
		return new ResponseEntity<>(listOne, HttpStatus.CREATED);
	}

	@PostMapping(value = "/zahtevOdsustvo")
	@PreAuthorize("hasAnyRole('LEKAR','MED_SESTRA')")
	public ResponseEntity<Boolean> zahtevOdsustvo(@RequestBody OdsustvoDTO odsustvoDTO) {
		boolean odgovor = korisnikService.zahtevOdsustvo(odsustvoDTO);
		return new ResponseEntity<>(odgovor, HttpStatus.OK);
	}
}
