package tim09.klinika.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import tim09.klinika.dto.KlinikaDTO;
import tim09.klinika.dto.KorisnikDTO;
import tim09.klinika.dto.LekarDTO;
import tim09.klinika.dto.PacijentDTO;
import tim09.klinika.dto.PredefinisaniDTO;
import tim09.klinika.dto.PregledDTO;
import tim09.klinika.dto.PretragaKlinikeDTO;
import tim09.klinika.dto.PretragaLekaraDTO;
import tim09.klinika.dto.RadniKalendarDTO;
import tim09.klinika.dto.SlobodanTerminDTO;
import tim09.klinika.dto.TipPregledaDTO;
import tim09.klinika.dto.ZakaziTerminLekarDTO;
import tim09.klinika.model.AdminKlinike;
import tim09.klinika.model.Klinika;
import tim09.klinika.model.Korisnik;
import tim09.klinika.model.Lekar;
import tim09.klinika.model.Pacijent;
import tim09.klinika.model.Popust;
import tim09.klinika.model.Pregled;
import tim09.klinika.model.Sala;
import tim09.klinika.model.TipPregleda;
import tim09.klinika.repository.AdminKlinikeRepository;
import tim09.klinika.repository.KlinikaRepository;
import tim09.klinika.repository.KorisnikRepository;
import tim09.klinika.repository.LekarRepository;
import tim09.klinika.repository.PacijentRepository;
import tim09.klinika.repository.PopustRepository;
import tim09.klinika.repository.PregledRepository;
import tim09.klinika.repository.TipPregledaRepository;

@Service
public class PregledService {

	@Autowired
	private PregledRepository pregledRepository;

	@Autowired
	private AdminKlinikeService adminKlinikeService;

	@Autowired
	private SalaService salaService;

	@Autowired
	private LekarService lekarService;

	@Autowired
	private TipPregledaRepository tipoviRepository;

	@Autowired
	private PopustRepository popustRepository;

	@Autowired
	private PacijentRepository pacijentRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private LekarRepository lekarRepository;

	@Autowired
	private AdminKlinikeRepository adminKlinikeRepository;

	@Autowired
	private KorisnikRepository korisniciRepository;

	@Autowired
	private KlinikaRepository klinikaRepository;

	public Pregled findOne(Long id) {
		return pregledRepository.findById(id).orElseGet(null);
	}

	public List<Pregled> findAll() {
		return pregledRepository.findAll();
	}

	public Pregled save(Pregled pregled) {
		return pregledRepository.save(pregled);
	}

	public void remove(Long id) {
		pregledRepository.deleteById(id);
	}

	public List<Pregled> findByTipPregledaIdAndVremeGreaterThan(long id, long vreme) {
		return pregledRepository.findByTipPregledaIdAndVremeGreaterThan(id, vreme);
	}

	public boolean insertPregled(SlobodanTerminDTO slobodanTerminDTO) {
		Klinika klinika = adminKlinikeService.findOne(slobodanTerminDTO.getIdAdmina()).getKlinika();
		int i = pregledRepository.insertPregled(slobodanTerminDTO.getLekar().getId(),
				slobodanTerminDTO.getTipPregleda().getId(), slobodanTerminDTO.getSala().getId(),
				slobodanTerminDTO.getDatumiVreme(), slobodanTerminDTO.getTrajanje(), klinika.getId());
		if (i == 1) {
			return true;
		}
		return false;
	}

	public List<Pregled> findByKlinikaIdAndVremeAfterAndPacijentIsNull(long id, long vreme) {
		return pregledRepository.findByKlinikaIdAndVremeAfterAndOtkazanFalseAndPacijentIsNull(id, vreme);
	}

	public List<Pregled> findByOtkazanAndZauzetAndKlinikaIdAndVremeAfterAndPotvrdjen(long id, boolean b, boolean c,
			long vreme, boolean potvrdjen) {
		return pregledRepository.findByOtkazanAndZauzetAndKlinikaIdAndVremeAfterAndSalaIdIsNotNullAndPotvrdjen(b, c, id,
				vreme, potvrdjen);
	}

	public List<Pregled> findByKlinikaIdAndSalaIdAndVremeAfterAndPotvrdjen(Long id, long time, boolean potvrdjen) {
		return pregledRepository.findByKlinikaIdAndSalaIdIsNullAndOtkazanFalseAndVremeAfterAndPotvrdjen(id, time,
				potvrdjen);
	}

	public List<RadniKalendarDTO> kreirajRadniKalendar(Long id, long time) {
		List<Pregled> pregledi = pregledRepository.findBySalaIdAndOtkazanFalseAndVremeAfter(id, time);
		List<RadniKalendarDTO> kalendar = new ArrayList<RadniKalendarDTO>();
		for (Pregled pregled : pregledi) {
			long pacid = 0;
			if (pregled.getPacijent() != null) {
				pacid = pregled.getPacijent().getId();
			}
			kalendar.add(new RadniKalendarDTO(pregled.getVreme(), pregled.getVreme() + pregled.getTrajanje(), "Pregled",
					pacid, "", "", "", ""));
		}
		return kalendar;
	}

	public List<RadniKalendarDTO> kreirajRadniKalendarRadnika(Long id, long time) {
		List<Pregled> pregledi = pregledRepository.findByLekarIdAndOtkazanFalse(id);
		List<RadniKalendarDTO> kalendar = new ArrayList<RadniKalendarDTO>();
		for (Pregled pregled : pregledi) {
			String sala = "";
			if (pregled.getSala() != null) {
				sala = pregled.getSala().getNaziv();
			}
			long pacijentId = 0;
			String pacijent = "";
			if (pregled.getPacijent() != null) {
				pacijent = pregled.getPacijent().getIme() + " " + pregled.getPacijent().getPrezime();
				pacijentId = pregled.getPacijent().getId();
			}
			kalendar.add(new RadniKalendarDTO(pregled.getVreme(), pregled.getVreme() + pregled.getTrajanje(), "Pregled",
					pacijentId, pacijent, pregled.getTipPregleda().getNaziv(), "", sala));
		}
		return kalendar;
	}

	public void dodijeliSalu(SlobodanTerminDTO slobodanTerminDTO) {
		Sala sala = salaService.findOne(slobodanTerminDTO.getSala().getId());
		Pregled pregled = pregledRepository.findById(slobodanTerminDTO.getPregledId()).orElseGet(null);
		pregled.setSala(sala);
		AdminKlinike ak = adminKlinikeService.findOne(slobodanTerminDTO.getIdAdmina());
		pregled.setKlinika(ak.getKlinika());
		pregled.setVreme(slobodanTerminDTO.getDatumiVreme());
		Lekar lekar = lekarService.findOne(slobodanTerminDTO.getLekar().getId());
		pregled.setLekar(lekar);
		emailService.posaljiLinkPotvrdePregleda(pregled, "aleksa.goljovic4@gmail.com");
		// emailService.obavijestiLekara(pregled, "aleksa.goljovic4@gmail.com");
		pregledRepository.save(pregled);

	}

	public ArrayList<PredefinisaniDTO> ucitajPredefinisane(PretragaKlinikeDTO pkdto) {
		TipPregleda tp = tipoviRepository.findByNazivAndAktivan(pkdto.getTipPregleda(), true);
		ArrayList<PredefinisaniDTO> predefinisaniPregledi = new ArrayList<PredefinisaniDTO>();
		ArrayList<Pregled> pregledi = new ArrayList<Pregled>();
		if (tp != null) {
			pregledi = (ArrayList<Pregled>) pregledRepository.findByKlinikaAndVremeAndTip(pkdto.getId(),
					pkdto.getDatum(), pkdto.getDatum() + 86400000, tp.getId());
			for (Pregled pr : pregledi) {
				PredefinisaniDTO p = new PredefinisaniDTO();
				p.setLekar(new LekarDTO(pr.getLekar()));
				p.setSala(pr.getSala().getBroj());
				p.setTip(new TipPregledaDTO(tp));
				p.setDatum(pr.getVreme());
				p.setCena(tp.getStavkaCenovnika().getCena());
				Popust popust = popustRepository.nadjiPopust(tp.getStavkaCenovnika().getId(), pkdto.getDatum(),
						pkdto.getDatum() + 86400000);
				if (popust == null) {
					p.setPopust(0);
				} else {
					p.setPopust(popust.getProcenat());
				}
				predefinisaniPregledi.add(p);
			}
		}
		return predefinisaniPregledi;
	}

	public Boolean zakaziPredefinisani(PredefinisaniDTO predef) throws MailException, InterruptedException {
		Pregled p = pregledRepository.findPregledByVremeAndLekarId(predef.getDatum(), predef.getLekar().getId());
		Optional<Pacijent> pa = pacijentRepository.findById(predef.getPacijent().getId());
		p.setPacijent(pa.get());
		p.setZauzet(true);
		p.setPotvrdjen(true);
		pregledRepository.save(p);
		String text = "Poštovani, \nUspešno ste zakazali pregled.\nPodaci o pregledu: \nKlinika: "
				+ predef.getLekar().getKlinika() + "\nLokacija: " + predef.getLokacija().getLokacija() + "\nVreme: "
				+ new Date(predef.getDatum()).toString() + "\nLekar: " + predef.getLekar().getIme() + " "
				+ predef.getLekar().getPrezime() + "\nTip pregleda: " + predef.getTip().getNaziv() + "\nBroj sale: "
				+ predef.getSala() + "\nCena: " + (predef.getCena() / 100.00) * (100 - predef.getPopust());
		emailService.posaljiEmail("aleksa.goljovic4@gmail.com", "Potvrda o zakazanom pregledu", text);
		return true;
	}

	public PredefinisaniDTO zakaziTermin(PretragaLekaraDTO pldto) {
		PredefinisaniDTO p = new PredefinisaniDTO();
		p.setDatum(pldto.getDatum());
		Optional<Lekar> le = lekarRepository.findById(pldto.getId());
		p.setLekar(new LekarDTO(le.get()));
		TipPregleda tp = tipoviRepository.findByNazivAndAktivan(pldto.getTipPregleda(), true);
		p.setTip(new TipPregledaDTO(tp));
		p.setCena(tp.getStavkaCenovnika().getCena());
		Popust popust = popustRepository.nadjiPopust(tp.getStavkaCenovnika().getId(), pldto.getDatum(),
				pldto.getDatum() + 86400000);
		if (popust == null) {
			p.setPopust(0);
		} else {
			p.setPopust(popust.getProcenat());
		}
		Optional<Klinika> k = klinikaRepository.findById(pldto.getKlinika());
		p.setLokacija(new KlinikaDTO(k.get()));
		Optional<Korisnik> pa = korisniciRepository.findById(pldto.getPacijent());
		p.setPacijent(new KorisnikDTO(pa.get()));
		return p;
	}

	public List<PregledDTO> vratiPregledePacijenta(long id) {
		List<Pregled> pregledi = pregledRepository.findByPacijentIdAndPotvrdjenAndOtkazan(id, true, false);
		List<PregledDTO> pregledidto = new ArrayList<PregledDTO>();
		if (pregledi != null) {
			for (Pregled p : pregledi) {
				pregledidto.add(new PregledDTO(p));
			}
		}
		return pregledidto;
	}

	public Boolean otkaziPregledPacijenta(PregledDTO pregled) throws MailException, InterruptedException {
		Optional<Pregled> p = pregledRepository.findById(pregled.getId());
		if (p != null) {
			Pregled pr = p.get();
			String to = pr.getLekar().getEmail();
			String text = "Poštovani,\nVaš pregled je otkazan.\nPodaci o pregledu: \nVreme: "
					+ new Date(pr.getVreme()).toString() + "\nTip pregleda: " + pr.getTipPregleda().getNaziv()
					+ "\nBroj sale: " + pr.getSala().getBroj() + "\nPacijent: " + pr.getPacijent().getIme() + " "
					+ pr.getPacijent().getPrezime();
			emailService.posaljiEmail("aleksa.goljovic4@gmail.com", "Otkazan pregled", text);
			pr.setPacijent(null);
			pr.setZauzet(false);
			pr.setPotvrdjen(false);
			pregledRepository.save(pr);
			return true;
		}
		return false;
	}

	public Boolean potvrdiZakazivanje(PredefinisaniDTO predef) throws MailException, InterruptedException {
		pregledRepository.insertZakazaniPregled(predef.getLekar().getId(), predef.getPacijent().getId(),
				predef.getTip().getId(), predef.getDatum(), predef.getLokacija().getId());
		ArrayList<AdminKlinike> admini = adminKlinikeRepository.findAdminByKlinikaId(predef.getLokacija().getId());
		if (admini != null) {
			for (AdminKlinike ak : admini) {
				String text = "Poštovani, \nPristigao je zahtev za zakazivanje pregleda.\nPodaci o pregledu:\nPacijent: "
						+ predef.getPacijent().getEmail() + "\nVreme: " + new Date(predef.getDatum()).toString()
						+ "\nLekar: " + predef.getLekar().getIme() + " " + predef.getLekar().getPrezime()
						+ "\nTip pregleda: " + predef.getTip().getNaziv();
				emailService.posaljiEmail("aleksa.goljovic4@gmail.com", "Zahtev za zakazivanje pregleda", text);
			}
		}
		return true;
	}

	public List<Pregled> findByLekarIdAndPacijentIdAndVreme(long idLekara, long idPacijenta, long time) {
		return pregledRepository.findByLekarIdAndPacijentIdAndVremeAndOtkazanFalse(idLekara, idPacijenta, time);
	}

	public Pregled mozeZapocetiPregled(long idLekara, long idPacijenta, long time) {
		return pregledRepository.mozeZapocetiPregled(idLekara, idPacijenta, time);
	}

	public Pregled dobaviPregled(long idLekara, long idPacijenta, long time) {
		return pregledRepository.mozeZapocetiPregled(idLekara, idPacijenta, time);
	}

	public Boolean otkaziPregledLekara(PretragaLekaraDTO pldto) throws MailException, InterruptedException {
		Pregled p = pregledRepository.findPregledByVremeAndLekarId(pldto.getDatum(), pldto.getId());
		if (p != null) {
			p.setOtkazan(true);
			pregledRepository.save(p);
			String slaBr = "";
			if (p.getSala() != null) {
				slaBr = p.getSala().getBroj() + "";
			}
			if (p.getPacijent() != null) {
				String to = p.getPacijent().getEmail();
				String text = "Poštovani,\nVaš pregled je otkazan.\nPodaci o pregledu: \nKlinika: "
						+ p.getKlinika().getNaziv() + "\nLokacija: " + p.getKlinika().getLokacija() + "\nVreme: "
						+ new Date(pldto.getDatum()).toString() + "\nLekar: " + p.getLekar().getIme() + " "
						+ p.getLekar().getPrezime() + "\nTip pregleda: " + p.getTipPregleda().getNaziv()
						+ "\nBroj sale: " + slaBr;
				emailService.posaljiEmail("aleksa.goljovic4@gmail.com", "Otkazan pregled", text);
			}
			return true;
		}
		return false;
	}

	public List<Pregled> dobaviSvePregledeBezSale() {
		return pregledRepository.dobaviSvePregledeBezSale();
	}

	public Boolean zakaziTerminLekar(ZakaziTerminLekarDTO ztlDTO) throws MailException, InterruptedException {
		Klinika klinika = lekarService.findOne(ztlDTO.getLekar()).getKlinika();
		Pacijent pacijent=pacijentRepository.getOne(ztlDTO.getPacijent());
		Lekar l=lekarRepository.getOne(ztlDTO.getLekar());
		ArrayList<AdminKlinike> admini = adminKlinikeRepository.findAdminByKlinikaId(klinika.getId());
		if (admini != null) {
			for (AdminKlinike ak : admini) {
				String text = "Poštovani, \nPristigao je zahtev za zakazivanje pregleda.\nPodaci o pregledu:\nPacijent: "
						+ pacijent.getIme()+" "+pacijent.getPrezime() + "\nVreme: " + new Date(ztlDTO.getDatumiVreme()).toString()
						+ "\nLekar: " + l.getIme() + " " + l.getPrezime()
						+ "\nTip pregleda: " + ztlDTO.getTipPregleda().getNaziv();
				emailService.posaljiEmail(ak.getEmail(), "Zahtev za zakazivanje pregleda", text);
			}
		}
		pregledRepository.insertZakazaniPregled(ztlDTO.getLekar(),ztlDTO.getPacijent(),ztlDTO.getTipPregleda().getId(),ztlDTO.getDatumiVreme(),klinika.getId());
		return true;
		
	}
	public List<Pregled> findByKlinikaIdAndPacijentIdAndVremeBeforeAndOtkazan(Long id, Long id2, long time, boolean b) {
		return pregledRepository.findByKlinikaIdAndPacijentIdAndVremeBeforeAndOtkazan(id, id2, time, b);
	}

	public List<Pregled> findByLekarIdAndPacijentIdAndVremeBeforeAndOtkazan(Long id, Long id2, long time, boolean b) {
		return pregledRepository.findByLekarIdAndPacijentIdAndVremeBeforeAndOtkazan(id, id2, time, b);
	}
}
