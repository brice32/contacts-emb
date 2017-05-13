package contacts.emb.service.standard;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import contacts.commun.dto.DtoAnnonceur;
import contacts.commun.dto.DtoMouvement;
import contacts.commun.service.IServiceMouvement;
import contacts.commun.util.ExceptionAnomalie;
import contacts.commun.util.ExceptionAppli;
import contacts.commun.util.ExceptionValidation;
import contacts.emb.dao.IDaoAnnonceur;
import contacts.emb.dao.IDaoMouvement;
import contacts.emb.dao.IManagerTransaction;
import contacts.emb.dao.jdbc.DaoAnnonceur;
import contacts.emb.dom.Annonceur;
import contacts.emb.dom.Mouvement;
import contacts.emb.util.mapper.IMapperDoDto;
import contacts.emb.util.securite.IManagerSecurite;

public class ServiceMouvement implements IServiceMouvement {

	// Logger
		private static final Logger logger = Logger.getLogger(ServiceMouvement.class.getName());


		// Champs

		private IManagerSecurite managerSecurite;

		private IDaoMouvement daoMouvement;
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

		public void setDaoMouvement(IDaoMouvement daoMouvement) {
			this.daoMouvement = daoMouvement;
		}

		public void setDaoAnnonceur(IDaoAnnonceur daoAnnonceur) {
			this.daoAnnonceur = daoAnnonceur;
		}

	@Override
	public int inserer(DtoMouvement dtoMouvement) throws ExceptionAppli {
		// TODO Auto-generated method stub
		try {
			managerSecurite.verifierAutorisationSecretaire();
			verifierValiditeDonnees(dtoMouvement);

			managerTransaction.begin();
			try {
				int id = daoMouvement.inserer( mapper.map( dtoMouvement ) );
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
	public void modifier(DtoMouvement dtoMouvement) throws ExceptionAppli {
		// TODO Auto-generated method stub
		try {

			managerSecurite.verifierAutorisationSecretaire();

			managerTransaction.begin();
			try {
				daoMouvement.modifier( mapper.map( dtoMouvement ) );
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
	public void supprimer(int idMouvement) throws ExceptionAppli {
		// TODO Auto-generated method stub
		try {

			managerSecurite.verifierAutorisationSecretaire();

			managerTransaction.begin();
			try {
				daoMouvement.supprimer(idMouvement);
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
	public DtoAnnonceur retrouver(int idMouvement) throws ExceptionAppli {
		// TODO Auto-generated method stub
		try {
//			managerSecurite.verifierAutorisationUtilisateurOuAdmin();
//			return mapPersonnes.get(idPersonne);
			return mapper.map(daoMouvement.retrouver(idMouvement));
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new ExceptionAnomalie(e);
		}
	}

	@Override
	public List<DtoMouvement> listerTout() throws ExceptionAppli {
		// TODO Auto-generated method stub
		try {

			List<DtoMouvement> mouvements = new ArrayList<>();
			for( Mouvement mouvement : daoMouvement.listerTout() ) {
				mouvements.add( mapper.map( mouvement ) );
			}
			return mouvements;

		} catch (RuntimeException e) {
			logger.log( Level.SEVERE, e.getMessage(), e );
			throw new ExceptionAnomalie(e);
		}
	}

	@Override
	public DtoMouvement retrouverIdannonceur(int idAnnonceur) throws ExceptionAppli{
		try {
			return mapper.map(daoMouvement.retouverDerniereMouvement(idAnnonceur));
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new ExceptionAnomalie(e);
		}
	}

	@Override
	public List<DtoMouvement> retouverListe(int idAnnonceur) throws ExceptionAppli{
		try{
			DtoMouvement dtomouvement;
			Annonceur annonceur = daoAnnonceur.retrouver(idAnnonceur);
			List<DtoMouvement> mouvements = new ArrayList<>();
			for( Mouvement mouvement : daoMouvement.retouverListe(idAnnonceur)){
//				System.out.print(daoAnnonceur.retrouver(idAnnonceur).getNom());
//				mapper.update(daoAnnonceur.retrouver(idAnnonceur), mouvement.getAnnonceur());
//				mouvement.setAnnonceur(daoAnnonceur.retrouver(idAnnonceur));
				dtomouvement=mapper.map(mouvement);
				dtomouvement.setAnnonceur(mapper.map(mouvement.getAnnonceur()));
				mouvements.add(dtomouvement);
			}
			return mouvements;
		} catch (RuntimeException e){
			logger.log( Level.SEVERE, e.getMessage(), e );
			throw new ExceptionAnomalie(e);
		}
	}

	// Méthodes auxiliaires
	private void verifierValiditeDonnees( DtoMouvement dtomouvement ) throws ExceptionAppli{
		StringBuilder message = new StringBuilder();

		if (dtomouvement.getMontant() == 0){
			message.append( "\nLe montant est absent." );
		}else if( dtomouvement.getMontant()+dtomouvement.getSolde() < 0 ){
			message.append("\nSolde du compte est insuffisante("+(dtomouvement.getMontant() + dtomouvement.getSolde())+"euros).");
		}

		if ( dtomouvement.getLibelle() == null || dtomouvement.getLibelle().isEmpty() ) {
			message.append( "\nLe libelle est absent." );
		}else if(dtomouvement.getLibelle().length() > 100){
			message.append("\nLe libelle est trop long.");
		}

		if( dtomouvement.getDescription() == null || dtomouvement.getDescription().isEmpty()){

		}else if(dtomouvement.getDescription().length() > 254){
			message.append("\nLe description est trop long.("+dtomouvement.getDescription().length()+" > 254)");
		}

		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}
	}
}
