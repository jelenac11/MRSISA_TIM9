package tim09.klinika.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim09.klinika.dto.PredefinisaniDTO;
import tim09.klinika.dto.PregledDTO;
import tim09.klinika.dto.PretragaKlinikeDTO;
import tim09.klinika.dto.PretragaLekaraDTO;
import tim09.klinika.dto.SlobodanTerminDTO;
import tim09.klinika.service.PregledService;

@RestController
@RequestMapping(value = "pregledi")
public class PregledController {

	@Autowired
	private PregledService pregledService;
	
	@PostMapping(value="dodajSlobodanTerminZaPregled",consumes="application/json")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<Boolean> dodajSlobodanTerminZaPregled(@RequestBody SlobodanTerminDTO slobodanTerminDTO){
		boolean uspesno=pregledService.insertPregled(slobodanTerminDTO);
		return new ResponseEntity<Boolean>(uspesno,HttpStatus.OK);
	}
	
	@PutMapping(value="/ucitajPredefinisanePreglede",consumes="application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<List<PredefinisaniDTO>> ucitajPredefinisane(@RequestBody PretragaKlinikeDTO pkdto){
		return new ResponseEntity<>(pregledService.ucitajPredefinisane(pkdto), HttpStatus.OK);
	}
	
	@PutMapping(value="/zakaziPredefinisani",consumes="application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<Boolean> zakaziPredefinisani(@RequestBody PredefinisaniDTO predef) throws MailException, InterruptedException{
		return new ResponseEntity<>(pregledService.zakaziPredefinisani(predef), HttpStatus.OK);
	}
	
	@PutMapping(value = "/zakaziTermin", consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<PredefinisaniDTO> zakaziTermin(@RequestBody PretragaLekaraDTO pldto){
		return new ResponseEntity<>(pregledService.zakaziTermin(pldto), HttpStatus.OK);
	}
	
	@PutMapping(value = "/potvrdiZakazivanje", consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<Boolean> potvrdiZakazivanje(@RequestBody PredefinisaniDTO predef) throws MailException, InterruptedException {
		return new ResponseEntity<>(pregledService.potvrdiZakazivanje(predef), HttpStatus.OK);
	}
}
