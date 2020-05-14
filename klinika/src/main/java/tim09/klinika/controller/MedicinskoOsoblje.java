package tim09.klinika.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim09.klinika.dto.MedicinskoOsobljeDTO;
import tim09.klinika.dto.RadniKalendarDTO;
import tim09.klinika.model.Korisnik;
import tim09.klinika.model.Lekar;
import tim09.klinika.model.MedSestra;
import tim09.klinika.service.AutoritetService;
import tim09.klinika.service.KorisnikService;
import tim09.klinika.service.LekarService;
import tim09.klinika.service.MedSestraService;
import tim09.klinika.service.OdsustvoService;
import tim09.klinika.service.OperacijaService;
import tim09.klinika.service.PregledService;

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

	@GetMapping(value = "/dobaviRadniKalendar")
	@PreAuthorize("hasAnyRole('LEKAR', 'MED_SESTRA')")
	public ResponseEntity<List<RadniKalendarDTO>> dobaviRadniKalendar() {
		Authentication trenutniKorisnik = SecurityContextHolder.getContext().getAuthentication();
		Korisnik k = korisnikService.findByEmail(trenutniKorisnik.getName());
		if (k == null) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		List<RadniKalendarDTO> listOne = pregledService.kreirajRadniKalendarRadnika(k.getId(), new Date().getTime());
		List<RadniKalendarDTO> listTwo = operacijaService.kreirajRadniKalendarRadnika(k.getId(), new Date().getTime());
		//List<RadniKalendarDTO> listThree = odsustvoService.kreirajRadniKalendarRadnika(k.getId(), new Date().getTime());
		listOne.addAll(listTwo);
		//listOne.addAll(listThree);
//		System.out.println("***************************************************************************************");
//		for (RadniKalendarDTO radniKalendarDTO : listOne) {
//			System.out.println(radniKalendarDTO.getNaziv());
//		}
		return new ResponseEntity<>(listOne, HttpStatus.CREATED);
	}

}
