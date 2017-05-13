package contacts.emb.dao;

import java.util.List;

import contacts.emb.dom.Personne;
import contacts.emb.dom.Telephone;


public interface IDaoTelephone {

	void insererPourPersonne(Personne personne);

	void modifierPourPersonne(Personne personne);

	void supprimerPourPersonne(int idPersonne);

	List<Telephone> listerPourPersonne( Personne personne );

}
