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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import klinika.dto.MedSestraDTO;
import klinika.model.AdminKlinike;
import klinika.model.Klinika;
import klinika.model.Korisnik;
import klinika.model.MedSestra;
import klinika.service.AdminKlinikeService;
import klinika.service.AutoritetService;
import klinika.service.KlinikaService;
import klinika.service.KorisnikService;
import klinika.service.MedSestraService;

@RestController
@RequestMapping(value = "sestre")
public class MedSestraController {

	@Autowired
	private KlinikaService klinikaService;

	@Autowired
	private MedSestraService medicinskaSestraService;

	@Autowired
	private KorisnikService korisnikService;

	@Autowired
	private AdminKlinikeService adminKlinikeService;

	@Autowired
	private AutoritetService autoritetService;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN_KLINIKE', 'MED_SESTRA')")
	public ResponseEntity<MedSestraDTO> ucitajPoId(@PathVariable Long id) {
		MedSestra m = medicinskaSestraService.findOne(id);
		m.getKlinika();
		MedSestraDTO ms = new MedSestraDTO(m);
		return new ResponseEntity<>(ms, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	@GetMapping(value = "/ucitajSve")
	public ResponseEntity<List<MedSestraDTO>> ucitajSveMedicinskeSestre() {
		Authentication trenutniKorisnik = SecurityContextHolder.getContext().getAuthentication();
		Korisnik ulogovan = korisnikService.findByEmail(trenutniKorisnik.getName());

		List<MedSestra> medicinskeSestre = null;
		if (ulogovan.getAutoriteti().get(0).getIme().equals("ROLE_ADMIN_KLINIKE")) {
			AdminKlinike admin = adminKlinikeService.findByEmail(trenutniKorisnik.getName());
			medicinskeSestre = medicinskaSestraService.findAllByKlinika(admin.getKlinika());
		} else {
			medicinskeSestre = medicinskaSestraService.findAll();
		}

		List<MedSestraDTO> medSestraDTO = new ArrayList<>();
		for (MedSestra m : medicinskeSestre) {
			medSestraDTO.add(new MedSestraDTO(m));
		}
		return new ResponseEntity<>(medSestraDTO, HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<?> kreirajMedSestru(@RequestBody MedSestraDTO medSestraDTO) {
		MedSestra medSestra = new MedSestra();
		medSestra.setAdresa(medSestraDTO.getAdresa());
		medSestra.setDrzava(medSestraDTO.getDrzava());
		medSestra.setGrad(medSestraDTO.getGrad());
		medSestra.setEmail(medSestraDTO.getEmail());
		medSestra.setLozinka(korisnikService.encodePassword(medSestraDTO.getLozinka()));
		medSestra.setIme(medSestraDTO.getIme());
		medSestra.setPrezime(medSestraDTO.getPrezime());
		medSestra.setBrojTelefona(medSestraDTO.getBrojTelefona());
		medSestra.setPocetakRadnogVremena(medSestraDTO.getPocetakRadnogVremena());
		medSestra.setKrajRadnogVremena(medSestraDTO.getKrajRadnogVremena());
		medSestra.setAutoriteti(autoritetService.findByName("ROLE_LEKAR"));
		medSestra.setAktiviran(true);
		medSestra.setVerifikovan(true);
		medSestra.setPromenjenaLozinka(false);
		Klinika k = klinikaService.findByNaziv(medSestraDTO.getKlinika());
		medSestra.setKlinika(k);

		try {
			medSestra = medicinskaSestraService.save(medSestra);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Database error!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new MedSestraDTO(medSestra), HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json")
	@PreAuthorize("hasRole('MED_SESTRA')")
	public ResponseEntity<?> promeniKorisnika(@RequestBody MedSestraDTO msDTO) {

		MedSestra ms = medicinskaSestraService.findOne(msDTO.getId());
		if (ms == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		ms.setAdresa(msDTO.getAdresa());
		ms.setDrzava(msDTO.getDrzava());
		ms.setGrad(msDTO.getGrad());
		ms.setIme(msDTO.getIme());
		ms.setPrezime(msDTO.getPrezime());
		ms.setBrojTelefona(msDTO.getBrojTelefona());
		ms.setAktiviran(msDTO.isAktiviran());
		ms.setPromenjenaLozinka(msDTO.isPromenjenaLozinka());
		ms.setVerifikovan(msDTO.isVerifikovan());
		ms.setKlinika(klinikaService.findByNaziv(msDTO.getKlinika()));
		ms.setPocetakRadnogVremena(msDTO.getPocetakRadnogVremena());
		ms.setKrajRadnogVremena(msDTO.getKrajRadnogVremena());

		try {
			ms = medicinskaSestraService.save(ms);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Database error!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new MedSestraDTO(ms), HttpStatus.OK);
	}

}
