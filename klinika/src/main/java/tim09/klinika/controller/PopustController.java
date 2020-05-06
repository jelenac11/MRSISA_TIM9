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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim09.klinika.dto.PopustDTO;
import tim09.klinika.model.AdminKlinike;
import tim09.klinika.model.Popust;
import tim09.klinika.model.StavkaCenovnika;
import tim09.klinika.model.TipPregleda;
import tim09.klinika.service.AdminKlinikeService;
import tim09.klinika.service.PopustService;
import tim09.klinika.service.StavkaCenovnikaService;
import tim09.klinika.service.TipPregledaService;

@RestController
@RequestMapping(value = "popusti")
public class PopustController {

	@Autowired
	private PopustService popustService;
	
	@Autowired
	private AdminKlinikeService adminKlinikeService;
	
	@Autowired
	private TipPregledaService tipPregledaService;
	
	@Autowired
	private StavkaCenovnikaService stavkaCenovnikaService;
	
	@GetMapping(value = "/ucitajSve")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<PopustDTO>> ucitajSvePopuste() {
		Authentication trenutniKorisnik = SecurityContextHolder.getContext().getAuthentication();
		AdminKlinike ulogovan = adminKlinikeService.findByEmail(trenutniKorisnik.getName());
		
		List<Popust> popusti = popustService.findAllByKlinikaId(ulogovan.getKlinika().getId());

		List<PopustDTO> popustDTO = new ArrayList<>();
		for (Popust p : popusti) {
			popustDTO.add(new PopustDTO(p));
		}
		return new ResponseEntity<>(popustDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/dodaj")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<Popust> kreirajPopust(@RequestBody PopustDTO popustDTO) {
		Authentication trenutniKorisnik = SecurityContextHolder.getContext().getAuthentication();
		AdminKlinike ulogovan = adminKlinikeService.findByEmail(trenutniKorisnik.getName());
		
		Popust p = new Popust();
		p.setKlinika(ulogovan.getKlinika());
		p.setKraj(popustDTO.getKraj());
		p.setPocetak(popustDTO.getPocetak());
		p.setProcenat(popustDTO.getProcenat());
		
		TipPregleda tp = tipPregledaService.findOneByNaziv(popustDTO.getTipPregleda());
		StavkaCenovnika sc = stavkaCenovnikaService.findByTipPregleda(tp);
		p.setStavkaCenovnika(sc);
		sc.getPopusti().add(p);
		
		return new ResponseEntity<>(popustService.save(p), HttpStatus.OK);
	}
	
	@PutMapping(value = "/izmeni")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<Popust> izmeniPopust(@RequestBody PopustDTO popustDTO) {
		Authentication trenutniKorisnik = SecurityContextHolder.getContext().getAuthentication();
		AdminKlinike ulogovan = adminKlinikeService.findByEmail(trenutniKorisnik.getName());
		
		Popust p = popustService.findOne(popustDTO.getId());
		p.setKlinika(ulogovan.getKlinika());
		p.setKraj(popustDTO.getKraj());
		p.setPocetak(popustDTO.getPocetak());
		p.setProcenat(popustDTO.getProcenat());
		
		TipPregleda tp = tipPregledaService.findOneByNaziv(popustDTO.getTipPregleda());
		p.setStavkaCenovnika(stavkaCenovnikaService.findByTipPregleda(tp));
		
		return new ResponseEntity<>(popustService.save(p), HttpStatus.OK);
	}
}
