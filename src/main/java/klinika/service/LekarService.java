package klinika.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import klinika.dto.LekarDTO;
import klinika.dto.PretragaLekaraDTO;
import klinika.dto.TipPregledaDTO;
import klinika.model.Klinika;
import klinika.model.Lekar;
import klinika.model.OcenaLekara;
import klinika.model.Odsustvo;
import klinika.model.Operacija;
import klinika.model.Pregled;
import klinika.model.TipPregleda;
import klinika.repository.LekarRepository;
import klinika.repository.OdsustvoRepository;
import klinika.repository.OperacijaRepository;
import klinika.repository.PregledRepository;

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

	@Transactional(readOnly = false)
	public Lekar save(Lekar lekar) {
		return lekarRepository.save(lekar);
	}

	public boolean remove(Long id) {
		Date sad = new Date();
		Optional<Lekar> l = lekarRepository.findById(id);
		if (l.isPresent()) {
			Lekar lekar = l.get();
			List<Pregled> pregledi = pregledRepository.findByLekarIdAndVremeAfterOrBetween(id, sad.getTime());
			List<Operacija> operacije = operacijaRepository.findByLekarIdAndVremeAfterOrBetween(id, sad.getTime());
			if (pregledi.isEmpty() && operacije.isEmpty()) {
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
			TipPregledaDTO tipPregleda) {
		return lekarRepository.findByIdKlinikaAndVremeAndTipPregleda(klinikaId, datumiVreme,
				datumService.getRadnoVrijemeLongIzLong(datumiVreme), tipPregleda.getId());
	}

	public List<Lekar> findByIdKlinikaAndVreme(Long klinikaId, long datumiVreme) {
		return lekarRepository.findByIdKlinikaAndVreme(klinikaId, datumiVreme, (datumiVreme + 7200000) % 86400000);
	}

	// Metoda koja vraća slobodne termine izabranog lekara za traženi datum
	public List<Long> vratiSlobodneTermine(PretragaLekaraDTO pldto) {
		List<Operacija> operacije = operacijaRepository.findByLekarIdAndVreme(pldto.getId(), pldto.getDatum(),
				pldto.getDatum() + 86400000);
		List<Pregled> pregledi = pregledRepository.findByLekarAndVreme(pldto.getId(), pldto.getDatum(),
				pldto.getDatum() + 86400000);
		Optional<Lekar> l = lekarRepository.findById(pldto.getId());
		ArrayList<Long> vremena = new ArrayList<>();
		if (l.isPresent()) {
			Lekar le = l.get();
			long pocetak = le.getPocetakRadnogVremena();
			long kraj = le.getKrajRadnogVremena();
			vremena = vratiRadneSate(pocetak, kraj, pldto.getDatum());
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

	// Metoda koja vraća radne sate lekara
	private ArrayList<Long> vratiRadneSate(long pocetak, long kraj, long datum) {
		ArrayList<Long> vremena = new ArrayList<>();
		if (kraj < pocetak) {
			int pre = (int) (kraj / 3600000);
			int ostatak = (int) (kraj % 3600000);
			for (int i = 0; i < pre; i++) {
				vremena.add(datum + i * 3600000 + ostatak);
			}
			int posle = (int) ((86400000 - pocetak) / 3600000);
			for (int i = 0; i < posle; i++) {
				vremena.add(datum + pocetak + i * 3600000);
			}
		} else {
			int sati = (int) ((kraj - pocetak) / 3600000);
			for (int i = 0; i < sati; i++) {
				vremena.add(datum + pocetak + i * 3600000);
			}
		}
		return vremena;
	}

	public List<LekarDTO> vratiLekareKlinike(Long id) {
		List<Lekar> lekari = lekarRepository.findByKlinikaId(id);
		List<LekarDTO> lekaridto = new ArrayList<>();
		if (lekari != null) {
			for (Lekar l : lekari) {
				lekaridto.add(new LekarDTO(l));
			}
		}
		return lekaridto;
	}

	// Pomoćna metoda koja proverava da li je lekar slobodan za traženi datum i specijalozovan za traženi pregled
	public Boolean proveriTipIVreme(PretragaLekaraDTO pldto) {
		Optional<Lekar> le = lekarRepository.findById(pldto.getId());
		boolean zadovoljava = true;
		boolean postoji = false;
		if (le.isPresent()) {
			if (le.get().getSpecijalnosti() != null) {
				for (TipPregleda tp : le.get().getSpecijalnosti()) {
					if (tp.getNaziv().equals(pldto.getTipPregleda())) {
						postoji = true;
						List<Odsustvo> odsustva = odsustvoRepository.findByPodnosilacAndVreme(le.get().getId(), pldto.getDatum());
						if (!odsustva.isEmpty()) {
							zadovoljava = false;
						}
						List<Long> termini = vratiSlobodneTermine(pldto);
						if (termini.isEmpty()) {
							zadovoljava = false;
						}
					}
				}
			}
		}
		if (!postoji) {
			zadovoljava = false;
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
		List<Odsustvo> odsustva = odsustvoRepository.findByPodnosilacAndVreme(pldto.getId(), pldto.getDatum());
		return odsustva.isEmpty();
	}

}
