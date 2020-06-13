package klinika.controller;

import java.util.ArrayList;
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

import klinika.dto.LekarDTO;
import klinika.dto.PretragaLekaraDTO;
import klinika.dto.SlobodanTerminDTO;
import klinika.dto.SlobodanTerminOperacijaDTO;
import klinika.dto.TipPregledaDTO;
import klinika.model.AdminKlinike;
import klinika.model.Klinika;
import klinika.model.Korisnik;
import klinika.model.Lekar;
import klinika.model.TipPregleda;
import klinika.service.AdminKlinikeService;
import klinika.service.AutoritetService;
import klinika.service.KlinikaService;
import klinika.service.KorisnikService;
import klinika.service.LekarService;
import klinika.service.TipPregledaService;

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
		return new ResponseEntity<>(le, HttpStatus.OK);
	}

	@GetMapping("/ucitajSveLekareKlinikeByAdmin/{id}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<LekarDTO>> ucitajLekareKlinikeByAdmin(@PathVariable Long id) {
		Klinika k = adminKlinikeService.findOne(id).getKlinika();
		return new ResponseEntity<>(lekarService.vratiLekareKlinike(k.getId()), HttpStatus.OK);
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
	public ResponseEntity<?> kreirajLekara(@RequestBody LekarDTO lekarDTO) {
		Lekar lekar = new Lekar();
		lekar.setAdresa(lekarDTO.getAdresa());
		lekar.setDrzava(lekarDTO.getDrzava());
		lekar.setGrad(lekarDTO.getGrad());
		lekar.setEmail(lekarDTO.getEmail());
		lekar.setLozinka(korisnikService.encodePassword(lekarDTO.getLozinka()));
		lekar.setIme(lekarDTO.getIme());
		lekar.setPrezime(lekarDTO.getPrezime());
		lekar.setBrojTelefona(lekarDTO.getBrojTelefona());
		lekar.setPocetakRadnogVremena(lekarDTO.getPocetakRadnogVremena());
		lekar.setKrajRadnogVremena(lekarDTO.getKrajRadnogVremena());
		lekar.setAutoriteti(autoritetService.findByName("ROLE_LEKAR"));
		lekar.setAktiviran(true);
		lekar.setAktivan(true);
		lekar.setVerifikovan(true);
		lekar.setPromenjenaLozinka(false);
		Klinika k = klinikaService.findByNaziv(lekarDTO.getKlinika());
		lekar.setKlinika(k);
		try {
			lekar = lekarService.save(lekar);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Database error!", HttpStatus.BAD_REQUEST);
		}
		lekar.setSpecijalnosti(new HashSet<>());
		for (TipPregledaDTO tpDTO : lekarDTO.getSpecijalnosti()) {
			lekar.getSpecijalnosti().add(tipPregledaService.findOneByNaziv(tpDTO.getNaziv()));
		}
		return new ResponseEntity<>(new LekarDTO(lekar), HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json")
	@PreAuthorize("hasRole('LEKAR')")
	public ResponseEntity<?> promeniKorisnika(@RequestBody LekarDTO lDTO) {
		Lekar l = lekarService.findOne(lDTO.getId());
		if (l == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		l.setAdresa(lDTO.getAdresa());
		l.setDrzava(lDTO.getDrzava());
		l.setGrad(lDTO.getGrad());
		l.setIme(lDTO.getIme());
		l.setPrezime(lDTO.getPrezime());
		l.setBrojTelefona(lDTO.getBrojTelefona());
		l.setAktiviran(lDTO.isAktiviran());
		l.setPromenjenaLozinka(lDTO.isPromenjenaLozinka());
		l.setVerifikovan(lDTO.isVerifikovan());
		l.setKlinika(klinikaService.findByNaziv(lDTO.getKlinika()));
		l.setPocetakRadnogVremena(lDTO.getPocetakRadnogVremena());
		l.setKrajRadnogVremena(lDTO.getKrajRadnogVremena());
		
		try {
			l = lekarService.save(l);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Database error!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new LekarDTO(l), HttpStatus.OK);
	}

	@PostMapping(value = "dobaviSlobodneLekareZaPregled", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<LekarDTO>> dobaviSlobodneLekareZaPregled(
			@RequestBody SlobodanTerminDTO slobodanTerminDTO) {
		AdminKlinike admin = adminKlinikeService.findOne(slobodanTerminDTO.getIdAdmina());
		Klinika k = admin.getKlinika();
		List<Lekar> lekari = lekarService.findByIdKlinikaAndVremeAndTipPregleda(k.getId(),
				slobodanTerminDTO.getDatumiVreme(), slobodanTerminDTO.getTipPregleda());
		List<LekarDTO> lekariDTO = new ArrayList<>();
		for (Lekar lekar : lekari) {
			lekariDTO.add(new LekarDTO(lekar));
		}
		return new ResponseEntity<>(lekariDTO, HttpStatus.OK);
	}

	@PostMapping(value = "dobaviSlobodneLekareZaOperaciju", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<LekarDTO>> dobaviSlobodneLekareZaPregled(
			@RequestBody SlobodanTerminOperacijaDTO slobodanTerminDTO) {
		AdminKlinike admin = adminKlinikeService.findOne(slobodanTerminDTO.getIdAdmina());
		Klinika k = admin.getKlinika();
		List<Lekar> lekari = lekarService.findByIdKlinikaAndVreme(k.getId(), slobodanTerminDTO.getDatumiVreme());
		List<LekarDTO> lekariDTO = new ArrayList<>();
		for (Lekar lekar : lekari) {
			lekariDTO.add(new LekarDTO(lekar));
		}
		return new ResponseEntity<>(lekariDTO, HttpStatus.OK);
	}

	@PutMapping(value = "/vratiSlobodneTermine", consumes = "application/json")
	@PreAuthorize("hasAnyRole('PACIJENT', 'ADMIN_KLINIKE', 'LEKAR')")
	public ResponseEntity<List<Long>> vratiSlobodneTermine(@RequestBody PretragaLekaraDTO pldto) {
		return new ResponseEntity<>(lekarService.vratiSlobodneTermine(pldto), HttpStatus.OK);
	}

	@GetMapping("/ucitajSveLekareKlinike/{id}")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<List<LekarDTO>> ucitajLekareKlinike(@PathVariable Long id) {
		return new ResponseEntity<>(lekarService.vratiLekareKlinike(id), HttpStatus.OK);
	}

	@PutMapping(value = "/proveriTipIVremeLekara", consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<Boolean> imaLiVremenaLekar(@RequestBody PretragaLekaraDTO pldto) {
		return new ResponseEntity<>(lekarService.proveriTipIVreme(pldto), HttpStatus.OK);
	}

	@PostMapping(value = "izbrisiLekara", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<Boolean> izbrisiLekara(@RequestBody LekarDTO lekarDTO) {
		boolean uspesno = lekarService.remove(lekarDTO.getId());
		return new ResponseEntity<>(uspesno, HttpStatus.OK);
	}

	@GetMapping("/dobaviTipovePregledaZaLekara/{id}")
	@PreAuthorize("hasAnyRole('LEKAR', 'ADMIN_KLINIKE')")
	public ResponseEntity<List<TipPregledaDTO>> dobaviTipovePregledaZaLekara(@PathVariable Long id) {
		Lekar l = lekarService.findOne(id);
		Set<TipPregleda> tip = l.getSpecijalnosti();
		List<TipPregledaDTO> dto = new ArrayList<>();
		for (TipPregleda pregled : tip) {
			dto.add(new TipPregledaDTO(pregled));
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@PutMapping(value = "/proveriGodisnji", consumes = "application/json")
	@PreAuthorize("hasRole('LEKAR')")
	public ResponseEntity<Boolean> proveriGodisnji(@RequestBody PretragaLekaraDTO pldto) {
		return new ResponseEntity<>(lekarService.proveriGodisnji(pldto), HttpStatus.OK);
	}
}
