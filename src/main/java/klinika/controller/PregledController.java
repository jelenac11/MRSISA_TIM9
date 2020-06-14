package klinika.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
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

import klinika.dto.KlinikaDTO;
import klinika.dto.LekarDTO;
import klinika.dto.LekarPacijentDTO;
import klinika.dto.PredefinisaniDTO;
import klinika.dto.PregledDTO;
import klinika.dto.PretragaKlinikeDTO;
import klinika.dto.PretragaLekaraDTO;
import klinika.dto.SlobodanTerminDTO;
import klinika.dto.ZakaziTerminLekarDTO;
import klinika.model.AdminKlinike;
import klinika.model.Klinika;
import klinika.model.Korisnik;
import klinika.model.Lekar;
import klinika.model.Pacijent;
import klinika.model.Pregled;
import klinika.repository.AdminKlinikeRepository;
import klinika.repository.PacijentRepository;
import klinika.service.AdminKlinikeService;
import klinika.service.EmailService;
import klinika.service.KorisnikService;
import klinika.service.LekarService;
import klinika.service.PregledService;

@RestController
@RequestMapping(value = "pregledi")
public class PregledController {

	@Autowired
	private PregledService pregledService;

	@Autowired
	private KorisnikService korisnikService;

	@Autowired
	private AdminKlinikeService adminKlinikeService;
	
	@Autowired 
	private EmailService emailService;
	
	@Autowired
	private AdminKlinikeRepository adminKlinikeRepository;
	
	@Autowired
	private LekarService lekarService;
	
	@Autowired
	private PacijentRepository pacijentRepository;
	

	
	@PostMapping(value = "dodajSlobodanTerminZaPregled", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<Boolean> dodajSlobodanTerminZaPregled(@RequestBody SlobodanTerminDTO slobodanTerminDTO) {
		try {
			boolean uspesno = pregledService.insertPregled(slobodanTerminDTO);
			return new ResponseEntity<>(uspesno, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(false, HttpStatus.OK);
		}
	}

	@PostMapping(value = "mozeDaOceniKliniku", consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<Boolean> mozeDaOceniKliniku(@RequestBody KlinikaDTO klinikaDTO) {
		Authentication trenutniKorisnik = SecurityContextHolder.getContext().getAuthentication();
		Korisnik pacijent = korisnikService.findByEmail(trenutniKorisnik.getName());

		List<Pregled> pregledi = pregledService.findByKlinikaIdAndPacijentIdAndVremeBeforeAndOtkazan(klinikaDTO.getId(),
				pacijent.getId(), new Date().getTime(), false);
		return new ResponseEntity<>(!pregledi.isEmpty(), HttpStatus.OK);
	}

	@PostMapping(value = "mozeDaOceniLekara", consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<Boolean> mozeDaOceniLekara(@RequestBody LekarDTO lekarDTO) {
		Authentication trenutniKorisnik = SecurityContextHolder.getContext().getAuthentication();
		Korisnik pacijent = korisnikService.findByEmail(trenutniKorisnik.getName());

		List<Pregled> pregledi = pregledService.findByLekarIdAndPacijentIdAndVremeBeforeAndOtkazan(lekarDTO.getId(),
				pacijent.getId(), new Date().getTime(), false);
		return new ResponseEntity<>(!pregledi.isEmpty(), HttpStatus.OK);
	}

	@PutMapping(value = "/otkaziPregled", consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<Boolean> otkaziPregled(@RequestBody PregledDTO pregled)
			throws MailException, InterruptedException {
		return new ResponseEntity<>(pregledService.otkaziPregledPacijenta(pregled), HttpStatus.OK);
	}

	@GetMapping(value = "ucitajSvePregledePacijenta/{id}")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<List<PregledDTO>> ucitajSvePregledePacijenta(@PathVariable("id") long id) {
		return new ResponseEntity<>(pregledService.vratiPregledePacijenta(id), HttpStatus.OK);
	}

	@PutMapping(value = "/ucitajPredefinisanePreglede", consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<List<PredefinisaniDTO>> ucitajPredefinisane(@RequestBody PretragaKlinikeDTO pkdto) {
		return new ResponseEntity<>(pregledService.ucitajPredefinisane(pkdto), HttpStatus.OK);
	}

	@PostMapping(value = "dodijeliSaluPregledu", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<Boolean> dodijeliSaluPregledu(@RequestBody SlobodanTerminDTO slobodanTerminDTO) {
		boolean ispravno = true;
		try {
			ispravno = pregledService.dodijeliSalu(slobodanTerminDTO);
			if(ispravno) {
				Pregled p=pregledService.findOne(slobodanTerminDTO.getPregledId());
				try{
					emailService.posaljiLinkPotvrdePregleda(new PregledDTO(p), "aleksa.goljovic4@gmail.com");
				}
				catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		catch (Exception e) {
			return new ResponseEntity<>(false, HttpStatus.OK);
		}
		return new ResponseEntity<>(ispravno, HttpStatus.OK);
	}

	@PutMapping(value = "/zakaziPredefinisani", consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<Boolean> zakaziPredefinisani(@RequestBody PredefinisaniDTO predef)
			throws MailException, InterruptedException {
		try{
			boolean odgovor=pregledService.zakaziPredefinisani(predef);
			if(odgovor){
				String text = "Poštovani, \nUspešno ste zakazali pregled.\nPodaci o pregledu: \nKlinika: "
						+ predef.getLekar().getKlinika() + "\nLokacija: " + predef.getLokacija().getLokacija() + "\nVreme: "
						+ new Date(predef.getDatum()).toString() + "\nLekar: " + predef.getLekar().getIme() + " "
						+ predef.getLekar().getPrezime() + "\nTip pregleda: " + predef.getTip().getNaziv() + "\nBroj sale: "
						+ predef.getSala() + "\nCena: " + (predef.getCena() / 100.00) * (100 - predef.getPopust());
				try{
					emailService.posaljiEmail("aleksa.goljovic4@gmail.com", "Potvrda o zakazanom pregledu", text);
				}
				catch (Exception e) {
					// TODO: handle exception
				}
			
			}
			return new ResponseEntity<>(odgovor, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(false, HttpStatus.OK);
			
		}
	}

	@PutMapping(value = "/zakaziTermin", consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<PredefinisaniDTO> zakaziTermin(@RequestBody PretragaLekaraDTO pldto) {
		return new ResponseEntity<>(pregledService.zakaziTermin(pldto), HttpStatus.OK);
	}

	@PutMapping(value = "/potvrdiZakazivanje", consumes = "application/json")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<Boolean> potvrdiZakazivanje(@RequestBody PredefinisaniDTO predef)
			throws MailException, InterruptedException {
		try {
			boolean odgovor= pregledService.potvrdiZakazivanje(predef);
			if(odgovor) {
				ArrayList<AdminKlinike> admini = adminKlinikeRepository.findAdminByKlinikaId(predef.getLokacija().getId());
				if (admini != null) {
					for (AdminKlinike ak : admini) {
						String text = "Poštovani, \nPristigao je zahtev za zakazivanje pregleda.\nPodaci o pregledu:\nPacijent: "
								+ predef.getPacijent().getEmail() + "\nVreme: " + new Date(predef.getDatum()).toString()
								+ "\nLekar: " + predef.getLekar().getIme() + " " + predef.getLekar().getPrezime()
								+ "\nTip pregleda: " + predef.getTip().getNaziv();
						try{
							emailService.posaljiEmail("aleksa.goljovic4@gmail.com", "Zahtev za zakazivanje pregleda", text);
						}
						catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
			}
			return new ResponseEntity<>(odgovor, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(false, HttpStatus.OK);
			
		}
	}

	@GetMapping(value = "ucitajSveZakazanePreglede/{id}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<PregledDTO>> ucitajSveZakazanePreglede(@PathVariable("id") long id) {
		AdminKlinike admin = adminKlinikeService.findOne(id);
		List<Pregled> pregledi = pregledService.findByOtkazanAndZauzetAndKlinikaIdAndVremeAfterAndPotvrdjen(
				admin.getKlinika().getId(), false, true, new Date().getTime(), true);

		List<PregledDTO> preglediDTO = new ArrayList<>();
		for (Pregled pregled : pregledi) {
			preglediDTO.add(new PregledDTO(pregled));
		}
		return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
	}

	@GetMapping(value = "ucitajSveSlobodnePreglede/{id}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<PregledDTO>> ucitajSveSlobodnePreglede(@PathVariable("id") long id) {
		AdminKlinike admin = adminKlinikeService.findOne(id);
		List<Pregled> pregledi = pregledService
				.findByKlinikaIdAndVremeAfterAndPacijentIsNull(admin.getKlinika().getId(), new Date().getTime());

		List<PregledDTO> preglediDTO = new ArrayList<>();
		for (Pregled pregled : pregledi) {
			preglediDTO.add(new PregledDTO(pregled));
		}
		return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
	}

	@GetMapping(value = "ucitajSvePregledeNaCekanju/{id}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<PregledDTO>> ucitajSvePregledeNaCekanju(@PathVariable("id") long id) {
		AdminKlinike admin = adminKlinikeService.findOne(id);
		List<Pregled> pregledi = pregledService.findByKlinikaIdAndSalaIdAndVremeAfterAndPotvrdjen(
				admin.getKlinika().getId(), new Date().getTime(), false);

		List<PregledDTO> preglediDTO = new ArrayList<>();
		for (Pregled pregled : pregledi) {
			preglediDTO.add(new PregledDTO(pregled));
		}
		return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/provjeraPregledaZaPacijentaOdLekara")
	@PreAuthorize("hasRole('LEKAR')")
	public ResponseEntity<Boolean> provjeraPregledaZaPacijentaOdLekara(@RequestBody LekarPacijentDTO lekarPacijentDTO) {
		List<Pregled> pregledi = pregledService.findByLekarIdAndPacijentIdAndVreme(lekarPacijentDTO.getIdLekara(),
				lekarPacijentDTO.getIdPacijenta(), new Date().getTime());
		boolean odgovor;
		if (pregledi.isEmpty()) {
			odgovor = false;
		} else {
			odgovor = true;
		}
		return new ResponseEntity<>(odgovor, HttpStatus.OK);

	}

	@PostMapping(value = "/mozeZapocetiPregled")
	@PreAuthorize("hasRole('LEKAR')")
	public ResponseEntity<Boolean> mozeZapocetiPregled(@RequestBody LekarPacijentDTO lekarPacijentDTO) {
		Pregled pregled = pregledService.mozeZapocetiPregled(lekarPacijentDTO.getIdLekara(),
				lekarPacijentDTO.getIdPacijenta(), new Date().getTime());
		boolean odgovor;
		if (pregled == null) {
			odgovor = false;
		} else {
			odgovor = true;
		}
		return new ResponseEntity<>(odgovor, HttpStatus.OK);

	}

	@PostMapping(value = "/dobaviPregled")
	@PreAuthorize("hasRole('LEKAR')")
	public ResponseEntity<PregledDTO> dobaviPregled(@RequestBody LekarPacijentDTO lekarPacijentDTO) {
		Pregled pregled = pregledService.dobaviPregled(lekarPacijentDTO.getIdLekara(),
				lekarPacijentDTO.getIdPacijenta(), new Date().getTime());
		return new ResponseEntity<>(new PregledDTO(pregled), HttpStatus.OK);
	}

	@PostMapping(value = "/daLiJeZavrsen")
	@PreAuthorize("hasRole('LEKAR')")
	public ResponseEntity<Boolean> daLiJeZavrsen(@RequestBody LekarPacijentDTO lekarPacijentDTO) {
		Pregled pregled = pregledService.dobaviPregled(lekarPacijentDTO.getIdLekara(),
				lekarPacijentDTO.getIdPacijenta(), new Date().getTime());
		boolean zavrsen = false;
		if (pregled == null) {
			zavrsen = true;
		} else {
			if (pregled.getIzvestaj() != null) {
				zavrsen = true;
			}
		}
		return new ResponseEntity<>(zavrsen, HttpStatus.OK);

	}

	@PutMapping(value = "/otkaziPregledLekara", consumes = "application/json")
	@PreAuthorize("hasRole('LEKAR')")
	public ResponseEntity<Boolean> otkaziPregledLekara(@RequestBody PretragaLekaraDTO pldto)
			throws MailException, InterruptedException {
		return new ResponseEntity<>(pregledService.otkaziPregledLekara(pldto), HttpStatus.OK);
	}

	@PostMapping(value = "zakaziNoviTerminLekar", consumes = "application/json")
	@PreAuthorize("hasRole('LEKAR')")
	public ResponseEntity<Boolean> zakaziNoviTerminLekar(@RequestBody ZakaziTerminLekarDTO ztlDTO)
			throws MailException, InterruptedException {
		try{
			boolean odgovor = pregledService.zakaziTerminLekar(ztlDTO);
			if(odgovor) {
				Klinika klinika = lekarService.findOne(ztlDTO.getLekar()).getKlinika();
				ArrayList<AdminKlinike> admini = adminKlinikeRepository.findAdminByKlinikaId(klinika.getId());
				if (admini != null) {
					Pacijent pacijent = pacijentRepository.getOne(ztlDTO.getPacijent());
					Lekar l = lekarService.findOne(ztlDTO.getLekar());
					for (AdminKlinike ak : admini) {
						String text = "Poštovani, \nPristigao je zahtev za zakazivanje pregleda.\nPodaci o pregledu:\nPacijent: "
								+ pacijent.getIme() + " " + pacijent.getPrezime() + "\nVreme: "
								+ new Date(ztlDTO.getDatumiVreme()).toString() + "\nLekar: " + l.getIme() + " " + l.getPrezime()
								+ "\nTip pregleda: " + ztlDTO.getTipPregleda().getNaziv();
						try{
							emailService.posaljiEmail(ak.getEmail(), "Zahtev za zakazivanje pregleda", text);
						}
						catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
			}
			return new ResponseEntity<>(odgovor, HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(false, HttpStatus.OK);
			
		}

	}
}
