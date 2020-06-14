package klinika.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import klinika.dto.OperacijaDTO;
import klinika.dto.PretragaLekaraDTO;
import klinika.dto.SlobodanTerminOperacijaDTO;
import klinika.dto.ZakaziTerminLekarDTO;
import klinika.model.AdminKlinike;
import klinika.model.Lekar;
import klinika.model.Operacija;
import klinika.model.Pacijent;
import klinika.repository.AdminKlinikeRepository;
import klinika.repository.LekarRepository;
import klinika.repository.OperacijaRepository;
import klinika.repository.PacijentRepository;
import klinika.service.AdminKlinikeService;
import klinika.service.EmailService;
import klinika.service.OperacijaService;

@RestController
@RequestMapping(value = "operacije")
public class OperacijaController {

	@Autowired
	private OperacijaService operacijaService;

	@Autowired
	private OperacijaRepository operacijaRepository;

	@Autowired
	private AdminKlinikeService adminKlinikeService;

	@Autowired
	private AdminKlinikeRepository adminKlinikeRepository;

	@Autowired
	private PacijentRepository pacijentRepository;

	@Autowired
	private LekarRepository lekarRepository;

	@Autowired
	private EmailService emailService;

	@GetMapping(value = "ucitajSveOperacijeNaCekanju/{id}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<OperacijaDTO>> ucitajSveOperacijeNaCekanju(@PathVariable("id") long id) {
		AdminKlinike admin = adminKlinikeService.findOne(id);
		List<Operacija> operacije = operacijaService.findByKlinikaIdAndSalaIdAndVremeAfter(admin.getKlinika().getId(),
				new Date().getTime());

		List<OperacijaDTO> operacijeDTO = new ArrayList<>();
		for (Operacija op : operacije) {
			operacijeDTO.add(new OperacijaDTO(op));
		}
		return new ResponseEntity<>(operacijeDTO, HttpStatus.OK);
	}

	@GetMapping(value = "ucitajSveOperacijePacijenta/{id}")
	@PreAuthorize("hasRole('PACIJENT')")
	public ResponseEntity<List<OperacijaDTO>> ucitajSveOperacijePacijenta(@PathVariable("id") long id) {
		return new ResponseEntity<>(operacijaService.vratiOperacijePacijenta(id), HttpStatus.OK);
	}

	@GetMapping(value = "ucitajSveZakazaneOperacije/{id}")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<List<OperacijaDTO>> ucitajSveZakazaneOperacije(@PathVariable("id") long id) {
		AdminKlinike admin = adminKlinikeService.findOne(id);
		List<Operacija> operacije = operacijaService.findByOtkazanaAndKlinikaIdAndVremeAfter(false,
				admin.getKlinika().getId(), new Date().getTime());

		List<OperacijaDTO> operacijeDTO = new ArrayList<>();
		for (Operacija operacija : operacije) {
			operacijeDTO.add(new OperacijaDTO(operacija));
		}
		return new ResponseEntity<>(operacijeDTO, HttpStatus.OK);
	}

	@PostMapping(value = "dodijeliSaluOperaciji", consumes = "application/json")
	@PreAuthorize("hasRole('ADMIN_KLINIKE')")
	public ResponseEntity<Boolean> dodijeliSaluOperaciji(@RequestBody SlobodanTerminOperacijaDTO slobodanTerminDTO) {
		boolean ispravno = false;
		try {
			ispravno = operacijaService.dodijeliSalu(slobodanTerminDTO);
			if (ispravno) {
				Operacija operacija = operacijaRepository.findById(slobodanTerminDTO.getOperacijaId()).orElseGet(null);
				OperacijaDTO operacijaDTO = new OperacijaDTO(operacija);
				for (Lekar lekar : operacija.getLekari()) {
					try {
						emailService.obavestiLekaraZaOperaciju(operacijaDTO, "milan_marinkovic98@hotmail.com");
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				}
				try {
					emailService.obavestiPacijentaZaOperaciju(operacijaDTO, "milan_marinkovic98@hotmail.com");
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}

			}
		} catch (Exception e) {
			return new ResponseEntity<>(false, HttpStatus.OK);
		}
		return new ResponseEntity<>(ispravno, HttpStatus.OK);
	}

	@PutMapping(value = "otkaziOperacijuLekara")
	@PreAuthorize("hasRole('LEKAR')")
	public ResponseEntity<Boolean> otkaziOperacijuLekara(@RequestBody PretragaLekaraDTO pldto)
			throws MailException, InterruptedException {
		return new ResponseEntity<>(operacijaService.otkaziOperacijuLekara(pldto), HttpStatus.OK);
	}

	@PostMapping(value = "zakaziNoviTerminLekar", consumes = "application/json")
	@PreAuthorize("hasRole('LEKAR')")
	public ResponseEntity<Boolean> zakaziNoviTerminLekar(@RequestBody ZakaziTerminLekarDTO ztlDTO)
			throws MailException, InterruptedException {
		try {
			boolean odgovor = operacijaService.zakaziTerminLekar(ztlDTO);
			if (odgovor) {
				Pacijent pacijent = pacijentRepository.findById(ztlDTO.getPacijent()).orElseGet(null);
				Lekar l = lekarRepository.findById(ztlDTO.getLekar()).orElseGet(null);
				ArrayList<AdminKlinike> admini = adminKlinikeRepository.findAdminByKlinikaId(l.getKlinika().getId());
				if (admini != null) {
					for (AdminKlinike ak : admini) {
						String text = "Poštovani, \nPristigao je zahtev za zakazivanje operacije.\nPodaci o operaciji:\nPacijent: "
								+ pacijent.getIme() + " " + pacijent.getPrezime() + "\nVreme: "
								+ new Date(ztlDTO.getDatumiVreme()).toString() + "\nLekar: " + l.getIme() + " "
								+ l.getPrezime();
						try {
							emailService.posaljiEmail(ak.getEmail(), "Zahtev za zakazivanje operacije", text);
						} catch (Exception e) {
							System.err.println(e.getMessage());
						}
					}
				}
			}
			return new ResponseEntity<>(odgovor, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(false, HttpStatus.OK);
		}

	}
}
