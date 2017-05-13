package contacts.emb.dom;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import contacts.commun.util.Statute;

public class Annonce {
	private int          	idAnnonce;
	private String 			titre;
	private String 			description;
	private Statute         statute;
	private Date       dateDebut;
	private Date       dateFin;
	private Time       heureDebut;
	private Time       heureFin;
	private String 			lieuNom;
	private String  		lieuAdresse;
	private String 			lieuCp;
	private String 			lieuVille;
	private String 			animateurNom;
	private String 			animateurQualification;
	private String 			organisateurNom;
	private String 			organisateurSiteWeb;
	private String 			organisateurEmail;
	private String			organisateurTelephone;

	private Annonceur	annonceur = new Annonceur();
	private Rubrique    rubrique  = new Rubrique();
	private Zone		zone      = new Zone();
	private Categorie   categorie = new Categorie();

	public Annonce(){

	}

	public Annonce(int idAnnocne,String titre,String description,String statute,Date dateDebut,Date dateFin,Time heureDebut,Time heureFin,
			String lieuNom,String lieuAdresse,String lieuCp,String lieuVille,String animateurNom,String animateurQualification,String organisateurNom,
			String organisateurSiteWeb,String organisateurEmail,String organisateurTelephone)
	{
		this.setIdAnnonce(idAnnocne);
		this.setTitre(titre);
		this.setDescription(description);
		this.setStatute(statute);
		this.setDateDebut(dateDebut);
		this.setDateFin(dateFin);
		this.setHeureDebut(heureDebut);
	    this.setHeureFin(heureFin);
		this.setLieuNom(lieuNom);
		this.setLieuAdresse(lieuAdresse);
		this.setLieuCp(lieuCp);
		this.setLieuVille(lieuVille);
		this.setAnimateurNom(animateurNom);
		this.setAnimateurQualification(animateurQualification);
		this.setOrganisateurNom(organisateurNom);
		this.setOrganisateurSiteWeb(organisateurSiteWeb);
		this.setOrganisateurEmail(organisateurEmail);
		this.setOrganisateurTelephone(organisateurTelephone);



	}

	public int getIdAnnonce() {
		return idAnnonce;
	}

	public void setIdAnnonce(int idAnnonce) {
		this.idAnnonce = idAnnonce;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Statute getStatute() {
		return statute;
	}

	public void setStatute(String statute) {
		this.statute=Statute.valueOf(statute);
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public Time getHeureDebut() {
		return heureDebut;
	}

	public void setHeureDebut(Time heureDebut) {
		this.heureDebut = heureDebut;
	}

	public Time getHeureFin() {
		return heureFin;
	}

	public void setHeureFin(Time heureFin) {
		this.heureFin = heureFin;
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

	public String getAnimateurNom() {
		return animateurNom;
	}

	public void setAnimateurNom(String animateurNom) {
		this.animateurNom = animateurNom;
	}

	public String getAnimateurQualification() {
		return animateurQualification;
	}

	public void setAnimateurQualification(String animateurQualification) {
		this.animateurQualification = animateurQualification;
	}

	public String getOrganisateurNom() {
		return organisateurNom;
	}

	public void setOrganisateurNom(String organisateurNom) {
		this.organisateurNom = organisateurNom;
	}

	public String getOrganisateurSiteWeb() {
		return organisateurSiteWeb;
	}

	public void setOrganisateurSiteWeb(String organisateurSiteWeb) {
		this.organisateurSiteWeb = organisateurSiteWeb;
	}

	public String getOrganisateurEmail() {
		return organisateurEmail;
	}

	public void setOrganisateurEmail(String organisateurEmail) {
		this.organisateurEmail = organisateurEmail;
	}

	public String getOrganisateurTelephone() {
		return organisateurTelephone;
	}

	public void setOrganisateurTelephone(String organisateurTelephone) {
		this.organisateurTelephone = organisateurTelephone;
	}

	public Annonceur getAnnonceur() {
		return annonceur;
	}

	public void setAnnonceur(Annonceur annonceur) {
		this.annonceur = annonceur;
	}

	public Rubrique getRubrique() {
		return rubrique;
	}

	public void setRubrique(Rubrique rubrique) {
		this.rubrique = rubrique;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}



}
