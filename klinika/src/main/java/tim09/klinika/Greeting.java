package tim09.klinika;

public class Greeting {
	
	private String message;
	
	private Long id;
	
	public Greeting() {
		
	}
	
	public Greeting(Long _id, String msg) {
		id = _id;
		message = msg;
	}
	
	public String getMessage(){
		return message;
	}
	
	public void setMessage(String mess) {
		message = mess;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id_) {
		id = id_;
	}

}
