package tim09.klinika.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.dto.KlinikaDTO;
import tim09.klinika.dto.LekarDTO;
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
				List<Operacija> operacije = operacijaRepository.findByLekarIdAndVreme(l.getId(), pretragaKlinikeDTO.getDatum(), pretragaKlinikeDTO.getDatum() + 86400000);
				List<Pregled> pregledi = pregledRepository.findByLekarAndVreme(l.getId(), pretragaKlinikeDTO.getDatum(), pretragaKlinikeDTO.getDatum() + 86400000);
				int sati = (int) (krajRadnog - pocetakRadnog) / 3600000;
				for (int i = 0; i <= pregledi.size() - 1; i++) {
					if (pregledi.get(i).getPacijent() == null) {
						pregledi.remove(i);
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
		Klinika klinika=findOne(klinikaDTO.getId());
		Klinika postojiKlinika=null;
		
		if(!klinikaDTO.getNaziv().equals(klinika.getNaziv())) {
			postojiKlinika=klinikaRepository.findByNaziv(klinikaDTO.getNaziv());
		}
		
		if(postojiKlinika==null) {
			klinika.setNaziv(klinikaDTO.getNaziv());
			klinika.setLokacija(klinikaDTO.getLokacija());
			klinika.setOpis(klinikaDTO.getOpis());
			klinikaRepository.save(klinika);
		}
		else {
			return false;
		}
		return true;
	}

	public List<LekarDTO> vratiSlobodneLekare(PretragaKlinikeDTO pkdto) {
		ArrayList<LekarDTO> lekariDTO = new ArrayList<LekarDTO>();
		TipPregleda tp = tipPregledaRepository.findByIdAndNaziv(pkdto.getId(), pkdto.getTipPregleda());
		if ( tp != null) {
			ArrayList<Lekar> lekari = (ArrayList<Lekar>) lekarRepository.findBySearchParams(pkdto.getId(), tp.getId(), pkdto.getDatum()); 
			for (Lekar l : lekari) {
				long pocetakRadnog = l.getPocetakRadnogVremena();
				long krajRadnog = l.getKrajRadnogVremena();
				List<Operacija> operacije = operacijaRepository.findByLekarIdAndVreme(l.getId(), pkdto.getDatum(), pkdto.getDatum() + 86400000);
				List<Pregled> pregledi = pregledRepository.findByLekarAndVreme(l.getId(), pkdto.getDatum(), pkdto.getDatum() + 86400000);
				int sati = (int) (krajRadnog - pocetakRadnog) / 3600000;
				for (int i = 0; i <= pregledi.size() - 1; i++) {
					if (pregledi.get(i).getPacijent() == null) {
						pregledi.remove(i);
					}
				}
				if ((operacije.size() + pregledi.size()) < sati) {
					lekariDTO.add(new LekarDTO(l));
				}
			}
		}
		return lekariDTO;
	}
}

