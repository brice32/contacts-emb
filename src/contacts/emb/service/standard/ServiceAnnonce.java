package contacts.emb.service.standard;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import contacts.commun.dto.DtoAnnonce;
import contacts.commun.dto.DtoAnnonceur;
import contacts.commun.dto.DtoRubrique;
import contacts.commun.dto.DtoZone;
import contacts.commun.service.IServiceAnnonce;
import contacts.commun.util.ExceptionAnomalie;
import contacts.commun.util.ExceptionAppli;
import contacts.emb.dao.IDaoAnnonce;
import contacts.emb.dao.IDaoAnnonceur;
import contacts.emb.dao.IManagerTransaction;
import contacts.emb.dom.Annonce;
import contacts.emb.dom.Annonceur;
import contacts.emb.util.mapper.IMapperDoDto;
import contacts.emb.util.securite.IManagerSecurite;

public class ServiceAnnonce implements IServiceAnnonce {

	// Logger
	private static final Logger logger = Logger.getLogger(ServiceAnnonceur.class.getName());

	// Champs

	private IManagerSecurite managerSecurite;

	private IDaoAnnonce daoAnnonce;
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

	public void setDaoAnnonce(IDaoAnnonce daoAnnonce) {
		this.daoAnnonce = daoAnnonce;
	}



	@Override
	public DtoAnnonce retrouver(int idAnnonce) throws ExceptionAppli {
		try{
			return mapper.map(daoAnnonce.retrouver(idAnnonce));
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new ExceptionAnomalie(e);
		}

	}

	@Override
	public List<DtoAnnonce> listerTout() throws ExceptionAppli {
		try {
			DtoAnnonce dtoAnnonce;
			List<DtoAnnonce> annonces = new ArrayList<>();
			for( Annonce annonce : daoAnnonce.listerTout() )
			{
				annonces.add( mapper.map( annonce ) );
			}
			return annonces;

		} catch (RuntimeException e) {
			logger.log( Level.SEVERE, e.getMessage(), e );
			throw new ExceptionAnomalie(e);
		}
	}
}
