package tim09.klinika.model;

public class Sala {
	private int broj;
	private String info;
	
	public Sala() {
		
	}
	
	public Sala(int broj, String info) {
		this.broj = broj;
		this.info = info;
	}
	
	public int getBroj() {
		return broj;
	}
	public void setBroj(int broj) {
		this.broj = broj;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
}
