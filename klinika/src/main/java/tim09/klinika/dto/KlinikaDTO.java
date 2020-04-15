package tim09.klinika.dto;

import java.util.ArrayList;
import java.util.Set;

import tim09.klinika.model.AdminKlinike;
import tim09.klinika.model.Cenovnik;
import tim09.klinika.model.Klinika;
import tim09.klinika.model.MedicinskoOsoblje;
import tim09.klinika.model.Odsustvo;
import tim09.klinika.model.Operacija;
import tim09.klinika.model.Popust;
import tim09.klinika.model.Pregled;
import tim09.klinika.model.Sala;
import tim09.klinika.model.TipPregleda;

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
	
	public KlinikaDTO(Klinika klinika) {
		this(klinika.getId(),klinika.getAdmini(),klinika.getOdsustva(),klinika.getOsoblje(),klinika.getPopusti(),klinika.getCenovnik(),klinika.getPregledi(),klinika.getOperacije(),klinika.getTipoviPregleda(),klinika.getSale());
	}

	public KlinikaDTO(Long id, Set<AdminKlinike> admini, Set<Odsustvo> odsustva,
			Set<MedicinskoOsoblje> osoblje, Set<Popust> popusti, Cenovnik cenovnik,
			Set<Pregled> pregledi, Set<Operacija> operacije, Set<TipPregleda> tipoviPregleda,
			Set<Sala> sale) {
		super();
		this.id = id;
		this.admini = new ArrayList<AdminKlinikeDTO>();
		for(AdminKlinike admin:admini){
			this.admini.add(new AdminKlinikeDTO(admin));
		}
		this.odsustva = new ArrayList<OdsustvoDTO>();
		for(Odsustvo odsustvo:odsustva){
			this.odsustva.add(new OdsustvoDTO(odsustvo));
		}
		this.osoblje = new ArrayList<MedicinskoOsobljeDTO>();
		for(MedicinskoOsoblje os:osoblje){
			this.osoblje.add(new MedicinskoOsobljeDTO(os));
		}
		this.popusti = new ArrayList<PopustDTO>();
		for(Popust p:popusti){
			this.popusti.add(new PopustDTO(p));
		}
		this.cenovnik = new CenovnikDTO(cenovnik);
		
		this.pregledi = new ArrayList<PregledDTO>();
		for(Pregled os:pregledi){
			this.pregledi.add(new PregledDTO(os));
		}
		this.operacije = new ArrayList<OperacijaDTO>();
		for(Operacija os:operacije){
			this.operacije.add(new OperacijaDTO(os));
		}
		
		this.tipoviPregleda = new ArrayList<TipPregledaDTO>();
		for(TipPregleda os:tipoviPregleda){
			this.tipoviPregleda.add(new TipPregledaDTO(os));
		}
		this.sale = new ArrayList<SalaDTO>();
		for(Sala os:sale){
			this.sale.add(new SalaDTO(os));
		}
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
