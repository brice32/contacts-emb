package contacts.emb.service.standard;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import contacts.commun.dto.DtoCompte;
import contacts.commun.service.IServiceCompte;
import contacts.commun.util.ExceptionAnomalie;
import contacts.commun.util.ExceptionAppli;
import contacts.commun.util.ExceptionValidation;
import contacts.emb.dao.IDaoCompte;
import contacts.emb.dao.IManagerTransaction;
import contacts.emb.dom.Compte;
import contacts.emb.util.mapper.IMapperDoDto;
import contacts.emb.util.securite.IManagerSecurite;


public class ServiceCompte implements IServiceCompte {

	
	// Logger
	private static final Logger	logger = Logger.getLogger(ServiceCompte.class.getName());

	
	// Champs 

	private IManagerSecurite		managerSecurite;
    private	IManagerTransaction		managerTransaction;
	private IMapperDoDto			mapper;
	private IDaoCompte				daoCompte;
	
	
	// Injecteurs
	
	public void setManagerSecurite(IManagerSecurite managerSecurite) {
		this.managerSecurite = managerSecurite;
	}
	
	public void setManagerTransaction(IManagerTransaction managerTransaction) {
		this.managerTransaction = managerTransaction;
	}
	
	public void setMapper( IMapperDoDto mapper ) {
		this.mapper = mapper;
	}

	public void setDaoCompte(IDaoCompte daoCompte) {
		this.daoCompte = daoCompte;
	}

	
	// Actions 

	@Override
	public int inserer(DtoCompte dtoCompte) throws ExceptionAppli {
		
		try {
			
			managerSecurite.verifierAutorisationAdmin();
			verifierValiditeDonnees( dtoCompte );

			managerTransaction.begin();
			try {
				int id = daoCompte.inserer( mapper.map( dtoCompte ) );
				managerTransaction.commit();
				return id;
			} catch (Exception e) {
				managerTransaction.rollback();
				throw e;
			}

		} catch (RuntimeException e) {
			logger.log( Level.SEVERE, e.getMessage(), e );
			throw new ExceptionAnomalie(e);
		}
	}

	@Override
	public void modifier(DtoCompte dtoCompte) throws ExceptionAppli { 
		try {

			managerSecurite.verifierAutorisationAdmin();
			verifierValiditeDonnees( dtoCompte );

			managerTransaction.begin();
			try {
				daoCompte.modifier( mapper.map( dtoCompte ) );
				managerTransaction.commit();
			} catch (Exception e) {
				managerTransaction.rollback();
				throw e;
			}

		} catch (RuntimeException e) {
			logger.log( Level.SEVERE, e.getMessage(), e );
			throw new ExceptionAnomalie(e);
		}
		
	}

	@Override
	public void supprimer(int idCompte) throws ExceptionAppli  {
		try {
			
			managerSecurite.verifierAutorisationAdmin();
			if ( managerSecurite.getIdCompteConnecte() == idCompte ) {
				throw new ExceptionValidation("Vous ne pouvez pas supprimer le compte avec lequel vous êtes connecté !");
			}

			managerTransaction.begin();
			try {
				daoCompte.supprimer(idCompte);
				managerTransaction.commit();
			} catch (Exception e) {
				managerTransaction.rollback();
				throw e;
			}

		} catch (RuntimeException e) {
			logger.log( Level.SEVERE, e.getMessage(), e );
			throw new ExceptionAnomalie(e);
		}
	}

	
	@Override
	public DtoCompte retrouver(int idCompte) throws ExceptionAppli {
		try {
			
			managerSecurite.verifierAutorisationAdmin();
			return mapper.map( daoCompte.retrouver(idCompte) );

		} catch (RuntimeException e) {
			logger.log( Level.SEVERE, e.getMessage(), e );
			throw new ExceptionAnomalie(e);
		}
	}

	
	@Override
	public List<DtoCompte> listerTout() throws ExceptionAppli {
		try {

			managerSecurite.verifierAutorisationAdmin();
			List<DtoCompte> liste = new ArrayList<>();
			for( Compte compte : daoCompte.listerTout() ) {
				liste.add( mapper.map( compte ) );
			}
			return liste;

		} catch (RuntimeException e) {
			logger.log( Level.SEVERE, e.getMessage(), e );
			throw new ExceptionAnomalie(e);
		}
	}
	
	
	// Méthodes auxiliaires
	
	private void verifierValiditeDonnees( DtoCompte dtoCompte ) throws ExceptionAppli {
		
		StringBuilder message = new StringBuilder();
		
		if ( dtoCompte.getPseudo() == null || dtoCompte.getPseudo().isEmpty() ) {
			message.append( "\nLe pseudo est absent." );
		} else 	if ( dtoCompte.getPseudo().length() < 3 ) {
			message.append( "\nLe pseudo est trop court." );
		} else 	if ( dtoCompte.getPseudo().length() > 25 ) {
			message.append( "\nLe pseudo est trop long." );
		} else 	if ( ! daoCompte.verifierUnicitePseudo( dtoCompte.getPseudo(), dtoCompte.getId() ) ) {
			message.append( "\nLe pseudo " + dtoCompte.getPseudo() + " est déjà utilisé." );
		}
		
		if ( dtoCompte.getMotDePasse() == null || dtoCompte.getMotDePasse().isEmpty() ) {
			message.append( "\nLe mot de passe est absent." );
		} else 	if ( dtoCompte.getMotDePasse().length() < 3 ) {
			message.append( "\nLe mot de passe est trop court." );
		} else 	if ( dtoCompte.getMotDePasse().length() > 25 ) {
			message.append( "\nLe mot de passe est trop long." );
		}
		
		if ( dtoCompte.getEmail() == null || dtoCompte.getEmail().isEmpty() ) {
			message.append( "\nL'adresse e-mail est absente." );
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}
	}
	
}
