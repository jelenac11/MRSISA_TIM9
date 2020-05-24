package klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import klinika.model.ZdravstveniKarton;
import klinika.repository.ZdravstveniKartonRepository;

@Service
public class ZdravstveniKartonService {

	@Autowired
	private ZdravstveniKartonRepository zdravstveniKartonRepository;

	public ZdravstveniKarton findOne(Long id) {
		return zdravstveniKartonRepository.findById(id).orElseGet(null);
	}

	public List<ZdravstveniKarton> findAll() {
		return zdravstveniKartonRepository.findAll();
	}

	public ZdravstveniKarton save(ZdravstveniKarton zdravstveniKarton) {
		return zdravstveniKartonRepository.save(zdravstveniKarton);
	}

	public void remove(Long id) {
		zdravstveniKartonRepository.deleteById(id);
	}
}
