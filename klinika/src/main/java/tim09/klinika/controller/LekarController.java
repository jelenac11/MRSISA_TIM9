package tim09.klinika.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import tim09.klinika.dto.AdminKlinikeDTO;
import tim09.klinika.dto.KlinikaDTO;
import tim09.klinika.dto.LekarDTO;
import tim09.klinika.dto.PretragaLekaraDTO;
import tim09.klinika.dto.SlobodanTerminDTO;
import tim09.klinika.dto.TipPregledaDTO;
import tim09.klinika.model.AdminKlinike;
import tim09.klinika.model.Autoritet;
import tim09.klinika.model.Klinika;
import tim09.klinika.model.Korisnik;
import tim09.klinika.model.Lekar;
import tim09.klinika.model.TipPregleda;
import tim09.klinika.service.AdminKlinikeService;
import tim09.klinika.service.AutoritetService;
import tim09.klinika.service.KlinikaService;
import tim09.klinika.service.KorisnikService;
import tim09.klinika.service.LekarService;
import tim09.klinika.service.TipPregledaService;

@RestController
@RequestMapping(value = "lekari")
public class LekarController {

	@Autowired
	private LekarService lekarService;

	@Autowired
	private KorisnikService korisnikService;

	@Autowired
	private AdminKlinikeService adminKlinikeService;
	
	@Autowired
	private TipPregledaService tipPregledaService;

	@Autowired
	private AutoritetService autoritetService;

	@Autowired
	private KlinikaService klinikaService;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('PACIJENT', 'ADMIN_KLINIKE', 'LEKAR', 'MED_SESTRA')")
	public ResponseEntity<LekarDTO> ucitajPoId(@PathVariable Long id) {
		Lekar l = lekarService.findOne(id);
		l.getKlinika();
		LekarDTO le = new LekarDTO(l);
		return new ResponseEntity<LekarDTO>(le, HttpStatus.OK);
	}

	@GetMapping(value = "/ucitajSve")
	@PreAuthorize("hasAnyRole('PACIJENT', 'ADMIN_KLINIKE')")
	public ResponseEntity<List<LekarDTO>> ucitajSveLekare() {
		Authentication trenutniKorisnik = SecurityContextHolder.getContext().getAuthentication();
		Korisnik ulogovan = korisnikService.findByEmail(trenutniKorisnik.getName());

		List<Lekar> lekari = null;
		if (ulogovan.getAutoriteti().get(0).getIme().equals("ROLE_ADMIN_KLINIKE")) {
			AdminKlinike admin = adminKlinikeService.findByEmail(trenutniKorisnik.getName());
			lekari = lekarService.findAllByKlinika(admin.getKlinika());
		} else {
			lekari = lekarService.findAll();
		}

		List<LekarDTO> lekarDTO = new ArrayList<>();
		for (Lekar l : lekari) {
			lekarDTO.add(new LekarDTO(l));
		}
		return new ResponseEntity<>(lekarDTO, HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<LekarDTO> kreirajLekara(@RequestBody LekarDTO lekarDTO) {
		Lekar lekar = new Lekar();
		lekar.setAdresa(lekarDTO.getAdresa());
		lekar.setDrzava(lekarDTO.getDrzava());
		lekar.setGrad(lekarDTO.getGrad());
		lekar.setEmail(lekarDTO.getEmail());
		lekar.setIme(lekarDTO.getIme());
		lekar.setLozinka(korisnikService.encodePassword(lekarDTO.getLozinka()));
		lekar.setPrezime(lekarDTO.getPrezime());
		lekar.setBrojTelefona(lekarDTO.getBrojTelefona());
		lekar.setPocetakRadnogVremena(lekarDTO.getPocetakRadnogVremena());
		lekar.setKrajRadnogVremena(lekarDTO.getKrajRadnogVremena());
		lekar.setAutoriteti(autoritetService.findByName("ROLE_LEKAR"));
		lekar.setAktiviran(true);
		lekar.setVerifikovan(true);
		lekar.setPromenjenaLozinka(false);
		Klinika k = klinikaService.findByNaziv(lekarDTO.getKlinika());
		lekar.setKlinika(k);
		lekar = lekarService.save(lekar);
		lekar.setSpecijalnosti(new HashSet<TipPregleda>());
		for (TipPregledaDTO tpDTO : lekarDTO.getSpecijalnosti()) {
			lekar.getSpecijalnosti().add(tipPregledaService.findOneByNaziv(tpDTO.getNaziv()));
		}
		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json")
	@PreAuthorize("hasRole('LEKAR')")
	public ResponseEntity<LekarDTO> promeniKorisnika(@RequestBody LekarDTO korisnikDTO) {

		Lekar korisnik = lekarService.findOne(korisnikDTO.getId());
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

		korisnik = lekarService.save(korisnik);
		return new ResponseEntity<>(new LekarDTO(korisnik), HttpStatus.OK);
	}
	
	@PostMapping(value="dobaviSlobodneLekareZaPregled",consumes="application/json")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<LekarDTO>> dobaviSlobodneLekareZaPregled(@RequestBody SlobodanTerminDTO slobodanTerminDTO){
		AdminKlinike admin=adminKlinikeService.findOne(slobodanTerminDTO.getIdAdmina());
		Klinika k=admin.getKlinika();
		List<Lekar> lekari=lekarService.findByIdKlinikaAndVremeAndTipPregleda(k.getId(),slobodanTerminDTO.getDatumiVreme(),slobodanTerminDTO.getTipPregleda(),slobodanTerminDTO.getTrajanje());
		List<LekarDTO> lekariDTO=new ArrayList<LekarDTO>();
		for(Lekar lekar:lekari) {
			lekariDTO.add(new LekarDTO(lekar));
		}
		return new ResponseEntity<List<LekarDTO>>(lekariDTO,HttpStatus.OK);
	}
	
	@PutMapping(value="/vratiSlobodneTermine",consumes="application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<List<Long>> vratiSlobodneTermine(@RequestBody PretragaLekaraDTO pldto){
		return new ResponseEntity<>(lekarService.vratiSlobodneTermine(pldto), HttpStatus.OK);
	}
	
	@GetMapping("/ucitajSveLekareKlinike/{id}")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<List<LekarDTO>> ucitajLekareKlinike(@PathVariable Long id) {
		return new ResponseEntity<>(lekarService.vratiLekareKlinike(id), HttpStatus.OK);
	}
	
	@PutMapping(value = "/proveriTipIVremeLekara", consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<Boolean> imaLiVremenaLekar(@RequestBody PretragaLekaraDTO pldto){
		return new ResponseEntity<>(lekarService.proveriTipIVreme(pldto), HttpStatus.OK);
	}
}
