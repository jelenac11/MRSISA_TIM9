package tim09.klinika.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.dto.TipPregledaDTO;
import tim09.klinika.model.Pregled;
import tim09.klinika.model.TipPregleda;
import tim09.klinika.repository.TipPregledaRepository;

@Service
public class TipPregledaService {

	@Autowired
	private TipPregledaRepository tipPregledaRepository;

	@Autowired
	private PregledService pregledService;
	
	public TipPregleda findOne(Long id) {
		return tipPregledaRepository.findById(id).orElseGet(null);
	}

	public TipPregleda findOneByNaziv(String naziv) {
		return tipPregledaRepository.findByNaziv(naziv);
	}
	
	public List<TipPregleda> findAll() {
		return tipPregledaRepository.findAll();
	}

	public List<TipPregleda> findAllByKlinikaId(long id){
		return tipPregledaRepository.findByKlinikaId(id);
	}
	public TipPregleda save(TipPregleda tipPregleda) {
		return tipPregledaRepository.save(tipPregleda);
	}

	public boolean remove(Long id) {
		List<Pregled> pregledi=pregledService.findByTipPregledaIdAndOtkazanAndVremeGreaterThan(id,false,new Date().getTime());
		if(pregledi.isEmpty()) {
			tipPregledaRepository.deleteById(id);
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean update(TipPregledaDTO tipPregledaDTO,TipPregleda tipPregleda) {
		List<Pregled> pregledi=pregledService.findByTipPregledaIdAndOtkazanAndVremeGreaterThan(tipPregledaDTO.getId(),false,new Date().getTime());
		TipPregleda postojiNaziv=null;
		if(!tipPregledaDTO.getNaziv().equals(tipPregleda.getNaziv())) {
			postojiNaziv=tipPregledaRepository.findByNaziv(tipPregledaDTO.getNaziv());
		}
		if(pregledi.isEmpty() && postojiNaziv==null) {
			TipPregleda pregled=tipPregledaRepository.findById(tipPregledaDTO.getId()).orElseGet(null);
			pregled.setNaziv(tipPregledaDTO.getNaziv());
			pregled.setOpis(tipPregledaDTO.getOpis());
			tipPregledaRepository.save(pregled);
			return true;
		}
		else {
			return false;
		}
	}
}
