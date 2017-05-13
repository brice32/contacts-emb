package contacts.emb.dao;

import java.util.List;

import contacts.emb.dom.Annonceur;


public interface IDaoAnnonceur {

	int			inserer( Annonceur annonceur  );

	void 		modifier( Annonceur annonceur );

	void 		supprimer( int idAnnonceur );

	Annonceur 	retrouver( int idAnnonceur );

	List<Annonceur> listerTout();

}
