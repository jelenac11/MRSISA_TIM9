package tim09.klinika.dto;

import java.util.ArrayList;

public class KlinikaDTO {

	private Long id;
	private ArrayList<AdminKlinikeDTO> admini;
	private ArrayList<OdsustvoDTO> odsustva;
	private ArrayList<MedicinskoOsobljeDTO> osoblje;
	private ArrayList<PopustDTO> popusti;
	private CenovnikDTO cenovnik;
	private ArrayList<PregledDTO> pregledi;
	private ArrayList<OperacijaDTO> operacije;
	private ArrayList<TipPregledaDTO> tipoviPregleda;
	private ArrayList<SalaDTO> sale;

	public KlinikaDTO() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArrayList<AdminKlinikeDTO> getAdmini() {
		return admini;
	}

	public void setAdmini(ArrayList<AdminKlinikeDTO> admini) {
		this.admini = admini;
	}

	public ArrayList<OdsustvoDTO> getOdsustva() {
		return odsustva;
	}

	public void setOdsustva(ArrayList<OdsustvoDTO> odsustva) {
		this.odsustva = odsustva;
	}

	public ArrayList<MedicinskoOsobljeDTO> getOsoblje() {
		return osoblje;
	}

	public void setOsoblje(ArrayList<MedicinskoOsobljeDTO> osoblje) {
		this.osoblje = osoblje;
	}

	public ArrayList<PopustDTO> getPopusti() {
		return popusti;
	}

	public void setPopusti(ArrayList<PopustDTO> popusti) {
		this.popusti = popusti;
	}

	public CenovnikDTO getCenovnik() {
		return cenovnik;
	}

	public void setCenovnik(CenovnikDTO cenovnik) {
		this.cenovnik = cenovnik;
	}

	public ArrayList<PregledDTO> getPregledi() {
		return pregledi;
	}

	public void setPregledi(ArrayList<PregledDTO> pregledi) {
		this.pregledi = pregledi;
	}

	public ArrayList<OperacijaDTO> getOperacije() {
		return operacije;
	}

	public void setOperacije(ArrayList<OperacijaDTO> operacije) {
		this.operacije = operacije;
	}

	public ArrayList<TipPregledaDTO> getTipoviPregleda() {
		return tipoviPregleda;
	}

	public void setTipoviPregleda(ArrayList<TipPregledaDTO> tipoviPregleda) {
		this.tipoviPregleda = tipoviPregleda;
	}

	public ArrayList<SalaDTO> getSale() {
		return sale;
	}

	public void setSale(ArrayList<SalaDTO> sale) {
		this.sale = sale;
	}
}
