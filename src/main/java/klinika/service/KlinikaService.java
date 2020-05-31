package klinika.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import klinika.dto.DnevniIzvjestajDTO;
import klinika.dto.IzvjestajPoslovanjaDTO;
import klinika.dto.KlinikaDTO;
import klinika.dto.LekarDTO;
import klinika.dto.PretragaKlinikeDTO;
import klinika.model.Klinika;
import klinika.model.Lekar;
import klinika.model.OcenaKlinike;
import klinika.model.Operacija;
import klinika.model.Popust;
import klinika.model.Pregled;
import klinika.model.TipPregleda;
import klinika.repository.KlinikaRepository;
import klinika.repository.LekarRepository;
import klinika.repository.OperacijaRepository;
import klinika.repository.PopustRepository;
import klinika.repository.PregledRepository;
import klinika.repository.TipPregledaRepository;

@Service
public class KlinikaService {

	@Autowired
	private KlinikaRepository klinikaRepository;

	@Autowired
	private LekarRepository lekarRepository;

	@Autowired
	private TipPregledaRepository tipPregledaRepository;

	@Autowired
	private OperacijaRepository operacijaRepository;

	@Autowired
	private PregledRepository pregledRepository;

	@Autowired
	private PopustRepository popustRepository;

	public Klinika findByNaziv(String naziv) {
		return klinikaRepository.findByNaziv(naziv);
	}

	public Klinika findOne(Long id) {
		return klinikaRepository.findById(id).orElseGet(null);
	}

	public List<Klinika> findAll() {
		return klinikaRepository.findAll();
	}

	public Klinika save(Klinika klinika) {
		return klinikaRepository.save(klinika);
	}

	public void remove(Long id) {
		klinikaRepository.deleteById(id);
	}

	public Boolean proveriTip(PretragaKlinikeDTO pretragaKlinikeDTO) {
		TipPregleda tp = tipPregledaRepository.findByIdAndNazivAndAktivan(pretragaKlinikeDTO.getId(),
				pretragaKlinikeDTO.getTipPregleda());
		if (tp != null) {
			ArrayList<Lekar> lekari = (ArrayList<Lekar>) lekarRepository.findBySearchParams(pretragaKlinikeDTO.getId(),
					tp.getId(), pretragaKlinikeDTO.getDatum());
			if (lekari == null) {
				return false;
			}
			for (Lekar l : lekari) {
				long pocetakRadnog = l.getPocetakRadnogVremena();
				long krajRadnog = l.getKrajRadnogVremena();
				List<Operacija> operacije = operacijaRepository.findByLekarIdAndVreme(l.getId(),
						pretragaKlinikeDTO.getDatum(), pretragaKlinikeDTO.getDatum() + 86400000);
				List<Pregled> pregledi = pregledRepository.findByLekarAndVreme(l.getId(), pretragaKlinikeDTO.getDatum(),
						pretragaKlinikeDTO.getDatum() + 86400000);
				int sati = (int) (krajRadnog - pocetakRadnog) / 3600000;
				for (int i = 0; i <= pregledi.size() - 1; i++) {
					if (pregledi.get(i).getPacijent() == null) {
						pregledi.remove(i);
						i--;
					}
				}
				if ((operacije.size() + pregledi.size()) < sati) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean update(KlinikaDTO klinikaDTO) {
		Klinika klinika = findOne(klinikaDTO.getId());
		Klinika postojiKlinika = null;

		if (!klinikaDTO.getNaziv().equals(klinika.getNaziv())) {
			postojiKlinika = klinikaRepository.findByNaziv(klinikaDTO.getNaziv());
		}

		if (postojiKlinika == null) {
			klinika.setNaziv(klinikaDTO.getNaziv());
			klinika.setLokacija(klinikaDTO.getLokacija());
			klinika.setOpis(klinikaDTO.getOpis());
			klinika.setLat(klinikaDTO.getLat());
			klinika.setLng(klinikaDTO.getLng());
			klinikaRepository.save(klinika);
		} else {
			return false;
		}
		return true;
	}

	public List<LekarDTO> vratiSlobodneLekare(PretragaKlinikeDTO pkdto) {
		ArrayList<LekarDTO> lekariDTO = new ArrayList<>();
		TipPregleda tp = tipPregledaRepository.findByIdAndNazivAndAktivan(pkdto.getId(), pkdto.getTipPregleda());
		if (tp != null) {
			ArrayList<Lekar> lekari = (ArrayList<Lekar>) lekarRepository.findBySearchParams(pkdto.getId(), tp.getId(),
					pkdto.getDatum());
			for (Lekar l : lekari) {
				if (l.isAktivan()) {
					long pocetakRadnog = l.getPocetakRadnogVremena();
					long krajRadnog = l.getKrajRadnogVremena();
					List<Operacija> operacije = operacijaRepository.findByLekarIdAndVreme(l.getId(), pkdto.getDatum(),
							pkdto.getDatum() + 86400000);
					List<Pregled> pregledi = pregledRepository.findByLekarAndVremeAndPacijentIdIsNotNull(l.getId(), pkdto.getDatum(),
							pkdto.getDatum() + 86400000);
					int sati = (int) (krajRadnog - pocetakRadnog) / 3600000;
					for (int i = 0; i <= pregledi.size() - 1; i++) {
						pregledi.remove(i);
						i--;
					}
					if ((operacije.size() + pregledi.size()) < sati) {
						lekariDTO.add(new LekarDTO(l));
					}
				}
			}
		}
		return lekariDTO;
	}

	public void izracunajProsek(Long id) {
		Klinika k = findOne(id);
		long suma = 0;
		for (OcenaKlinike ocena : k.getOcene()) {
			suma += ocena.getVrednost();
		}
		k.setProsecnaOcena(round(suma * 1.0 / k.getOcene().size(), 1));
		save(k);
	}

	public void dodajOcenu(OcenaKlinike ocena) {
		Klinika k = findOne(ocena.getKlinika().getId());
		k.getOcene().add(ocena);
	}

	public static double round(double value, int places) {
		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public List<ArrayList<Long>> sedmicniIzvjestaj(long id, long start, long end) {
		List<Pregled> pregledi = pregledRepository.sedmicniIzvjestaj(id, start, end, new Date().getTime());
		long dana = (end - start) / 86400000;
		List<ArrayList<Long>> izvjestaj = new ArrayList<>();
		for (int i = 0; i < dana; i++) {
			ArrayList<Long> dan = new ArrayList<>();
			dan.add(start + i * 86400000);
			long broj = 0;
			for (Pregled pregled : pregledi) {
				if (pregled.getVreme() > (start + i * 86400000) && pregled.getVreme() < (start + (i + 1) * 86400000)) {
					broj++;
				}
			}
			dan.add(broj);
			izvjestaj.add(dan);
		}
		return izvjestaj;
	}

	public DnevniIzvjestajDTO dnevniIzvjestaj(long id, long start) {
		List<Pregled> pregledi = pregledRepository.sedmicniIzvjestaj(id, start, start + 86400000, new Date().getTime());
		List<TipPregleda> tipovi = tipPregledaRepository.findByKlinikaIdAndAktivan(id, true);
		HashMap<String, Integer> mapa = new HashMap<>();
		for (TipPregleda tip : tipovi) {
			mapa.put(tip.getNaziv(), 0);
		}
		for (Pregled pregled : pregledi) {
			mapa.put(pregled.getTipPregleda().getNaziv(), mapa.get(pregled.getTipPregleda().getNaziv()) + 1);
		}
		ArrayList<Long> izvjestaj = new ArrayList<>();
		izvjestaj.add(start);
		izvjestaj.add((long) pregledi.size());
		DnevniIzvjestajDTO dnevniIzvjestaj = new DnevniIzvjestajDTO();
		dnevniIzvjestaj.setKrofna(mapa);
		dnevniIzvjestaj.setUkupno(izvjestaj);
		return dnevniIzvjestaj;
	}

	public List<ArrayList<Long>> mjesecniIzvjestaj(long id, String mjesec) {
		Date time = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR, 2);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.AM_PM, 0);
		long start;
		long end;
		switch (mjesec) {
		case "Januar":
			cal.set(Calendar.MONTH, Calendar.JANUARY);
			break;
		case "Februar":
			cal.set(Calendar.MONTH, Calendar.FEBRUARY);
			break;
		case "Mart":
			cal.set(Calendar.MONTH, Calendar.MARCH);
			break;
		case "April":
			cal.set(Calendar.MONTH, Calendar.APRIL);
			break;
		case "Maj":
			cal.set(Calendar.MONTH, Calendar.MAY);
			break;
		case "Jun":
			cal.set(Calendar.MONTH, Calendar.JUNE);
			break;
		case "Jul":
			cal.set(Calendar.MONTH, Calendar.JULY);
			break;
		case "Avgust":
			cal.set(Calendar.MONTH, Calendar.AUGUST);
			break;
		case "Septembar":
			cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
			break;
		case "Oktobar":
			cal.set(Calendar.MONTH, Calendar.OCTOBER);
			break;
		case "Novembar":
			cal.set(Calendar.MONTH, Calendar.NOVEMBER);
			break;
		case "Decembar":
			cal.set(Calendar.MONTH, Calendar.DECEMBER);
			break;
		default:
			break;
		}
		start = cal.getTimeInMillis();
		cal.add(Calendar.MONTH, 1);
		end = cal.getTimeInMillis();
		List<Pregled> pregledi = pregledRepository.sedmicniIzvjestaj(id, start, end, new Date().getTime());
		long dana = (end - start) / 86400000;
		List<ArrayList<Long>> izvjestaj = new ArrayList<>();
		for (long i = 0; i < dana; i++) {
			long broj = 0;
			long privremena = i * 86400000;
			long pocetak = (start + privremena);
			privremena = (i + 1) * 86400000;
			long kraj = (start + privremena);
			ArrayList<Long> dan = new ArrayList<>();
			dan.add(pocetak);
			for (Pregled pregled : pregledi) {
				if (pregled.getVreme() > pocetak && pregled.getVreme() < kraj) {
					broj++;
				}
			}
			dan.add(broj);
			izvjestaj.add(dan);
		}
		return izvjestaj;
	}

	public IzvjestajPoslovanjaDTO izvjestajPoslovanja(long id, long start, long end) {
		IzvjestajPoslovanjaDTO izvjestaj = new IzvjestajPoslovanjaDTO();
		izvjestaj.setStart(start);
		izvjestaj.setEnd(end);
		double prihodi = 0;

		List<Pregled> pregledi = pregledRepository.sedmicniIzvjestaj(id, start, end, new Date().getTime());
		List<TipPregleda> tipovi = tipPregledaRepository.findByKlinikaIdAndAktivan(id, true);

		HashMap<String, Double> mapa = new HashMap<>();
		for (TipPregleda tip : tipovi) {
			mapa.put(tip.getNaziv(), 0.0);
		}

		for (Pregled pregled : pregledi) {
			Popust popust = popustRepository.nadjiPopust(pregled.getTipPregleda().getStavkaCenovnika().getId(),
					pregled.getVreme(), pregled.getVreme() + 86400000);
			if (popust == null) {
				mapa.put(pregled.getTipPregleda().getNaziv(), mapa.get(pregled.getTipPregleda().getNaziv())
						+ pregled.getTipPregleda().getStavkaCenovnika().getCena());
				prihodi += pregled.getTipPregleda().getStavkaCenovnika().getCena();
			} else {
				mapa.put(pregled.getTipPregleda().getNaziv(),
						mapa.get(pregled.getTipPregleda().getNaziv())
								+ (pregled.getTipPregleda().getStavkaCenovnika().getCena() / 100.00)
										* (100 - popust.getProcenat()));
				prihodi += (pregled.getTipPregleda().getStavkaCenovnika().getCena() / 100.00)
						* (100 - popust.getProcenat());
			}
		}
		izvjestaj.setPrihod(prihodi);
		izvjestaj.setKrofna(mapa);
		return izvjestaj;
	}

}
