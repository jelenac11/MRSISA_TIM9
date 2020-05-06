package tim09.klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.dto.SlobodanTerminDTO;
import tim09.klinika.model.Klinika;
import tim09.klinika.model.Pregled;
import tim09.klinika.repository.PregledRepository;

@Service
public class PregledService {

	@Autowired
	private PregledRepository pregledRepository;
	
	@Autowired
	private AdminKlinikeService adminKlinikeService;

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
	public List<Pregled> findByTipPregledaIdAndOtkazanAndVremeGreaterThan(long id,boolean otkazano,long vreme){
		return pregledRepository.findByTipPregledaIdAndOtkazanAndVremeGreaterThan(id,otkazano,vreme);
	}
	
	public boolean insertPregled(SlobodanTerminDTO slobodanTerminDTO) {
		Klinika klinika= adminKlinikeService.findOne(slobodanTerminDTO.getIdAdmina()).getKlinika();
		int i=pregledRepository.insertPregled(slobodanTerminDTO.getLekar().getId(), slobodanTerminDTO.getTipPregleda().getId(), slobodanTerminDTO.getSala().getId(), slobodanTerminDTO.getDatumiVreme(), slobodanTerminDTO.getTrajanje(),klinika.getId());
		if(i==1) {
			return true;
		}
		return false;
	}
}
