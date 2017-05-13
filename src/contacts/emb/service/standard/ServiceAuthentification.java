package contacts.emb.service.standard;

import java.util.logging.Level;
import java.util.logging.Logger;

import contacts.commun.dto.DtoCompte;
import contacts.commun.service.IServiceAuthentification;
import contacts.commun.util.ExceptionAnomalie;
import contacts.commun.util.ExceptionAppli;
import contacts.emb.dao.IDaoCompte;
import contacts.emb.util.mapper.IMapperDoDto;


public class ServiceAuthentification implements IServiceAuthentification {

	
	// Logger
	private static final Logger	logger = Logger.getLogger(ServiceAuthentification.class.getName());

	
	// Champs 

	private IMapperDoDto		mapper;
	private IDaoCompte			daoCompte;
	
	
	// Injecteurs
	
	public void setMapper( IMapperDoDto mapper ) {
		this.mapper = mapper;
	}

	public void setDaoCompte(IDaoCompte daoCompte) {
		this.daoCompte = daoCompte;
	}

	
	// Actions
	
	@Override
	public DtoCompte verifierAuthentification(String pseudo, String motDePasse) throws ExceptionAppli {
		
		DtoCompte	compteConnecte = null;

		try {
			compteConnecte = mapper.map( daoCompte.validerAuthentification(pseudo, motDePasse) );
			return compteConnecte;
		} catch ( RuntimeException e) {
			logger.log( Level.SEVERE, e.getMessage(), e );
			throw new ExceptionAnomalie(e);
		}
	}

}
