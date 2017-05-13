package contacts.emb.util.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import contacts.commun.dto.DtoAnnonce;
import contacts.commun.dto.DtoAnnonceur;
import contacts.commun.dto.DtoCategorie;
import contacts.commun.dto.DtoCompte;
import contacts.commun.dto.DtoMouvement;
import contacts.commun.dto.DtoPersonne;
import contacts.commun.dto.DtoRubrique;
import contacts.commun.dto.DtoTarif;
import contacts.commun.dto.DtoTelephone;
import contacts.commun.dto.DtoZone;
import contacts.emb.dom.Annonce;
import contacts.emb.dom.Annonceur;
import contacts.emb.dom.Categorie;
import contacts.emb.dom.Compte;
import contacts.emb.dom.Mouvement;
import contacts.emb.dom.Personne;
import contacts.emb.dom.Rubrique;
import contacts.emb.dom.Tarif;
import contacts.emb.dom.Telephone;
import contacts.emb.dom.Zone;


@Mapper
public interface IMapperDoDto {

	static final IMapperDoDto INSTANCE = Mappers.getMapper( IMapperDoDto.class );


	// Comptes

	Compte map( DtoCompte source );

	DtoCompte map( Compte source );

	Personne map(DtoPersonne source );

	DtoPersonne map( Personne source );

	Annonceur map( DtoAnnonceur source );

	DtoAnnonceur map( Annonceur source );

	Annonceur update( Annonceur source,@MappingTarget Annonceur cible);

	Categorie map (DtoCategorie source );

	DtoCategorie map ( Categorie source );

	Mouvement map ( DtoMouvement source );

	DtoMouvement map ( Mouvement source );

	Zone map(DtoZone source );

	DtoZone map( Zone source );

	Rubrique map(DtoRubrique source );

	DtoRubrique map( Rubrique source );

	@Mapping( target="personne", ignore=true )
	Telephone map( DtoTelephone source );


	DtoTelephone map( Telephone source );

	Tarif map( DtoTarif source );

	DtoTarif map( Tarif source );

	Annonce map( DtoAnnonce source );

	DtoAnnonce map( Annonce source );

	@AfterMapping
	default void ajouterRefPersonne( @MappingTarget Personne personne ) {
	 for ( Telephone telephone : personne.getTelephones() ) {
	 telephone.setPersonne( personne );
	 }
	}

}
