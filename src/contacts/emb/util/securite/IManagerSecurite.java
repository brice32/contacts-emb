package contacts.emb.util.securite;

import contacts.commun.service.IServiceAuthentification;
import contacts.commun.util.ExceptionAutorisation;


public interface IManagerSecurite {

	void	setServiceAuthentification(IServiceAuthentification serviceAuthentification);

	int		getIdCompteConnecte();

	void 	verifierAutorisationUtilisateurOuAdmin() throws ExceptionAutorisation;

	void 	verifierAutorisationAdmin() throws ExceptionAutorisation;

	void 	verifierAutorisationAdminOuCompteConnecte( int idCompte ) throws ExceptionAutorisation;

	void    verifierAutorisationSecretaire() throws ExceptionAutorisation;

}