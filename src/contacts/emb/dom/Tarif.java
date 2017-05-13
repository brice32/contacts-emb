package contacts.emb.dom;



public class Tarif {
	
	private int id;
	
	private float tarifConference ;
	
	private float tarifStage;
	
	private float tarifRelief;
	
	private String date;
	
	// constructeur
	
	public Tarif(){
		
	}
    //
	
	public float getTarifConference() {
		return tarifConference;
	}

	public void setTarifConference(float tarifConference) {
		this.tarifConference = tarifConference;
	}

	public float getTarifStage() {
		return tarifStage;
	}

	public void setTarifStage(float tarifStage) {
		this.tarifStage = tarifStage;
	}

	public float getTarifRelief() {
		return tarifRelief;
	}

	public void setTarifRelief(float tarifRelief) {
		this.tarifRelief = tarifRelief;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Tarif(int id,float tarifConference,float tarifStage,float tarifRelief,String date){
		setId(id);
		setTarifConference(tarifConference);
		setTarifStage(tarifStage);
		setTarifRelief(tarifRelief);
	    setDate(date);	
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
