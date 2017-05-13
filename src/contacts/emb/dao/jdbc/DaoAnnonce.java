package contacts.emb.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import contacts.emb.dao.IDaoAnnonce;
import contacts.emb.dom.Annonce;
import contacts.emb.dom.Annonceur;
import contacts.emb.dom.Categorie;
import contacts.emb.dom.Rubrique;
import contacts.emb.dom.Zone;

public class DaoAnnonce implements IDaoAnnonce{

	// Champs

	private DataSource dataSource;

	// Injecteurs
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// Action

	@Override
	public int inserer(Annonce annonce){
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			// Insère le compte
			sql = "INSERT INTO Annonce ( idRubrique, idAnnonceur, idZone, idCategorie, titre, description, statut, dateDebut, dateFin, heureDebut, heureFin, lieuNom, lieuAdresse, lieuCp, lieuVille, animateurNom, animateurQualification, organisateurNom, organisateurSiteWeb, organisateurEmail, organisateurTelephone, idAnnonce ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?, ?, ?, ?, ?)";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS  );
			stmt.setInt(	1, annonce.getRubrique().getId() );
			stmt.setInt(	2, annonce.getAnnonceur().getId() );
			stmt.setInt(	3, annonce.getZone().getId() );
			stmt.setInt(	4, annonce.getCategorie().getIdCategorie() );
			stmt.setString( 5, annonce.getTitre());
			stmt.setString( 6, annonce.getDescription());
			stmt.setString( 7, annonce.getStatute().toString());
			stmt.setDate(   8, annonce.getDateDebut());
			stmt.setDate(   9, annonce.getDateFin());
			stmt.setTime(  10, annonce.getHeureDebut());
			stmt.setTime(  11, annonce.getHeureFin());
			stmt.setString(12, annonce.getLieuNom());
			stmt.setString(13, annonce.getLieuAdresse());
			stmt.setString(14, annonce.getLieuCp());
			stmt.setString(15, annonce.getLieuVille());
			stmt.setString(16, annonce.getOrganisateurNom());
			stmt.setString(17, annonce.getOrganisateurSiteWeb());
			stmt.setString(18, annonce.getOrganisateurEmail());
			stmt.setString(19, annonce.getOrganisateurTelephone());
			stmt.setString(20, annonce.getAnimateurNom());
			stmt.setString(21, annonce.getAnimateurQualification());
			stmt.setInt(   22, annonce.getIdAnnonce() );
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			annonce.setIdAnnonce( rs.getInt(22) );

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (rs != null) rs.close();} catch (SQLException e) {}
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}

		// Retourne l'identifiant
		return annonce.getIdAnnonce();
	}

	@Override
	public void modifier(Annonce annonce) {
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Modifie le compte
			sql = "UPDATE Annonce SET idRubrique = ?, idAnnonceur = ?, idZone = ?, idCategorie = ? , titre = ?, description = ?, statut = ?, dateDebut = ?, dateFin = ?, heureDebut = ?, heureFin = ?, lieuNom = ?, lieuAdresse = ?, lieuCp = ?, lieuVille = ?, animateurNom = ?, animateurQualification = ?, organisateurNom = ?, organisateurSiteWeb = ?, organisateurEmail = ?, organisateurTelephone = ? WHERE idAnnonce = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setInt(	1, annonce.getRubrique().getId() );
			stmt.setInt(	2, annonce.getAnnonceur().getId() );
			stmt.setInt(	3, annonce.getZone().getId() );
			stmt.setInt(	4, annonce.getCategorie().getIdCategorie() );
			stmt.setString( 5, annonce.getTitre());
			stmt.setString( 6, annonce.getDescription());
			stmt.setString( 7, annonce.getStatute().toString());
			stmt.setDate(   8, annonce.getDateDebut());
			stmt.setDate(   9, annonce.getDateFin());
			stmt.setTime(  10, annonce.getHeureDebut());
			stmt.setTime(  11, annonce.getHeureFin());
			stmt.setString(12, annonce.getLieuNom());
			stmt.setString(13, annonce.getLieuAdresse());
			stmt.setString(14, annonce.getLieuCp());
			stmt.setString(15, annonce.getLieuVille());
			stmt.setString(16, annonce.getOrganisateurNom());
			stmt.setString(17, annonce.getOrganisateurSiteWeb());
			stmt.setString(18, annonce.getOrganisateurEmail());
			stmt.setString(19, annonce.getOrganisateurTelephone());
			stmt.setString(20, annonce.getAnimateurNom());
			stmt.setString(21, annonce.getAnimateurQualification());
			stmt.setInt(   22, annonce.getIdAnnonce() );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
	}

	@Override
	public Annonce retrouver(int idAnnonce) {
		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM Annonce WHERE idAnnonce = ?";
			stmt = cn.prepareStatement(sql);
			stmt.setInt(1, idAnnonce);
			rs = stmt.executeQuery();

			if (rs.next()) {
				return construireAnnonce(rs);
			} else {
				return null;
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
			}
			try {
				if (cn != null)
					cn.close();
			} catch (SQLException e) {
			}
		}

	}

	@Override
	public List<Annonce> listerTout() {
		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM Annonce ORDER BY dateDebut";
			stmt = cn.prepareStatement(sql);
			rs = stmt.executeQuery();

			List<Annonce> annonce = new ArrayList<>();
			while (rs.next()) {
				annonce.add(construireAnnonce(rs));
			}
			return annonce;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
			}
			try {
				if (cn != null)
					cn.close();
			} catch (SQLException e) {
			}
		}
	}

	private Annonceur retrouverAnnonceur(int idAnnonceur) {
		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM Annonceur WHERE idAnnonceur = ?";
			stmt = cn.prepareStatement(sql);
			stmt.setInt(1, idAnnonceur);
			rs = stmt.executeQuery();

			if (rs.next()) {
				return construireAnnonceur(rs);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
			}
			try {
				if (cn != null)
					cn.close();
			} catch (SQLException e) {
			}
		}
	}

	public Zone retrouverZone(int idZone)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM Zone WHERE idZone = ?";
            stmt = cn.prepareStatement(sql);
            stmt.setInt(1, idZone);
            rs = stmt.executeQuery();

            if ( rs.next() ) {
                return construireZone(rs);
            } else {
            	return null;
            }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (rs != null) rs.close();} catch (SQLException e) {}
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
	}

	public Rubrique retrouverRubrique (int idRubrique)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM Rubrique WHERE idRubrique = ?";
            stmt = cn.prepareStatement(sql);
            stmt.setInt(1, idRubrique);
            rs = stmt.executeQuery();

            if ( rs.next() ) {
                return construireRubrique(rs);
            } else {
            	return null;
            }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (rs != null) rs.close();} catch (SQLException e) {}
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
	}


	public Categorie retrouverCategorie(int idCategorie) {
		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM Categorie WHERE idCategorie = ?";
			stmt = cn.prepareStatement(sql);
			stmt.setInt(1, idCategorie);
			rs = stmt.executeQuery();

			if (rs.next()) {
				return construireCategorie(rs);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
			}
			try {
				if (cn != null)
					cn.close();
			} catch (SQLException e) {
			}
		}
	}
	// M� thodes auxiliaires

	private Annonce construireAnnonce(ResultSet rs) throws SQLException {
		Annonce annonce = new Annonce();
		annonce.setIdAnnonce(rs.getInt("idAnnonce"));
		annonce.setTitre(rs.getString("titre"));
		annonce.setDescription(rs.getString("description"));
		annonce.setStatute(rs.getString("statut"));
		annonce.setDateDebut(rs.getDate("dateDebut"));
		annonce.setDateFin(rs.getDate("dateFin"));
		annonce.setHeureDebut(rs.getTime("heureDebut"));
		annonce.setHeureFin(rs.getTime("heureFin"));
		annonce.setLieuNom(rs.getString("lieuNom"));
		annonce.setLieuAdresse(rs.getString("lieuAdresse"));
		annonce.setLieuCp(rs.getString("lieuCp"));
		annonce.setLieuVille(rs.getString("lieuVille"));
		annonce.setAnimateurNom(rs.getString("animateurNom"));
		annonce.setAnimateurQualification(rs.getString("animateurQualification"));
		annonce.setOrganisateurNom(rs.getString("organisateurNom"));
		annonce.setOrganisateurSiteWeb(rs.getString("organisateurSiteWeb"));
		annonce.setOrganisateurEmail(rs.getString("organisateurEmail"));
		annonce.setOrganisateurTelephone(rs.getString("organisateurTelephone"));
		int idAnnocneur = rs.getInt("idAnnonceur");
		Annonceur annonceur = retrouverAnnonceur(idAnnocneur);
		annonce.setAnnonceur(annonceur);
		int idRubrique  = rs.getInt("idRubrique");
		Rubrique rubrique = retrouverRubrique(idRubrique);
		annonce.setRubrique(rubrique);
		int idZone 	= rs.getInt("idZone");
		Zone zone = retrouverZone(idZone);
		annonce.setZone(zone);
		int idCategorie = rs.getInt("idCategorie");
		Categorie categorie = retrouverCategorie(idCategorie);
		annonce.setCategorie(categorie);
		return annonce;
	}

	private Annonceur construireAnnonceur(ResultSet rs) throws SQLException {
		Annonceur annonceur = new Annonceur();
		annonceur.setId(rs.getInt("idAnnonceur"));
		annonceur.setNom(rs.getString("nom"));
		annonceur.setTelephone(rs.getString("telephone"));
		annonceur.setEmail(rs.getString("email"));
		annonceur.setLieuNom(rs.getString("lieuNom"));
		annonceur.setLieuAdresse(rs.getString("lieuAdresse"));
		annonceur.setLieuCp(rs.getString("lieuCp"));
		annonceur.setLieuVille(rs.getString("lieuVille"));
		annonceur.setSiteWeb(rs.getString("siteWeb"));
		return annonceur;
	}

	private Zone construireZone( ResultSet rs ) throws SQLException {
		 Zone zone = new Zone();
		 zone.setId( rs.getInt( "idZone" ) );
		 zone.setNom( rs.getString( "nom" ) );

		 return zone;
	}

	private Rubrique construireRubrique( ResultSet rs ) throws SQLException {
		Rubrique Rubrique = new Rubrique();
		 Rubrique.setId( rs.getInt( "idRubrique" ) );
		 Rubrique.setNom( rs.getString( "nom" ) );

		 return Rubrique;
	}

	private Categorie construireCategorie(ResultSet rs) throws SQLException {
		Categorie categorie = new Categorie();
		categorie.setIdCategorie(rs.getInt("idCategorie"));
		categorie.setLibelle(rs.getString("libelle"));
		return categorie;
	}
}
