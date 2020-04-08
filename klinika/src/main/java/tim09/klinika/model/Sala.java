package tim09.klinika.model;

public class Sala {
	private int broj;
	private int info;
	
	public Sala() {
		
	}
	
	public Sala(int broj, int info) {
		this.broj = broj;
		this.info = info;
	}
	
	public int getBroj() {
		return broj;
	}
	public void setBroj(int broj) {
		this.broj = broj;
	}
	public int getInfo() {
		return info;
	}
	public void setInfo(int info) {
		this.info = info;
	}
}
