package klinika.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import klinika.dto.LekarDTO;
import klinika.dto.OperacijaDTO;
import klinika.dto.PretragaLekaraDTO;
import klinika.dto.RadniKalendarDTO;
import klinika.dto.SlobodanTerminOperacijaDTO;
import klinika.dto.ZakaziTerminLekarDTO;
import klinika.model.AdminKlinike;
import klinika.model.Klinika;
import klinika.model.Lekar;
import klinika.model.Operacija;
import klinika.model.Pacijent;
import klinika.model.Sala;
import klinika.repository.AdminKlinikeRepository;
import klinika.repository.OperacijaRepository;
import klinika.repository.SalaRepository;

@Service
public class OperacijaService {

	@Autowired
	private OperacijaRepository operacijaRepository;

	@Autowired
	private SalaService salaService;
	
	@Autowired
	private SalaRepository salaRepository;

	@Autowired
	private LekarService lekarService;

	@Autowired
	private AdminKlinikeService adminKlinikeService;

	@Autowired
	private AdminKlinikeRepository adminKlinikeRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PacijentService pacijentService;

	public Operacija findOne(Long id) {
		return operacijaRepository.findById(id).orElseGet(null);
	}

	public List<Operacija> findAll() {
		return operacijaRepository.findAll();
	}

	public Operacija save(Operacija operacija) {
		return operacijaRepository.save(operacija);
	}

	public void remove(Long id) {
		operacijaRepository.deleteById(id);
	}

	public List<RadniKalendarDTO> kreirajRadniKalendar(Long id, long time) {
		List<Operacija> operacije = operacijaRepository.findBySalaIdAndVremeAfterAndOtkazana(id, time, false);
		List<RadniKalendarDTO> kalendar = new ArrayList<>();
		for (Operacija operacija : operacije) {
			long pacid = 0;
			if (operacija.getPacijent() != null) {
				pacid = operacija.getPacijent().getId();
			}
			kalendar.add(new RadniKalendarDTO(operacija.getVreme(), operacija.getVreme() + 3600000, "Operacija", pacid,
					"", "", "", ""));
		}
		return kalendar;
	}

	public List<RadniKalendarDTO> kreirajRadniKalendarRadnika(Long id) {
		List<Operacija> operacije = operacijaRepository.findByLekar(id);
		List<RadniKalendarDTO> kalendar = new ArrayList<>();
		for (Operacija operacija : operacije) {
			String sala = "";
			if (operacija.getSala() != null) {
				sala = operacija.getSala().getNaziv();
			}
			kalendar.add(new RadniKalendarDTO(operacija.getVreme(), operacija.getVreme() + 3600000, "Operacija",
					operacija.getPacijent().getId(),
					operacija.getPacijent().getIme() + " " + operacija.getPacijent().getPrezime(), "",
					ispisiLekare(operacija.getLekari()), sala));
		}
		return kalendar;
	}

	public List<Operacija> findByKlinikaIdAndSalaIdAndVremeAfter(Long id, long time) {
		return operacijaRepository.findByKlinikaIdAndSalaIdIsNullAndVremeAfterAndOtkazana(id, time, false);
	}

	@Transactional(readOnly = false)
	public Operacija dodijeliSalu(SlobodanTerminOperacijaDTO slobodanTerminDTO) {
		Sala sala = salaService.findOne(slobodanTerminDTO.getSala().getId());
		List<Sala> sale = salaService.findByIdKlinikaAndVreme(sala.getKlinika().getId(), slobodanTerminDTO.getDatumiVreme());
		boolean zauzeta=true;
		for(Sala s:sale) {
			if(s.getId()==sala.getId()) {
				zauzeta=false;
			}
		}
		if(zauzeta) {
			System.out.println("Sala je zauzeta.");
			return null;
		}
		Operacija operacija = operacijaRepository.findById(slobodanTerminDTO.getOperacijaId()).orElseGet(null);
		if(operacija.getSala()!=null) {
			System.out.println("Vec zauzeta sala.");
			return null;
		}
		operacija.setSala(sala);
		AdminKlinike ak = adminKlinikeService.findOne(slobodanTerminDTO.getIdAdmina());
		operacija.setKlinika(ak.getKlinika());
		operacija.setVreme(slobodanTerminDTO.getDatumiVreme());
		sala.setIzmjena(new Date().getTime());
		salaRepository.save(sala);
		
		for (LekarDTO lekarDTO : slobodanTerminDTO.getLekari()) {
			Lekar lekar = lekarService.findOne(lekarDTO.getId());
			operacija.getLekari().add(lekar);
			emailService.obavestiLekaraZaOperaciju(operacija, "milan_marinkovic98@hotmail.com");	
		}
		emailService.obavestiPacijentaZaOperaciju(operacija, "milan_marinkovic98@hotmail.com");

		operacijaRepository.save(operacija);
		return operacija;
	}

	public List<Operacija> findByOtkazanaAndKlinikaIdAndVremeAfter(boolean b, Long id, long time) {
		return operacijaRepository.findByOtkazanaAndKlinikaIdAndVremeAfterAndSalaIdIsNotNull(b, id, time);
	}

	public List<OperacijaDTO> vratiOperacijePacijenta(long id) {
		List<Operacija> operacije = operacijaRepository.findByPacijentIdAndOtkazanaFalseAndSalaIsNotNull(id);
		List<OperacijaDTO> operacijeDTO = new ArrayList<>();
		if (operacije != null) {
			for (Operacija o : operacije) {
				operacijeDTO.add(new OperacijaDTO(o));
			}
		}
		return operacijeDTO;
	}

	private String ispisiLekare(Set<Lekar> set) {
		StringBuilder retVal = new StringBuilder();
		for (Lekar lekar : set) {
			retVal.append(lekar.getIme() + " " + lekar.getPrezime() + ", ");
		}
		return retVal.toString().substring(0, retVal.toString().length() - 2);
	}

	public Boolean otkaziOperacijuLekara(PretragaLekaraDTO pldto) throws MailException, InterruptedException {
		Operacija op = operacijaRepository.findByVremeAndLekarId(pldto.getDatum(), pldto.getId());
		if (op != null) {
			op.setOtkazana(true);
			operacijaRepository.save(op);
			String to = op.getPacijent().getEmail();
			String slaBr = "";
			if (op.getSala() != null) {
				slaBr = op.getSala().getBroj() + "";
			}
			String text = "Poštovani,\nVaša operacija je otkazana.\nPodaci o operaciji: \nKlinika: "
					+ op.getKlinika().getNaziv() + "\nLokacija: " + op.getKlinika().getLokacija() + "\nVreme: "
					+ new Date(pldto.getDatum()).toString() + "\nBroj sale: " + slaBr + "\nLekari: "
					+ this.ispisiLekare(op.getLekari());
			emailService.posaljiEmail("aleksa.goljovic4@gmail.com", "Otkazana operacija", text);
			for (Lekar l : op.getLekari()) {
				if (l.getId() != pldto.getId()) {
					String tekst = "Poštovani,\nVaša operacija je otkazana.\nPodaci o operaciji:\nPacijent: "
							+ op.getPacijent().getIme() + " " + op.getPacijent().getPrezime() + "\nVreme: "
							+ new Date(pldto.getDatum()).toString() + "\nBroj sale: " + slaBr + "\nLekari: "
							+ this.ispisiLekare(op.getLekari());
					emailService.posaljiEmail("aleksa.goljovic4@gmail.com", "Otkazana operacija", tekst);
				}
			}
			return true;
		}
		return false;
	}

	public List<Operacija> dobaviSveOperacijeBezSale() {
		return operacijaRepository.dobaviSveOperacijeBezSale();
	}

	public Boolean zakaziTerminLekar(ZakaziTerminLekarDTO ztlDTO) throws MailException, InterruptedException {
		Klinika klinika = lekarService.findOne(ztlDTO.getLekar()).getKlinika();
		Pacijent pacijent = pacijentService.findOne(ztlDTO.getPacijent());
		Lekar l = lekarService.findOne(ztlDTO.getLekar());
		Set<Lekar> lekari = new HashSet<>();
		lekari.add(l);
		Operacija op = new Operacija();
		op.setKlinika(klinika);
		op.setOtkazana(false);
		op.setLekari(lekari);
		op.setPacijent(pacijent);
		op.setVreme(ztlDTO.getDatumiVreme());
		ArrayList<AdminKlinike> admini = adminKlinikeRepository.findAdminByKlinikaId(klinika.getId());
		if (admini != null) {
			for (AdminKlinike ak : admini) {
				String text = "Poštovani, \nPristigao je zahtev za zakazivanje operacije.\nPodaci o operaciji:\nPacijent: "
						+ pacijent.getIme() + " " + pacijent.getPrezime() + "\nVreme: "
						+ new Date(ztlDTO.getDatumiVreme()).toString() + "\nLekar: " + l.getIme() + " "
						+ l.getPrezime();
				emailService.posaljiEmail(ak.getEmail(), "Zahtev za zakazivanje operacije", text);
			}
		}
		operacijaRepository.save(op);
		return true;
	}

}
