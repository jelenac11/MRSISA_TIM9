package tim09.klinika.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim09.klinika.model.AdminCentra;
import tim09.klinika.repository.AdminCentraRepository;

@Service
public class AdminCentraService {

	@Autowired
	private AdminCentraRepository adminCentraRepository;

	public AdminCentra findOne(Long id) {
		return adminCentraRepository.findById(id).orElseGet(null);
	}

	public List<AdminCentra> findAll() {
		return adminCentraRepository.findAll();
	}

	public AdminCentra save(AdminCentra adminCentra) {
		return adminCentraRepository.save(adminCentra);
	}

	public void remove(Long id) {
		adminCentraRepository.deleteById(id);
	}
}
