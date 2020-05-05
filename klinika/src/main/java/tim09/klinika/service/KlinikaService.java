package tim09.klinika.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.dto.OperacijaDTO;
import tim09.klinika.dto.PretragaKlinikeDTO;
import tim09.klinika.model.Klinika;
import tim09.klinika.model.Lekar;
import tim09.klinika.model.Operacija;
import tim09.klinika.model.Pregled;
import tim09.klinika.model.TipPregleda;
import tim09.klinika.repository.KlinikaRepository;
import tim09.klinika.repository.LekarRepository;
import tim09.klinika.repository.OperacijaRepository;
import tim09.klinika.repository.PregledRepository;
import tim09.klinika.repository.TipPregledaRepository;

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
		TipPregleda tp = tipPregledaRepository.findByIdAndNaziv(pretragaKlinikeDTO.getId(), pretragaKlinikeDTO.getTipPregleda());
		if ( tp != null) {
			//lekari koji zadovoljavaju tip pregleda, kliniku i nisu tada odsutni
			ArrayList<Lekar> lekari = (ArrayList<Lekar>) lekarRepository.findBySearchParams(pretragaKlinikeDTO.getId(), tp.getId(), pretragaKlinikeDTO.getDatum());
			if (lekari == null) {
				return false;
			} 
			for (Lekar l : lekari) {
				long pocetakRadnog = l.getPocetakRadnogVremena();
				long krajRadnog = l.getKrajRadnogVremena();
				//operacije dana kad se zeli pregled poredjane u rastucem redosledu, isto vazi i za preglede
				List<Operacija> operacije = operacijaRepository.findByLekarIdAndVreme(l.getId(), pretragaKlinikeDTO.getDatum(), pretragaKlinikeDTO.getDatum() + 86400000);
				List<Pregled> pregledi = pregledRepository.findByLekarAndVreme(l.getId(), pretragaKlinikeDTO.getDatum(), pretragaKlinikeDTO.getDatum() + 86400000);
				//sve je prazno
				if (operacije.isEmpty() && pregledi.isEmpty()) {
					return true;
				}
				//prazne samo operacije
				if (operacije.isEmpty()) {
					if (pregledi.get(0).getVreme() >= (pretragaKlinikeDTO.getDatum() + pocetakRadnog + 1800000)) {
						return true;
					}
					if (pregledi.get(pregledi.size()-1).getVreme() <= (pretragaKlinikeDTO.getDatum() + krajRadnog - 1800000)) {
						return true;
					}
					for (int i = 0; i <= pregledi.size()-2; i++) {
						if (pregledi.get(i).getVreme()<=(pregledi.get(i+1).getVreme()-1800000)) {
							return true;
						}
					}
					return false;
				}
				//prazni samo pregledi
				if (pregledi.isEmpty()) {
					if (operacije.get(0).getVreme() >= (pretragaKlinikeDTO.getDatum() + pocetakRadnog + 1800000)) {
						return true;
					}
					if (operacije.get(operacije.size()-1).getVreme() <= (pretragaKlinikeDTO.getDatum() + krajRadnog - 4500000)) {
						return true;
					}
					for (int i = 0; i <= operacije.size()-2; i++) {
						if (operacije.get(i).getVreme()<=(operacije.get(i+1).getVreme()-4500000)) {
							return true;
						}
					}
					return false;
				}
				//nista nije prazno
				if ((pregledi.get(0).getVreme() <= operacije.get(0).getVreme()) && (pregledi.get(0).getVreme() >= (pretragaKlinikeDTO.getDatum() + pocetakRadnog + 1800000))) {
					return true;
				}
				if ((pregledi.get(0).getVreme() >= operacije.get(0).getVreme()) && (operacije.get(0).getVreme() >= (pretragaKlinikeDTO.getDatum() + pocetakRadnog + 1800000))) {
					return true;
				}	
				if ((pregledi.get(0).getVreme() >= operacije.get(0).getVreme()) && (pregledi.get(0).getVreme() <= (pretragaKlinikeDTO.getDatum() + krajRadnog - 1800000))) {
					return true;
				}
				if ((pregledi.get(0).getVreme() <= operacije.get(0).getVreme()) && (operacije.get(0).getVreme() <= (pretragaKlinikeDTO.getDatum() + krajRadnog + 4500000))) {
					return true;
				}
				for (int i = 0; i <= pregledi.size()-2; i++) {
					//pregled pocinje minimum 30 minuta pre prve operacije i minimum 30 min pre prvog sledeceg pregleda
					if (pregledi.get(i).getVreme() <= (operacije.get(0).getVreme() - 1800000)){
						if (pregledi.get(i).getVreme() <= (pregledi.get(i+1).getVreme() - 1800000)) {
							return true;
						}
					}
					//pregled pocinje nakon poslednje operacije i barem 30 minuta pre sledeceg pregleda
					if (pregledi.get(i).getVreme() >= operacije.get(operacije.size()-1).getVreme()) {
						if ((pregledi.get(i).getVreme() + 1800000) <= pregledi.get(i+1).getVreme()) {
							return true;
						}
					}
					//pregled je izmedju dve operacije
					OperacijaDTO oMIN = new OperacijaDTO(operacije.get(0));
					OperacijaDTO oMAX = new OperacijaDTO(operacije.get(0));
					for (Operacija operac : operacije) {
						if ((operac.getVreme() >= pregledi.get(i).getVreme()) && (operac.getVreme()<= oMAX.getVreme().getTime())){
							oMAX = new OperacijaDTO(operac);
						}
						if ((operac.getVreme() <= pregledi.get(i).getVreme()) && (operac.getVreme()<= oMIN.getVreme().getTime())) {
							oMIN = new OperacijaDTO(operac);
						}
					}
					if ((oMIN.getVreme().getTime() <= pregledi.get(i-1).getVreme()) && ((pregledi.get(i-1).getVreme()+1800000) <= pregledi.get(i).getVreme())){
						return true;
					}
					if ((oMIN.getVreme().getTime() >= pregledi.get(i-1).getVreme()) && ((oMIN.getVreme().getTime()+4500000) <= pregledi.get(i).getVreme())) {
						return true;
					}
					if ((oMAX.getVreme().getTime() <= pregledi.get(i+1).getVreme()) && (oMAX.getVreme().getTime() >= (pregledi.get(i).getVreme()+1800000))){
						return true;
					}
					if ((oMAX.getVreme().getTime() >= pregledi.get(i+1).getVreme()) && ((pregledi.get(i).getVreme()+1800000) <= pregledi.get(i+1).getVreme())) {
						return true;
					}
				}
			}
		}
		return false;
	}
}

