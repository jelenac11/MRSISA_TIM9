package klinika.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import klinika.dto.CenovnikDTO;
import klinika.model.AdminKlinike;
import klinika.model.Cenovnik;
import klinika.service.AdminKlinikeService;
import klinika.service.CenovnikService;

@RestController
@RequestMapping(value = "cenovnici")
public class CenovnikController {

	@Autowired
	private CenovnikService cenovnikService;

	@Autowired
	private AdminKlinikeService adminKlinikeService;

	@GetMapping("/ucitajCenovnik")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<CenovnikDTO> ucitajCenovnik() {
		Authentication trenutniKorisnik = SecurityContextHolder.getContext().getAuthentication();
		AdminKlinike ulogovan = adminKlinikeService.findByEmail(trenutniKorisnik.getName());

		Cenovnik c = cenovnikService.findOne(ulogovan.getKlinika().getId());
		CenovnikDTO cen = new CenovnikDTO(c);
		return new ResponseEntity<>(cen, HttpStatus.OK);
	}
}
