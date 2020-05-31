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

import klinika.dto.AdminCentraDTO;
import klinika.model.AdminCentra;
import klinika.service.AdminCentraService;
import klinika.service.AutoritetService;
import klinika.service.KorisnikService;

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
		return new ResponseEntity<>(ac, HttpStatus.OK);
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
	public ResponseEntity<AdminCentraDTO> promeniKorisnika(@RequestBody AdminCentraDTO adminDTO) {
		AdminCentra admin = adminCentraService.findOne(adminDTO.getId());
		if (admin == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		admin.setAdresa(adminDTO.getAdresa());
		admin.setDrzava(adminDTO.getDrzava());
		admin.setGrad(adminDTO.getGrad());
		admin.setIme(adminDTO.getIme());
		admin.setLozinka(korisnikService.encodePassword(adminDTO.getLozinka()));
		admin.setPrezime(adminDTO.getPrezime());
		admin.setBrojTelefona(adminDTO.getBrojTelefona());
		admin.setAktiviran(adminDTO.isAktiviran());
		admin.setPromenjenaLozinka(adminDTO.isPromenjenaLozinka());
		admin.setVerifikovan(adminDTO.isVerifikovan());

		admin = adminCentraService.save(admin);
		return new ResponseEntity<>(new AdminCentraDTO(admin), HttpStatus.OK);
	}
}
