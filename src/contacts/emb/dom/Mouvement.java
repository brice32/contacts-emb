package contacts.emb.dom;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Mouvement {
	//Champs
		private int				idMouvement;

//		private int				idAnnonceur;

		private float			montant;

		private String 			date;

		private String          heure;

		private float           solde;

		private String			libelle;

		private String          description;

		private Annonceur	annonceur = new Annonceur();

		public Mouvement(){

		}

		public Mouvement(int idMouvement, float montant, String date, String heure, float solde, String lebelle, String description ){
			this.setIdMouvement(idMouvement);
//			this.setIdAnnonceur(idAnnonceur);
			this.setMontant(montant);
			this.setDate(date);
			this.setHeure(heure);
			this.setSolde(solde);
			this.setLibelle(lebelle);
			this.setDescription(description);
		}

		public int getIdMouvement() {
			return idMouvement;
		}

		public void setIdMouvement(int idMouvement) {
			this.idMouvement = idMouvement;
		}

//		public int getIdAnnonceur() {
//			return idAnnonceur;
//		}
//
//		public void setIdAnnonceur(int idAnnonceur) {
//			this.idAnnonceur = idAnnonceur;
//		}

		public float getMontant() {
			return montant;
		}

		public void setMontant(float montant) {
			this.montant = montant;
		}

//		public Date getDate() {
//			return date;
//		}
//
//		public void setDate(Date date) {
//			this.date = date;
//		}
//
//		public Time getHeure() {
//			return heure;
//		}
//
//		public void setHeure(Time heure) {
//			this.heure = heure;
//		}

		public float getSolde() {
			return solde;
		}

		public void setSolde(float solde) {
			this.solde = solde;
		}

		public String getLibelle() {
			return libelle;
		}

		public void setLibelle(String libelle) {
			this.libelle = libelle;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

//		public List<Annonceur> getAnnonceurs() {
//			return annonceurs;
//		}
//
//		public void setAnnonceurs(List<Annonceur> annonceurs) {
//			this.annonceurs = annonceurs;
//		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getHeure() {
			return heure;
		}

		public void setHeure(String heure) {
			this.heure = heure;
		}

		public Annonceur getAnnonceur() {
			return annonceur;
		}

		public void setAnnonceur(Annonceur annonceur) {
			this.annonceur = annonceur;
		}

		// Actions



}
