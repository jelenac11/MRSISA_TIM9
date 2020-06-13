package klinika.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import klinika.dto.TipPregledaDTO;
import klinika.model.Lekar;
import klinika.model.Pregled;
import klinika.model.TipPregleda;
import klinika.repository.TipPregledaRepository;

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
	@Transactional(readOnly = false)
	public boolean remove(Long id) {
		List<Pregled> pregledi = pregledService.findByTipPregledaIdAndVremeGreaterThan(id, new Date().getTime());

		if (pregledi.isEmpty()) {
			TipPregleda pregled = findOne(id);
			pregled.setAktivan(false);

			for (Lekar l : pregled.getLekari()) {
				l.getSpecijalnosti().remove(pregled);
			}

			tipPregledaRepository.save(pregled);
			return true;
		} else {
			return false;
		}
	}

	public boolean update(TipPregledaDTO tipPregledaDTO, TipPregleda tipPregleda) {
		List<Pregled> pregledi = pregledService.findByTipPregledaIdAndVremeGreaterThan(tipPregledaDTO.getId(),
				new Date().getTime());
		TipPregleda postojiNaziv = null;
		if (!tipPregledaDTO.getNaziv().equals(tipPregleda.getNaziv())) {
			postojiNaziv = tipPregledaRepository.findByNazivAndAktivan(tipPregledaDTO.getNaziv(), true);
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

	// Metoda koja vraća sve tipove preglede za izabranu kliniku
	public List<TipPregledaDTO> vratiTipoveKlinike(long id) {
		List<TipPregleda> tipovi = tipPregledaRepository.findByKlinikaIdAndAktivan(id, true);
		List<TipPregledaDTO> tipovidto = new ArrayList<>();
		if (tipovi != null) {
			for (TipPregleda tp : tipovi) {
				tipovidto.add(new TipPregledaDTO(tp));
			}
		}
		return tipovidto;
	}
}
