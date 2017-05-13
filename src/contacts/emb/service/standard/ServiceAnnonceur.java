package contacts.emb.service.standard;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import contacts.commun.dto.DtoAnnonceur;
import contacts.commun.service.IServiceAnnonceur;
import contacts.commun.util.ExceptionAnomalie;
import contacts.commun.util.ExceptionAppli;
import contacts.emb.dao.IDaoAnnonceur;
import contacts.emb.dao.IManagerTransaction;
import contacts.emb.dom.Annonceur;
import contacts.emb.util.mapper.IMapperDoDto;
import contacts.emb.util.securite.IManagerSecurite;

public class ServiceAnnonceur implements IServiceAnnonceur {

	// Logger
	private static final Logger logger = Logger.getLogger(ServiceAnnonceur.class.getName());


	// Champs

	private IManagerSecurite managerSecurite;

	private IDaoAnnonceur daoAnnonceur;
	private IMapperDoDto mapper;
	private IManagerTransaction managerTransaction;

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

	public void setDaoAnnonceur(IDaoAnnonceur daoAnnonceur) {
		this.daoAnnonceur = daoAnnonceur;
	}



	@Override
	public int inserer(DtoAnnonceur dtoAnnonceur) throws ExceptionAppli {
		// TODO Auto-generated method stub
		try {
		managerSecurite.verifierAutorisationSecretaire();

		managerTransaction.begin();
		try {
			int id = daoAnnonceur.inserer( mapper.map( dtoAnnonceur ) );
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
	public void modifier(DtoAnnonceur dtoannonceur) throws ExceptionAppli {
		try {

			managerSecurite.verifierAutorisationSecretaire();

			managerTransaction.begin();
			try {
				daoAnnonceur.modifier( mapper.map( dtoannonceur ) );
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
	public void supprimer(int idAnnonceur) throws ExceptionAppli {
	try {

			managerSecurite.verifierAutorisationSecretaire();

			managerTransaction.begin();
			try {
				daoAnnonceur.supprimer(idAnnonceur);
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
	public DtoAnnonceur retrouver(int idAnnonceur) throws ExceptionAppli {
		// TODO Auto-generated method stub
		try {
//			managerSecurite.verifierAutorisationUtilisateurOuAdmin();
//			return mapPersonnes.get(idPersonne);
			return mapper.map(daoAnnonceur.retrouver(idAnnonceur));
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new ExceptionAnomalie(e);
		}
	}

	@Override
	public List<DtoAnnonceur> listerTout() throws ExceptionAppli {
		try {

			List<DtoAnnonceur> annonceurs = new ArrayList<>();
			for( Annonceur annonceur : daoAnnonceur.listerTout() ) {
				annonceurs.add( mapper.map( annonceur ) );
			}
			return annonceurs;

		} catch (RuntimeException e) {
			logger.log( Level.SEVERE, e.getMessage(), e );
			throw new ExceptionAnomalie(e);
		}
	}

}
