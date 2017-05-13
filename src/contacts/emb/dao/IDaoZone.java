package contacts.emb.dao;

import java.util.List;

import contacts.emb.dom.Zone;

public interface IDaoZone {

	int			inserer( Zone zone );

	void 		modifier( Zone zone );

	void 		supprimer( int idZone );

	Zone 	retrouver( int idZone );

	List<Zone> listerTout();
}
