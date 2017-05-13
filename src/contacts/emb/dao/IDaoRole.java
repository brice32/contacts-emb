package contacts.emb.dao;

import java.util.List;

import contacts.emb.dom.Compte;


public interface IDaoRole {

	void insererPourCompte(Compte compte);

	void modifierPourCompte(Compte compte);

	void supprimerPourCompte( int idCompte );

	List<String> listerPourCompte( int idCompte );

}
