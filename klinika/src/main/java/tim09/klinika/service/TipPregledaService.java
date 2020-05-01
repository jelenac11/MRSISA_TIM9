package tim09.klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.model.TipPregleda;
import tim09.klinika.repository.TipPregledaRepository;

@Service
public class TipPregledaService {

	@Autowired
	private TipPregledaRepository tipPregledaRepository;

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

	public void remove(Long id) {
		tipPregledaRepository.deleteById(id);
	}
}
