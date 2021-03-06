package contacts.emb.dao;

import java.util.List;

import contacts.emb.dom.Annonceur;
import contacts.emb.dom.Categorie;


public interface IDaoCategorie {

	int			inserer( Categorie categorie  );

	void 		modifier( Categorie categorie );

	void 		supprimer( int idCategorie );

	Categorie 	retrouver( int idCategorie );

	List<Categorie> listerTout();

}
