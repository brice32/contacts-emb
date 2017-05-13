package contacts.emb.util.securite;

import java.util.logging.Level;
import java.util.logging.Logger;

import contacts.commun.dto.DtoCompte;
import contacts.commun.service.IServiceAuthentification;
import contacts.commun.service.IServiceConnexion;
import contacts.commun.util.ExceptionAnomalie;
import contacts.commun.util.ExceptionAppli;
import contacts.commun.util.ExceptionAutorisation;
import contacts.commun.util.Roles;


public class ManagerSecurite implements IManagerSecurite, IServiceConnexion {


	// Logger
	private static final Logger	logger = Logger.getLogger(ManagerSecurite.class.getName());


	// Champs

	protected final ThreadLocal<DtoCompte> tlCompteConnecte = new ThreadLocal<>();

	private IServiceAuthentification	serviceAuthentification;


	// Injecteurs

	@Override
	public void setServiceAuthentification( IServiceAuthentification serviceAuthentification) {
		this.serviceAuthentification = serviceAuthentification;
	}


	// Actions accessibles via l'interface IServiceConnexion

	@Override
	public DtoCompte sessionUtilisateurOuvrir( String pseudo, String motDePasse ) throws ExceptionAppli {

		DtoCompte	compteConnecte = null;

		try {
			sessionUtilisateurFermer();
			compteConnecte = serviceAuthentification.verifierAuthentification(pseudo, motDePasse);
			return compteConnecte;
		} catch ( RuntimeException e) {
			logger.log( Level.SEVERE, e.getMessage(), e );
			throw new ExceptionAnomalie(e);
		} finally {
			tlCompteConnecte.set(compteConnecte);
		}
	}


	@Override
	public void sessionUtilisateurFermer() throws ExceptionAppli {
		try {
			tlCompteConnecte.set(null);
		} catch ( RuntimeException e) {
			logger.log( Level.SEVERE, e.getMessage(), e );
			throw new ExceptionAnomalie(e);
		}
	}


	// Actions accessibles via l'interface ImanagerService

	@Override
	public int getIdCompteConnecte() {
		return tlCompteConnecte.get().getId();
	}

	// Vérifie que le compte connecté a le rôle utilisateur ou administrateur
	@Override
	public void verifierAutorisationUtilisateurOuAdmin() throws ExceptionAutorisation {
		DtoCompte compteConnecte = tlCompteConnecte.get();
		if (
				compteConnecte == null
				|| (
						! compteConnecte.isInRole( Roles.MODERATEUR )
						&& ! compteConnecte.isInRole( Roles.ADMINISTRATEUR )
				)
			) {
			throw new ExceptionAutorisation();
		}
	}

	// Vérifie que le compte connecté a le rôle administrateur
	@Override
	public void verifierAutorisationAdmin() throws ExceptionAutorisation {
		DtoCompte compteConnecte = tlCompteConnecte.get();
		if (
				compteConnecte == null
				|| ! compteConnecte.isInRole( Roles.ADMINISTRATEUR )
//				compteConnecte == null
//				|| ! compteConnecte.isInRole( "Administrateur" )
			) {
			throw new ExceptionAutorisation();
		}
	}

	public void verifierAutorisationSecretaire() throws ExceptionAutorisation {
		DtoCompte compteConnecte = tlCompteConnecte.get();
		if (
				compteConnecte == null
				|| ! compteConnecte.isInRole( Roles.SECRETAIRE)
//				compteConnecte == null
//				|| ! compteConnecte.isInRole( "Administrateur" )
			) {
			throw new ExceptionAutorisation();
		}
	}

	// Vérifie que le compte connecte, soit a le rôle administrateur
	// soit a comme identifiant celui passé en paramètre
	@Override
	public void verifierAutorisationAdminOuCompteConnecte( int idCompte ) throws ExceptionAutorisation {
		DtoCompte compteConnecte = tlCompteConnecte.get();
		if (
				compteConnecte == null
				|| (
						! compteConnecte.isInRole( Roles.ADMINISTRATEUR )
						&& compteConnecte.getId() != idCompte
				)
			) {
			throw new ExceptionAutorisation();
		}
	}


}
