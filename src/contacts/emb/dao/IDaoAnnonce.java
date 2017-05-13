package contacts.emb.dao;

import java.util.List;

import contacts.emb.dom.Annonce;
import contacts.emb.dom.Categorie;

public interface IDaoAnnonce {

	List<Annonce> listerTout();

	Annonce retrouver(int idAnnonce);

	int inserer(Annonce annonce);

	void modifier(Annonce annonce);

}
