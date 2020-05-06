package tim09.klinika.controller;

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

import tim09.klinika.dto.LekarDTO;
import tim09.klinika.dto.MedSestraDTO;
import tim09.klinika.model.AdminKlinike;
import tim09.klinika.model.Klinika;
import tim09.klinika.model.Korisnik;
import tim09.klinika.model.Lekar;
import tim09.klinika.model.MedSestra;
import tim09.klinika.service.AdminKlinikeService;
import tim09.klinika.service.AutoritetService;
import tim09.klinika.service.KlinikaService;
import tim09.klinika.service.KorisnikService;
import tim09.klinika.service.MedSestraService;

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
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<MedSestraDTO> ucitajPoId(@PathVariable Long id) {
		MedSestra m = medicinskaSestraService.findOne(id);
		m.getKlinika();
		MedSestraDTO ms = new MedSestraDTO(m);
		return new ResponseEntity<MedSestraDTO>(ms, HttpStatus.OK);
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
	public ResponseEntity<MedSestraDTO> kreirajMedSestru(@RequestBody MedSestraDTO medSestraDTO) {
		MedSestra medSestra = new MedSestra();
		medSestra.setAdresa(medSestraDTO.getAdresa());
		medSestra.setDrzava(medSestraDTO.getDrzava());
		medSestra.setGrad(medSestraDTO.getGrad());
		medSestra.setEmail(medSestraDTO.getEmail());
		medSestra.setIme(medSestraDTO.getIme());
		medSestra.setLozinka(korisnikService.encodePassword(medSestraDTO.getLozinka()));
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

		medSestra = medicinskaSestraService.save(medSestra);
		return new ResponseEntity<>(new MedSestraDTO(medSestra), HttpStatus.CREATED);
	}
	
	@PutMapping(consumes = "application/json")
	@PreAuthorize("hasRole('MED_SESTRA')")
	public ResponseEntity<MedSestraDTO> promeniKorisnika(@RequestBody MedSestraDTO korisnikDTO) {

		MedSestra korisnik = medicinskaSestraService.findOne(korisnikDTO.getId());
		if (korisnik == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		korisnik.setAdresa(korisnikDTO.getAdresa());
		korisnik.setDrzava(korisnikDTO.getDrzava());
		korisnik.setGrad(korisnikDTO.getGrad());
		korisnik.setIme(korisnikDTO.getIme());
		korisnik.setLozinka(korisnikService.encodePassword(korisnikDTO.getLozinka()));
		korisnik.setPrezime(korisnikDTO.getPrezime());
		korisnik.setBrojTelefona(korisnikDTO.getBrojTelefona());
		korisnik.setAktiviran(korisnikDTO.isAktiviran());
		korisnik.setPromenjenaLozinka(korisnikDTO.isPromenjenaLozinka());
		korisnik.setVerifikovan(korisnikDTO.isVerifikovan());
		korisnik.setKlinika(klinikaService.findByNaziv(korisnikDTO.getKlinika()));
		korisnik.setPocetakRadnogVremena(korisnikDTO.getPocetakRadnogVremena());
		korisnik.setKrajRadnogVremena(korisnikDTO.getKrajRadnogVremena());

		korisnik = medicinskaSestraService.save(korisnik);
		return new ResponseEntity<>(new MedSestraDTO(korisnik), HttpStatus.OK);
	}

}
