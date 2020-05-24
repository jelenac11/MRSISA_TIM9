package tim09.klinika.controller;

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

import tim09.klinika.dto.CenovnikDTO;
import tim09.klinika.model.AdminKlinike;
import tim09.klinika.model.Cenovnik;
import tim09.klinika.model.Korisnik;
import tim09.klinika.service.AdminKlinikeService;
import tim09.klinika.service.CenovnikService;

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
		return new ResponseEntity<CenovnikDTO>(cen, HttpStatus.OK);
	}
}
