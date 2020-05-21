package tim09.klinika.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.dto.KlinikaDTO;
import tim09.klinika.dto.LekarDTO;
import tim09.klinika.dto.PretragaLekaraDTO;
import tim09.klinika.dto.TipPregledaDTO;
import tim09.klinika.model.Klinika;
import tim09.klinika.model.Lekar;
import tim09.klinika.model.OcenaKlinike;
import tim09.klinika.model.OcenaLekara;
import tim09.klinika.model.Odsustvo;
import tim09.klinika.model.Operacija;
import tim09.klinika.model.Pregled;
import tim09.klinika.model.TipPregleda;
import tim09.klinika.repository.LekarRepository;
import tim09.klinika.repository.OdsustvoRepository;
import tim09.klinika.repository.OperacijaRepository;
import tim09.klinika.repository.PregledRepository;

@Service
public class LekarService {

	@Autowired
	private LekarRepository lekarRepository;

	@Autowired
	private FormatDatumaService datumService;

	@Autowired
	private OperacijaRepository operacijaRepository;

	@Autowired
	private PregledRepository pregledRepository;
	
	@Autowired
	private OdsustvoRepository odsustvoRepository;

	public List<Lekar> findAllByKlinika(Klinika k) {
		return lekarRepository.findAllByKlinikaAndAktivan(k, true);
	}

	public Lekar findOne(Long id) {
		return lekarRepository.findById(id).orElseGet(null);
	}

	public List<Lekar> findAll() {
		return lekarRepository.findAll();
	}

	public Lekar save(Lekar lekar) {
		return lekarRepository.save(lekar);
	}

	public boolean remove(Long id) {
		Date sad = new Date();
		Optional<Lekar> l = lekarRepository.findById(id);
		if (l != null) {
			Lekar lekar = l.get();
			List<Pregled> pregledi = pregledRepository.findByLekarIdAndVremeAfterOrBetween(id, sad.getTime());
			List<Operacija> operacije = operacijaRepository.findByLekarIdAndVremeAfterOrBetween(id, sad.getTime());
			if (pregledi.size() == 0 && operacije.size() == 0) {
				lekar.setAktivan(false);
				lekarRepository.save(lekar);
				return true;
			}
			return false;
		} else {
			return false;
		}
	}

	public List<Lekar> findByIdKlinikaAndVremeAndTipPregleda(Long klinikaId, long datumiVreme,
			TipPregledaDTO tipPregleda, int trajanje) {
		return lekarRepository.findByIdKlinikaAndVremeAndTipPregleda(klinikaId, datumiVreme, datumService.getRadnoVrijemeLongIzLong(datumiVreme),
				tipPregleda.getId());
	}

	public List<Lekar> findByIdKlinikaAndVreme(Long klinikaId, long datumiVreme, int trajanje) {
		return lekarRepository.findByIdKlinikaAndVreme(klinikaId, datumiVreme, (datumiVreme + 7200000) % 86400000);
	}

	public List<Long> vratiSlobodneTermine(PretragaLekaraDTO pldto) {
		List<Operacija> operacije = operacijaRepository.findByLekarIdAndVreme(pldto.getId(), pldto.getDatum(),
				pldto.getDatum() + 86400000);
		List<Pregled> pregledi = pregledRepository.findByLekarAndVreme(pldto.getId(), pldto.getDatum(),
				pldto.getDatum() + 86400000);
		Optional<Lekar> l = lekarRepository.findById(pldto.getId());
		ArrayList<Long> vremena = new ArrayList<Long>();
		if (l != null) {
			Lekar le = l.get();
			long pocetak = le.getPocetakRadnogVremena();
			long kraj = le.getKrajRadnogVremena();
			if (kraj < pocetak) {
				int pre = (int) (kraj / 3600000);
				int ostatak = (int) (kraj % 3600000);
				for (int i = 0; i < pre; i++) {
					vremena.add(pldto.getDatum() + i * 3600000 + ostatak);
				}
				int posle = (int) ((86400000 - pocetak) / 3600000);
				for (int i = 0; i < posle; i++) {
					vremena.add(pldto.getDatum() + pocetak + i * 3600000);
				}
			} else {
				int sati = (int) ((kraj - pocetak) / 3600000);
				for (int i = 0; i < sati; i++) {
					vremena.add(pldto.getDatum() + pocetak + i * 3600000);
				}
			}
			for (Pregled p : pregledi) {
				if (vremena.contains(p.getVreme())) {
					vremena.remove(p.getVreme());
				}
			}
			for (Operacija o : operacije) {
				if (vremena.contains(o.getVreme())) {
					vremena.remove(o.getVreme());
				}
			}
		}
		return vremena;
	}

	public List<LekarDTO> vratiLekareKlinike(Long id) {
		List<Lekar> lekari = lekarRepository.findByKlinikaId(id);
		List<LekarDTO> lekaridto = new ArrayList<LekarDTO>();
		if (lekari != null) {
			for (Lekar l : lekari) {
				lekaridto.add(new LekarDTO(l));
			}
		}
		return lekaridto;
	}

	public Boolean proveriTipIVreme(PretragaLekaraDTO pldto) {
		Optional<Lekar> le = lekarRepository.findById(pldto.getId());
		boolean zadovoljava = false;
		if (le != null) {
			if (le.get().getSpecijalnosti() != null) {
				for (TipPregleda tp : le.get().getSpecijalnosti()) {
					if (tp.getNaziv().equals(pldto.getTipPregleda())) {
						List<Long> termini = vratiSlobodneTermine(pldto);
						if (termini.size() > 0) {
							zadovoljava = true;
						}
					}
				}
			}
		}
		return zadovoljava;
	}
	
	public void izracunajProsek(Long id) {
		Lekar l = findOne(id);
		long suma = 0;
		for (OcenaLekara ocena : l.getOcene()) {
			suma += ocena.getVrednost();
		}
		l.setProsecnaOcena(round(suma * 1.0 / l.getOcene().size(), 1));
		save(l);
	}

	public void dodajOcenu(OcenaLekara ocena) {
		Lekar l = findOne(ocena.getLekar().getId());
		l.getOcene().add(ocena);
	}

	public static double round(double value, int places) {
		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public Boolean proveriGodisnji(PretragaLekaraDTO pldto) {
		List<Odsustvo> odsustva=odsustvoRepository.findByPodnosilacAndVreme(pldto.getId(), pldto.getDatum());
		if(odsustva.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}

}
