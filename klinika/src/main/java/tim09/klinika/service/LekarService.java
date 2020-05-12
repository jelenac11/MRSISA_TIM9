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
import tim09.klinika.model.Operacija;
import tim09.klinika.model.Pregled;
import tim09.klinika.model.TipPregleda;
import tim09.klinika.repository.LekarRepository;
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

	public List<Lekar> findAllByKlinika(Klinika k) {
		return lekarRepository.findAllByKlinika(k);
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
		Optional<Lekar> l = lekarRepository.findById(id);
		
		if (l != null) {
			Lekar lekar = l.get();
			lekar.setAktivan(false);
			return true;
		} else {
			return false;
		}
	}
	
	public List<Lekar> findByIdKlinikaAndVremeAndTipPregleda(Long klinikaId, long datumiVreme, TipPregledaDTO tipPregleda,int trajanje) {		
		return lekarRepository.findByIdKlinikaAndVremeAndTipPregleda(klinikaId,datumiVreme,datumService.getRadnoVrijemeLongIzLong(datumiVreme),tipPregleda.getId(),datumService.getMinuteULong(trajanje));
	}

	public List<Long> vratiSlobodneTermine(PretragaLekaraDTO pldto) {
		System.out.println("*************************************" + pldto.getDatum() + pldto.getId());
		List<Operacija> operacije = operacijaRepository.findByLekarIdAndVreme(pldto.getId(), pldto.getDatum(), pldto.getDatum() + 86400000);
		List<Pregled> pregledi = pregledRepository.findByLekarAndVreme(pldto.getId(), pldto.getDatum(), pldto.getDatum() + 86400000);
		Optional<Lekar> l = lekarRepository.findById(pldto.getId());
		ArrayList<Long> vremena = new ArrayList<Long>();
		if (l != null) {
			Lekar le =  l.get();
			long pocetak = le.getPocetakRadnogVremena();
			long kraj = le.getKrajRadnogVremena();
			int sati = (int) ((kraj - pocetak) / 3600000);
			for (int i = 0; i < sati; i++) {
				vremena.add(pldto.getDatum() + pocetak + i*3600000);
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


}
