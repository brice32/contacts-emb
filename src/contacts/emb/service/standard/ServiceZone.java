package contacts.emb.service.standard;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import contacts.commun.dto.DtoZone;
import contacts.commun.service.IServiceZone;
import contacts.commun.util.ExceptionAnomalie;
import contacts.commun.util.ExceptionAppli;
import contacts.commun.util.ExceptionValidation;
import contacts.emb.dao.IDaoZone;
import contacts.emb.dao.IManagerTransaction;
import contacts.emb.dom.Zone;
import contacts.emb.util.mapper.IMapperDoDto;
import contacts.emb.util.securite.IManagerSecurite;

public class ServiceZone implements IServiceZone{

	// Logger
		private static final Logger logger = Logger.getLogger(ServicePersonne.class.getName());

		// Champs

		// private Donnees donnees;
		// private Map<Integer, DtoPersonne> mapPersonnes;

		private IManagerSecurite managerSecurite;

		private IDaoZone daoZone;
		private IMapperDoDto mapper;
		private IManagerTransaction managerTransaction;

		public void setManagerSecurite(IManagerSecurite managerSecurite) {
			this.managerSecurite = managerSecurite;
		}

		// Actions

		@Override
		public int inserer(DtoZone dtoZone) throws ExceptionAppli {
			try {
				managerSecurite.verifierAutorisationUtilisateurOuAdmin();
				verifierValiditeDonnees(dtoZone);
				// if ( mapPersonnes.isEmpty() ) {
				// dtoPersonne.setId( 1 );
				// } else {
				// dtoPersonne.setId( Collections.max( mapPersonnes.keySet() ) + 1
				// );
				// }
				// affecterIdTelephones(dtoPersonne);
				// mapPersonnes.put( dtoPersonne.getId(), dtoPersonne );
				// return dtoPersonne.getId();
				managerTransaction.begin();
				try {
					int id = daoZone.inserer(mapper.map(dtoZone));
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
		public void modifier(DtoZone dtoZone) throws ExceptionAppli {
			try {
				managerSecurite.verifierAutorisationUtilisateurOuAdmin();
				verifierValiditeDonnees(dtoZone);
//				affecterIdTelephones(dtoPersonne);
//				mapPersonnes.replace(dtoPersonne.getId(), dtoPersonne);
				managerTransaction.begin();
				try {
				daoZone.modifier( mapper.map( dtoZone ) );
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
		public void supprimer(int idZone) throws ExceptionAppli {
			managerTransaction.begin();
			try {
				managerSecurite.verifierAutorisationUtilisateurOuAdmin();
//				mapPersonnes.remove(idPersonne);
				daoZone.supprimer(idZone);
				managerTransaction.commit();
			} catch (RuntimeException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				throw new ExceptionAnomalie(e);
			}
		}

		@Override
		public DtoZone retrouver(int idZone) throws ExceptionAppli {
			try {
//				managerSecurite.verifierAutorisationUtilisateurOuAdmin();
//				return mapPersonnes.get(idPersonne);
				return mapper.map(daoZone.retrouver(idZone));
			} catch (RuntimeException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				throw new ExceptionAnomalie(e);
			}
		}

		@Override
		public List<DtoZone> listerTout() throws ExceptionAppli {
			try {
				managerSecurite.verifierAutorisationUtilisateurOuAdmin();
//				return trierParNom(new ArrayList<>(mapPersonnes.values()));
				List<DtoZone> liste = new ArrayList<>();
				 for( Zone zone : daoZone.listerTout() ) {
				 liste.add( mapper.map( zone ) );
				 }
				 return liste;

			} catch (RuntimeException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				throw new ExceptionAnomalie(e);
			}		// TODO Auto-generated method stub return null;
		}

		private void verifierValiditeDonnees(DtoZone dtoZone) throws ExceptionAppli {

			StringBuilder message = new StringBuilder();

//			if (dtoZone.getNom() == null || dtoZone.getNom().isEmpty()) {
//				message.append("\nLe nom est absent.");
//			} else if (dtoZone.getNom().length() > 25) {
//				message.append("\nLe nom est trop long.");
//			}

		if (message.length() > 0) {
			throw new ExceptionValidation(message.toString().substring(1));
		}
			}

		public IDaoZone getDaoZone() {
			return daoZone;
		}

		public void setDaoZone(IDaoZone daoZone) {
			this.daoZone = daoZone;
		}

		public IMapperDoDto getMapper() {
			return mapper;
		}

		public void setMapper(IMapperDoDto mapper) {
			this.mapper = mapper;
		}

		public IManagerTransaction getManagerTransaction() {
			return managerTransaction;
		}

		public void setManagerTransaction(IManagerTransaction managerTransaction) {
			this.managerTransaction = managerTransaction;
		}
}
