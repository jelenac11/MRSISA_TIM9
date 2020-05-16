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

import tim09.klinika.dto.KlinikaDTO;
import tim09.klinika.dto.LekarDTO;
import tim09.klinika.dto.PretragaKlinikeDTO;
import tim09.klinika.model.Cenovnik;
import tim09.klinika.model.Klinika;
import tim09.klinika.model.Korisnik;
import tim09.klinika.service.KlinikaService;

@RestController
@RequestMapping(value = "klinike")
public class KlinikaController {

	@Autowired
	private KlinikaService klinikaService;
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN_KLINICKOG_CENTRA', 'PACIJENT', 'ADMIN_KLINIKE', 'LEKAR', 'MED_SESTRA')")
	public ResponseEntity<KlinikaDTO> ucitajPoId(@PathVariable Long id) {
		Klinika k = klinikaService.findOne(id);
		KlinikaDTO kl = new KlinikaDTO(k);
		return new ResponseEntity<KlinikaDTO>(kl, HttpStatus.OK);
	}
	
	@GetMapping(value = "/ucitajSve")
	@PreAuthorize("hasAnyRole('ADMIN_KLINICKOG_CENTRA', 'PACIJENT')")
	public ResponseEntity<List<KlinikaDTO>> ucitajSveKlinike() {
		List<Klinika> klinike = klinikaService.findAll();

		List<KlinikaDTO> klinikaDTO = new ArrayList<>();
		for (Klinika k : klinike) {
			klinikaDTO.add(new KlinikaDTO(k));
		}
		return new ResponseEntity<>(klinikaDTO, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINICKOG_CENTRA')")
	public ResponseEntity<KlinikaDTO> kreirajKliniku(@RequestBody KlinikaDTO klinikaDTO) {
		Klinika klinika = new Klinika();
		
		klinika.setNaziv(klinikaDTO.getNaziv());
		klinika.setLokacija(klinikaDTO.getLokacija());
		klinika.setOpis(klinikaDTO.getOpis());
		klinika.setCenovnik(new Cenovnik());

		klinika = klinikaService.save(klinika);
		return new ResponseEntity<>(new KlinikaDTO(klinika), HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/zadovoljavaTip", consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<Boolean> proveriTipZaKliniku(@RequestBody PretragaKlinikeDTO pretragaKlinikeDTO) {
		return new ResponseEntity<>(klinikaService.proveriTip(pretragaKlinikeDTO), HttpStatus.OK);
	}

	@GetMapping(value = "/proveriIme/{ime}")
	@PreAuthorize("hasRole('ADMIN_KLINICKOG_CENTRA')")
	public ResponseEntity<Boolean> proveriIme(@PathVariable("ime") String ime) {
		Klinika klinika = klinikaService.findByNaziv(ime);
		if (klinika != null) {
			return new ResponseEntity<>(true, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(false, HttpStatus.CREATED);
	}
	
	@PostMapping(value="/izmenaProfilaKlinike",consumes="application/json")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<Boolean> izmenaProfilaKlinike(@RequestBody KlinikaDTO klinikaDTO){
		
		boolean uspesno=klinikaService.update(klinikaDTO);
		
		return new ResponseEntity<Boolean>(uspesno,HttpStatus.OK);
	}
	
	@PutMapping(value="/ucitajSlobodneLekare",consumes="application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<List<LekarDTO>> ucitajSlobodneLekare(@RequestBody PretragaKlinikeDTO pkdto){
		return new ResponseEntity<>(klinikaService.vratiSlobodneLekare(pkdto), HttpStatus.OK);
	}
	
}
