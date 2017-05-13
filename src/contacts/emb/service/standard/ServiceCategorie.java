package contacts.emb.service.standard;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import contacts.commun.dto.DtoAnnonceur;
import contacts.commun.dto.DtoCategorie;
import contacts.commun.service.IServiceCategorie;
import contacts.commun.util.ExceptionAnomalie;
import contacts.commun.util.ExceptionAppli;
import contacts.emb.dao.IDaoAnnonceur;
import contacts.emb.dao.IDaoCategorie;
import contacts.emb.dao.IManagerTransaction;
import contacts.emb.dom.Annonceur;
import contacts.emb.dom.Categorie;
import contacts.emb.util.mapper.IMapperDoDto;
import contacts.emb.util.securite.IManagerSecurite;

public class ServiceCategorie implements IServiceCategorie {

	// Logger
	private static final Logger logger = Logger.getLogger(ServiceCategorie.class.getName());

	// Champs

	private IManagerSecurite managerSecurite;

	private IDaoCategorie daoCategorie;
	private IMapperDoDto mapper;
	private IManagerTransaction managerTransaction;

	// Injecteurs

	public void setManagerSecurite(IManagerSecurite managerSecurite) {
		this.managerSecurite = managerSecurite;
	}

	public void setManagerTransaction(IManagerTransaction managerTransaction) {
		this.managerTransaction = managerTransaction;
	}

	public void setMapper(IMapperDoDto mapper) {
		this.mapper = mapper;
	}

	public void setDaoCategorie(IDaoCategorie daoCategorie) {
		this.daoCategorie = daoCategorie;
	}

	@Override
	public int inserer(DtoCategorie dtoCategorie) throws ExceptionAppli {
		// TODO Auto-generated method stub
		try {
			managerSecurite.verifierAutorisationSecretaire();

			managerTransaction.begin();
			try {
				int id = daoCategorie.inserer(mapper.map(dtoCategorie));
				managerTransaction.commit();
				return id;
			} catch (Exception e) {
				managerTransaction.rollback();
				throw e;
			}

		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new ExceptionAnomalie(e);
		}
	}

	@Override
	public void modifier(DtoCategorie dtoCategorie) throws ExceptionAppli {
		// TODO Auto-generated method stub
		try {

			managerSecurite.verifierAutorisationSecretaire();

			managerTransaction.begin();
			try {
				daoCategorie.modifier(mapper.map(dtoCategorie));
				managerTransaction.commit();
			} catch (Exception e) {
				managerTransaction.rollback();
				throw e;
			}

		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new ExceptionAnomalie(e);
		}
	}

	@Override
	public void supprimer(int idCategorie) throws ExceptionAppli {
		// TODO Auto-generated method stub
		try {

			managerSecurite.verifierAutorisationSecretaire();

			managerTransaction.begin();
			try {
				daoCategorie.supprimer(idCategorie);
				managerTransaction.commit();
			} catch (Exception e) {
				managerTransaction.rollback();
				throw e;
			}

		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new ExceptionAnomalie(e);
		}
	}

	@Override
	public DtoCategorie retrouver(int idCategorie) throws ExceptionAppli {
		// TODO Auto-generated method stub
		try {
			// managerSecurite.verifierAutorisationUtilisateurOuAdmin();
			// return mapPersonnes.get(idPersonne);
			return mapper.map( daoCategorie.retrouver(idCategorie) );
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new ExceptionAnomalie(e);
		}
	}

	@Override
	public List<DtoCategorie> listerTout() throws ExceptionAppli {
		// TODO Auto-generated method stub
		try {

			List<DtoCategorie> categories = new ArrayList<>();
			for (Categorie categorie : daoCategorie.listerTout()) {
				categories.add(mapper.map(categorie));
			}
			return categories;

		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new ExceptionAnomalie(e);
		}
	}

}
