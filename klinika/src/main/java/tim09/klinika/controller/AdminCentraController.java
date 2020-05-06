package tim09.klinika.controller;

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

import tim09.klinika.dto.AdminCentraDTO;
import tim09.klinika.dto.KorisnikDTO;
import tim09.klinika.model.AdminCentra;
import tim09.klinika.model.Korisnik;
import tim09.klinika.service.AdminCentraService;
import tim09.klinika.service.AutoritetService;
import tim09.klinika.service.KorisnikService;

@RestController
@RequestMapping(value = "adminiCentra")
public class AdminCentraController {

	@Autowired
	private AdminCentraService adminCentraService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private AutoritetService autoritetService;
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN_KLINICKOG_CENTRA')")
	public ResponseEntity<AdminCentraDTO> ucitajPoId(@PathVariable Long id) {
		AdminCentra a = adminCentraService.findOne(id);
		AdminCentraDTO ac = new AdminCentraDTO(a);
		return new ResponseEntity<AdminCentraDTO>(ac, HttpStatus.OK);
	}
	
	@GetMapping(value = "/ucitajSve")
	@PreAuthorize("hasRole('ADMIN_KLINICKOG_CENTRA')")
	public ResponseEntity<List<AdminCentraDTO>> ucitajSveAdmineCentra() {
		List<AdminCentra> adminiCentra = adminCentraService.findAll();

		List<AdminCentraDTO> adminCentraDTO = new ArrayList<>();
		for (AdminCentra a : adminiCentra) {
			adminCentraDTO.add(new AdminCentraDTO(a));
		}
		return new ResponseEntity<>(adminCentraDTO, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINICKOG_CENTRA')")
	public ResponseEntity<AdminCentraDTO> kreirajAdminaCentra(@RequestBody AdminCentraDTO adminCentraDTO) {
		AdminCentra adminCentra = new AdminCentra();
		adminCentra.setAdresa(adminCentraDTO.getAdresa());
		adminCentra.setDrzava(adminCentraDTO.getDrzava());
		adminCentra.setGrad(adminCentraDTO.getGrad());
		adminCentra.setEmail(adminCentraDTO.getEmail());
		adminCentra.setIme(adminCentraDTO.getIme());
		adminCentra.setLozinka(korisnikService.encodePassword(adminCentraDTO.getLozinka()));
		adminCentra.setPrezime(adminCentraDTO.getPrezime());
		adminCentra.setBrojTelefona(adminCentraDTO.getBrojTelefona());
		adminCentra.setAutoriteti(autoritetService.findByName("ROLE_ADMIN_KLINICKOG_CENTRA"));
		adminCentra.setAktiviran(true);
		adminCentra.setVerifikovan(true);
		adminCentra.setPromenjenaLozinka(false);

		adminCentra = adminCentraService.save(adminCentra);
		return new ResponseEntity<>(new AdminCentraDTO(adminCentra), HttpStatus.CREATED);
	}
	
	@PutMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINICKOG_CENTRA')")
	public ResponseEntity<AdminCentraDTO> promeniKorisnika(@RequestBody AdminCentraDTO korisnikDTO) {

		AdminCentra korisnik = adminCentraService.findOne(korisnikDTO.getId());
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

		korisnik = adminCentraService.save(korisnik);
		return new ResponseEntity<>(new AdminCentraDTO(korisnik), HttpStatus.OK);
	}
}
