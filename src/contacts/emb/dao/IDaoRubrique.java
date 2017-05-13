package contacts.emb.dao;

import java.util.List;

import contacts.emb.dom.Rubrique;

public interface IDaoRubrique {
	int			inserer( Rubrique rubrique );

	void 		modifier( Rubrique rubrique );

	void 		supprimer( int idRubrique );

	Rubrique 	retrouver( int idRubrique );

	List<Rubrique> listerTout();
}
