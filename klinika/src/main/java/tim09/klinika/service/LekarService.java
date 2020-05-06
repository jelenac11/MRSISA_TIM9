package tim09.klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.dto.TipPregledaDTO;
import tim09.klinika.model.Klinika;
import tim09.klinika.model.Lekar;
import tim09.klinika.repository.LekarRepository;

@Service
public class LekarService {

	@Autowired
	private LekarRepository lekarRepository;
	
	@Autowired
	private FormatDatumaService datumService;

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

	public void remove(Long id) {
		lekarRepository.deleteById(id);
	}
	
	public List<Lekar> findByIdKlinikaAndVremeAndTipPregleda(Long klinikaId, long datumiVreme, TipPregledaDTO tipPregleda,int trajanje) {		
		return lekarRepository.findByIdKlinikaAndVremeAndTipPregleda(klinikaId,datumiVreme,datumService.getRadnoVrijemeLongIzLong(datumiVreme),tipPregleda.getId(),datumService.getMinuteULong(trajanje));
	}


}
