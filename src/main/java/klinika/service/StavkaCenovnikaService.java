package klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import klinika.dto.PretragaKlinikeDTO;
import klinika.model.Popust;
import klinika.model.StavkaCenovnika;
import klinika.model.TipPregleda;
import klinika.repository.PopustRepository;
import klinika.repository.StavkaCenovnikaRepository;
import klinika.repository.TipPregledaRepository;

@Service
public class StavkaCenovnikaService {

	@Autowired
	private StavkaCenovnikaRepository stavkaCenovnikaRepository;

	@Autowired
	private TipPregledaRepository tipPregledaRepository;

	@Autowired
	private PopustRepository popustRepository;

	public StavkaCenovnika findByTipPregleda(TipPregleda tp) {
		return stavkaCenovnikaRepository.findByTipPregleda(tp);
	}

	public StavkaCenovnika findOne(Long id) {
		return stavkaCenovnikaRepository.findById(id).orElseGet(null);
	}

	public List<StavkaCenovnika> findAll() {
		return stavkaCenovnikaRepository.findAll();
	}

	public StavkaCenovnika save(StavkaCenovnika stavkaCenovnika) {
		return stavkaCenovnikaRepository.save(stavkaCenovnika);
	}

	public void remove(Long id) {
		stavkaCenovnikaRepository.deleteById(id);
	}

	public double vratiCenuPregleda(PretragaKlinikeDTO pretragaKlinikeDTO) {
		double cena = 0;
		List<TipPregleda> tipovi = tipPregledaRepository.findByKlinikaIdAndAktivan(pretragaKlinikeDTO.getId(), true);
		if (tipovi != null) {
			for (TipPregleda tp : tipovi) {
				if (tp.getNaziv().equals(pretragaKlinikeDTO.getTipPregleda())) {
					cena = tp.getStavkaCenovnika().getCena();
					Popust popust = popustRepository.nadjiPopust(tp.getStavkaCenovnika().getId(),
							pretragaKlinikeDTO.getDatum(), pretragaKlinikeDTO.getDatum());
					if (popust != null) {
						cena = cena * (100 - popust.getProcenat()) / 100.00;
					}
				}
			}
		}
		return cena;
	}
}
