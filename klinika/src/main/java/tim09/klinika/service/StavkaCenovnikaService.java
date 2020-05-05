package tim09.klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.dto.PretragaKlinikeDTO;
import tim09.klinika.model.Popust;
import tim09.klinika.model.StavkaCenovnika;
import tim09.klinika.model.TipPregleda;
import tim09.klinika.repository.PopustRepository;
import tim09.klinika.repository.StavkaCenovnikaRepository;
import tim09.klinika.repository.TipPregledaRepository;

@Service
public class StavkaCenovnikaService {

	@Autowired
	private StavkaCenovnikaRepository stavkaCenovnikaRepository;
	
	@Autowired
	private TipPregledaRepository tipPregledaRepository;
	
	@Autowired
	private PopustRepository popustRepository;

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
		List<TipPregleda> tipovi = tipPregledaRepository.findByKlinikaId(pretragaKlinikeDTO.getId());
		if (tipovi != null) {
			for (TipPregleda tp : tipovi) {
				if (tp.getNaziv().equals(pretragaKlinikeDTO.getTipPregleda())) {
					cena = tp.getStavkaCenovnika().getCena();
					Popust popust = popustRepository.nadjiPopust(tp.getStavkaCenovnika().getId(), pretragaKlinikeDTO.getDatum(), pretragaKlinikeDTO.getDatum());
					if (popust != null) {
						cena = cena * (100 - popust.getProcenat()) / 100.00;
					}
				}
			}
		}
		return cena;
	}
}
