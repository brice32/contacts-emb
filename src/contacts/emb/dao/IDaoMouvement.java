package contacts.emb.dao;

import java.util.List;

import contacts.emb.dom.Annonceur;
import contacts.emb.dom.Mouvement;


public interface IDaoMouvement {

	int			inserer( Mouvement mouvement  );

	void 		modifier( Mouvement mouvement );

	void 		supprimer( int idMouvement );

	Annonceur 	retrouver( int idMouvement );

	List<Mouvement> listerTout();

	Mouvement retouverDerniereMouvement(int idAnnonceur);

	List<Mouvement> retouverListe(int idAnnonceur);

}
