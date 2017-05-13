package contacts.emb.dom;

public class Categorie {
	//Champs
		private int				idCategorie;

		private String			libelle;

		public Categorie(){

		}

		public Categorie( int idCategorie, String libelle){
			this.setIdCategorie(idCategorie);
			this.setLibelle(libelle);
		}

		public int getIdCategorie() {
			return idCategorie;
		}

		public void setIdCategorie(int idCategorie) {
			this.idCategorie = idCategorie;
		}

		public String getLibelle() {
			return libelle;
		}

		public void setLibelle(String libelle) {
			this.libelle = libelle;
		}


}
