package contacts.emb.service.standard;

import java.util.logging.Level;
import java.util.logging.Logger;

import contacts.commun.dto.DtoTarif;
import contacts.commun.util.ExceptionAnomalie;
import contacts.commun.util.ExceptionAppli;
import contacts.emb.dao.IDaoTarif;
import contacts.emb.dao.IManagerTransaction;
import contacts.emb.util.mapper.IMapperDoDto;
import contacts.emb.util.securite.IManagerSecurite;

public class ServiceTarif   {
	
	// Logger
		private static final Logger logger = Logger.getLogger(ServiceTarif.class.getName());

		// Champs

		private IManagerSecurite managerSecurite;
		private IDaoTarif daoTarif;
		private IMapperDoDto mapper;
		private IManagerTransaction managerTransaction;

		
		
		public void setManagerSecurite(IManagerSecurite managerSecurite) {
			this.managerSecurite = managerSecurite;
		}
  
   // faire la sécurité plutard car nous be savons pas qui peut avoir acces aux tarifs.
	  
		
		
		public int inserer(DtoTarif dtotarif) throws ExceptionAppli{
			try{
			managerTransaction.begin();
			try {
				int id = daoTarif.creationTarif(mapper.map(dtotarif));
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
		
	
    public void modifier(DtoTarif dtotarif) throws ExceptionAppli {
			try{
				managerTransaction.begin();
				try {
				daoTarif.modifierTarif( mapper.map( dtotarif ) );
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
		
		public void supprimer(int idTarif) throws ExceptionAppli {
			managerTransaction.begin();
			try {	
				daoTarif.supprimer(idTarif);
				managerTransaction.commit();
			} catch (RuntimeException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				throw new ExceptionAnomalie(e);
			}
		}

		
		
		
		public IDaoTarif getDaoTarif() {
			return daoTarif;
		}

		
		
		public void setDaoTarif(IDaoTarif daoTarif) {
			this.daoTarif = daoTarif;
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

		
		
		
		public IManagerSecurite getManagerSecurite() {
			return managerSecurite;
		}

}
		
		
		
		
		
		

