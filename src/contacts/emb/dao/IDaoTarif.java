package contacts.emb.dao;



import javax.sql.DataSource;

import contacts.emb.dom.Tarif;

public interface IDaoTarif {

	// injecteur
	void setDataSource(DataSource dataSource);

	// Actions 
	int creationTarif(Tarif tarif);

	void modifierTarif(Tarif tarif);

	void supprimer(int idTarif);

}