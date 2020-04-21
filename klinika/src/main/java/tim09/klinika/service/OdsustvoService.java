package tim09.klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.model.Odsustvo;
import tim09.klinika.repository.OdsustvoRepository;

@Service
public class OdsustvoService {

	@Autowired
	private OdsustvoRepository odsustvoRepository;

	public Odsustvo findOne(Long id) {
		return odsustvoRepository.findById(id).orElseGet(null);
	}

	public List<Odsustvo> findAll() {
		return odsustvoRepository.findAll();
	}

	public Odsustvo save(Odsustvo odsustvo) {
		return odsustvoRepository.save(odsustvo);
	}

	public void remove(Long id) {
		odsustvoRepository.deleteById(id);
	}
}
