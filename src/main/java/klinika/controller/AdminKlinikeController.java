package klinika.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import klinika.dto.AdminKlinikeDTO;
import klinika.dto.KlinikaDTO;
import klinika.model.AdminKlinike;
import klinika.model.Klinika;
import klinika.service.AdminKlinikeService;
import klinika.service.AutoritetService;
import klinika.service.KlinikaService;
import klinika.service.KorisnikService;

@RestController
@RequestMapping(value = "adminiKlinike")
public class AdminKlinikeController {

	@Autowired
	private AdminKlinikeService adminKlinikeService;

	@Autowired
	private KorisnikService korisnikService;

	@Autowired
	private KlinikaService klinikaService;

	@Autowired
	private AutoritetService autoritetService;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN_KLINICKOG_CENTRA', 'ADMIN_KLINIKE')")
	public ResponseEntity<AdminKlinikeDTO> ucitajPoId(@PathVariable Long id) {
		AdminKlinike a = adminKlinikeService.findOne(id);
		AdminKlinikeDTO ak = new AdminKlinikeDTO(a);
		return new ResponseEntity<>(ak, HttpStatus.OK);
	}

	@GetMapping("ucitajKlinikuPoIDAdmina/{id}")
	@PreAuthorize("hasAnyRole('ADMIN_KLINICKOG_CENTRA', 'ADMIN_KLINIKE')")
	public ResponseEntity<KlinikaDTO> ucitajKlinikuPoIDAdmina(@PathVariable Long id) {
		AdminKlinike a = adminKlinikeService.findOne(id);
		Klinika k = a.getKlinika();
		return new ResponseEntity<>(new KlinikaDTO(k), HttpStatus.OK);
	}

	@GetMapping(value = "/ucitajSve")
	@PreAuthorize("hasRole('ADMIN_KLINICKOG_CENTRA')")
	public ResponseEntity<List<AdminKlinikeDTO>> ucitajSveAdmineKlinike() {
		List<AdminKlinike> admini = adminKlinikeService.findAll();

		List<AdminKlinikeDTO> adminKlinikeDTO = new ArrayList<>();
		for (AdminKlinike a : admini) {
			adminKlinikeDTO.add(new AdminKlinikeDTO(a));
		}
		return new ResponseEntity<>(adminKlinikeDTO, HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINICKOG_CENTRA')")
	public ResponseEntity<AdminKlinikeDTO> kreirajAdminaKlinike(@RequestBody AdminKlinikeDTO adminKlinikeDTO) {
		AdminKlinike adminKlinike = new AdminKlinike();
		adminKlinike.setAdresa(adminKlinikeDTO.getAdresa());
		adminKlinike.setDrzava(adminKlinikeDTO.getDrzava());
		adminKlinike.setGrad(adminKlinikeDTO.getGrad());
		adminKlinike.setEmail(adminKlinikeDTO.getEmail());
		adminKlinike.setIme(adminKlinikeDTO.getIme());
		adminKlinike.setLozinka(korisnikService.encodePassword(adminKlinikeDTO.getLozinka()));
		adminKlinike.setPrezime(adminKlinikeDTO.getPrezime());
		adminKlinike.setBrojTelefona(adminKlinikeDTO.getBrojTelefona());
		adminKlinike.setAutoriteti(autoritetService.findByName("ROLE_ADMIN_KLINIKE"));
		adminKlinike.setAktiviran(true);
		adminKlinike.setVerifikovan(true);
		adminKlinike.setPromenjenaLozinka(false);

		Klinika klinika = klinikaService.findByNaziv(adminKlinikeDTO.getKlinika());
		adminKlinike.setKlinika(klinika);

		adminKlinike = adminKlinikeService.save(adminKlinike);
		return new ResponseEntity<>(new AdminKlinikeDTO(adminKlinike), HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<AdminKlinikeDTO> promeniKorisnika(@RequestBody AdminKlinikeDTO akDTO) {
		AdminKlinike ak = adminKlinikeService.findOne(akDTO.getId());
		if (ak == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		ak.setAdresa(akDTO.getAdresa());
		ak.setDrzava(akDTO.getDrzava());
		ak.setGrad(akDTO.getGrad());
		ak.setIme(akDTO.getIme());
		ak.setLozinka(korisnikService.encodePassword(akDTO.getLozinka()));
		ak.setPrezime(akDTO.getPrezime());
		ak.setBrojTelefona(akDTO.getBrojTelefona());
		ak.setAktiviran(akDTO.isAktiviran());
		ak.setPromenjenaLozinka(akDTO.isPromenjenaLozinka());
		ak.setVerifikovan(akDTO.isVerifikovan());
		ak.setKlinika(klinikaService.findByNaziv(akDTO.getKlinika()));

		ak = adminKlinikeService.save(ak);
		return new ResponseEntity<>(new AdminKlinikeDTO(ak), HttpStatus.OK);
	}
}
