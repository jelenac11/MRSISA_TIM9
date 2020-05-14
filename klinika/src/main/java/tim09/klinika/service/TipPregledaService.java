package tim09.klinika.service;

import java.util.ArrayList;
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
		return tipPregledaRepository.findByNazivAndAktivan(naziv, true);
	}

	public List<TipPregleda> findAll() {
		return tipPregledaRepository.findAllByAktivan(true);
	}

	public List<TipPregleda> findAllByKlinikaId(long id) {
		return tipPregledaRepository.findByKlinikaIdAndAktivan(id, true);
	}

	public TipPregleda save(TipPregleda tipPregleda) {
		return tipPregledaRepository.save(tipPregleda);
	}

	public boolean remove(Long id) {
		List<Pregled> pregledi = pregledService.findByTipPregledaIdAndOtkazanAndVremeGreaterThan(id, false,
				new Date().getTime());
		
		if (pregledi.isEmpty()) {
			TipPregleda pregled=findOne(id);
			pregled.setAktivan(false);
			tipPregledaRepository.save(pregled);
			return true;
		} else {
			return false;
		}
	}

	public boolean update(TipPregledaDTO tipPregledaDTO, TipPregleda tipPregleda) {
		List<Pregled> pregledi = pregledService.findByTipPregledaIdAndOtkazanAndVremeGreaterThan(tipPregledaDTO.getId(),
				false, new Date().getTime());
		TipPregleda postojiNaziv = null;
		if (!tipPregledaDTO.getNaziv().equals(tipPregleda.getNaziv())) {
			postojiNaziv = tipPregledaRepository.findByNazivAndAktivan(tipPregledaDTO.getNaziv(),true);
		}
		if (pregledi.isEmpty() && postojiNaziv == null) {
			TipPregleda pregled = tipPregledaRepository.findById(tipPregledaDTO.getId()).orElseGet(null);
			pregled.setNaziv(tipPregledaDTO.getNaziv());
			pregled.setOpis(tipPregledaDTO.getOpis());
			pregled.getStavkaCenovnika().setCena(tipPregledaDTO.getCena());
			tipPregledaRepository.save(pregled);
			return true;
		} else {
			return false;
		}
	}

	public List<TipPregledaDTO> vratiTipoveKlinike(long id) {
		List<TipPregleda> tipovi = tipPregledaRepository.findByKlinikaIdAndAktivan(id,true);
		List<TipPregledaDTO> tipovidto = new ArrayList<TipPregledaDTO>();
		if (tipovi != null) {
			for (TipPregleda tp : tipovi) {
				tipovidto.add(new TipPregledaDTO(tp));
			}
		}
		return tipovidto;
	}
}
