package contacts.emb.dom;

public class Annonceur {
	private int				id;
	private String			nom;
	private String 			telephone;
	private String 			email;
	private String 			lieuNom;
	private String 			lieuAdresse;
	private String 			lieuCp;
	private String 			lieuVille;
	private String 			siteWeb;

	public Annonceur(){

	}

	public Annonceur(int id,String nom,String telephone,String email,String lieuNom,String lieuAdresse,String lieuCp,String lieuVille,String siteWeb){
		this.setId(id);
		this.setNom(nom);
		this.setTelephone(telephone);
		this.setEmail(email);
		this.setLieuNom(lieuNom);
		this.setLieuAdresse(lieuAdresse);
		this.setLieuCp(lieuCp);
		this.setLieuVille(lieuVille);
		this.setSiteWeb(siteWeb);
	}

	//get & set
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLieuNom() {
		return lieuNom;
	}
	public void setLieuNom(String lieuNom) {
		this.lieuNom = lieuNom;
	}
	public String getLieuAdresse() {
		return lieuAdresse;
	}
	public void setLieuAdresse(String lieuAdresse) {
		this.lieuAdresse = lieuAdresse;
	}
	public String getLieuCp() {
		return lieuCp;
	}
	public void setLieuCp(String lieuCp) {
		this.lieuCp = lieuCp;
	}
	public String getLieuVille() {
		return lieuVille;
	}
	public void setLieuVille(String lieuVille) {
		this.lieuVille = lieuVille;
	}
	public String getSiteWeb() {
		return siteWeb;
	}
	public void setSiteWeb(String siteWeb) {
		this.siteWeb = siteWeb;
	}
}
