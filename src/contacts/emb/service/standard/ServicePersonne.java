package contacts.emb.service.standard;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import contacts.commun.dto.DtoPersonne;
import contacts.commun.dto.DtoTelephone;
import contacts.commun.service.IServicePersonne;
import contacts.commun.util.ExceptionAnomalie;
import contacts.commun.util.ExceptionAppli;
import contacts.commun.util.ExceptionValidation;
import contacts.emb.dao.IDaoPersonne;
import contacts.emb.dao.IManagerTransaction;
import contacts.emb.dom.Personne;
import contacts.emb.util.mapper.IMapperDoDto;
import contacts.emb.util.securite.IManagerSecurite;

public class ServicePersonne implements IServicePersonne {

	// Logger
	private static final Logger logger = Logger.getLogger(ServicePersonne.class.getName());

	// Champs

	// private Donnees donnees;
	// private Map<Integer, DtoPersonne> mapPersonnes;

	private IManagerSecurite managerSecurite;

	private IDaoPersonne daoPersonne;
	private IMapperDoDto mapper;
	private IManagerTransaction managerTransaction;

	// Injecteurs

	// public void setDonnees( Donnees donnees ) {
	// this.donnees = donnees;
	// mapPersonnes = donnees.getMapPersonnes();
	// }

	public void setManagerSecurite(IManagerSecurite managerSecurite) {
		this.managerSecurite = managerSecurite;
	}

	// Actions

	@Override
	public int inserer(DtoPersonne dtoPersonne) throws ExceptionAppli {
		try {
			managerSecurite.verifierAutorisationUtilisateurOuAdmin();
			verifierValiditeDonnees(dtoPersonne);
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
				int id = daoPersonne.inserer(mapper.map(dtoPersonne));
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
	public void modifier(DtoPersonne dtoPersonne) throws ExceptionAppli {
		try {
			managerSecurite.verifierAutorisationUtilisateurOuAdmin();
			verifierValiditeDonnees(dtoPersonne);
//			affecterIdTelephones(dtoPersonne);
//			mapPersonnes.replace(dtoPersonne.getId(), dtoPersonne);
			managerTransaction.begin();
			try {
			daoPersonne.modifier( mapper.map( dtoPersonne ) );
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
	public void supprimer(int idPersonne) throws ExceptionAppli {
		managerTransaction.begin();
		try {
			managerSecurite.verifierAutorisationUtilisateurOuAdmin();
//			mapPersonnes.remove(idPersonne);
			daoPersonne.supprimer(idPersonne);
			managerTransaction.commit();
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new ExceptionAnomalie(e);
		}
	}

	@Override
	public DtoPersonne retrouver(int idPersonne) throws ExceptionAppli {
		try {
//			managerSecurite.verifierAutorisationUtilisateurOuAdmin();
//			return mapPersonnes.get(idPersonne);
			return mapper.map(daoPersonne.retrouver(idPersonne));
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new ExceptionAnomalie(e);
		}
	}

	@Override
	public List<DtoPersonne> listerTout() throws ExceptionAppli {
		try {
			managerSecurite.verifierAutorisationUtilisateurOuAdmin();
//			return trierParNom(new ArrayList<>(mapPersonnes.values()));
			List<DtoPersonne> liste = new ArrayList<>();
			 for( Personne personne : daoPersonne.listerTout() ) {
			 liste.add( mapper.map( personne ) );
			 }
			 return liste;

		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new ExceptionAnomalie(e);
		}
	}

	// Méthodes auxiliaires

	private void verifierValiditeDonnees(DtoPersonne dtoPersonne) throws ExceptionAppli {

		StringBuilder message = new StringBuilder();

		if (dtoPersonne.getNom() == null || dtoPersonne.getNom().isEmpty()) {
			message.append("\nLe nom est absent.");
		} else if (dtoPersonne.getNom().length() > 25) {
			message.append("\nLe nom est trop long.");
		}

		if (dtoPersonne.getPrenom() == null || dtoPersonne.getPrenom().isEmpty()) {
			message.append("\nLe prenom est absent.");
		} else if (dtoPersonne.getPrenom().length() > 25) {
			message.append("\nLe prenom est trop long.");
		}

		for (DtoTelephone telephoe : dtoPersonne.getTelephones()) {
			if (telephoe.getLibelle() == null || telephoe.getLibelle().isEmpty()) {
				message.append("\nLlibelle absent pour le telephone : " + telephoe.getNumero());
			} else if (telephoe.getLibelle().length() > 25) {
				message.append("\nLe libellé du telephone est trop lon : " + telephoe.getLibelle());
			}

			if (telephoe.getNumero() == null || telephoe.getNumero().isEmpty()) {
				message.append("\nNumero absent pour le telephone : " + telephoe.getLibelle());
			} else if (telephoe.getNumero().length() > 25) {
				message.append("\nLe numero du telephone est trop lon : " + telephoe.getNumero());
			}
		}

		if (message.length() > 0) {
			throw new ExceptionValidation(message.toString().substring(1));
		}
	}

//	private void affecterIdTelephones(DtoPersonne dtoPersonne) {
//		for (DtoTelephone t : dtoPersonne.getTelephones()) {
//			if (t.getId() == 0) {
//				t.setId(donnees.getProchainIdTelephone());
//			}
//		}
//	}
//
//	private List<DtoPersonne> trierParNom(List<DtoPersonne> liste) {
//		Collections.sort(liste, (Comparator<DtoPersonne>) (item1, item2) -> {
//			int resultat = item1.getNom().toUpperCase().compareTo(item2.getNom().toUpperCase());
//			if (resultat != 0) {
//				return resultat;
//			} else {
//				return (item1.getPrenom().toUpperCase().compareTo(item2.getPrenom().toUpperCase()));
//			}
//		});
//		return liste;
//	}

	public IDaoPersonne getDaoPersonne() {
		return daoPersonne;
	}

	public void setDaoPersonne(IDaoPersonne daoPersonne) {
		this.daoPersonne = daoPersonne;
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
