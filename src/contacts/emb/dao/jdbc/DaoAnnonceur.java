package contacts.emb.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import contacts.emb.dao.IDaoAnnonceur;
import contacts.emb.dom.Annonceur;
import contacts.emb.dom.Personne;

public class DaoAnnonceur implements IDaoAnnonceur {

	// Champs

	private DataSource dataSource;

	// Injecteurs
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// Action

	@Override
	public List<Annonceur> listerTout() {
		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM Annonceur ORDER BY nom";
			stmt = cn.prepareStatement(sql);
			rs = stmt.executeQuery();

			List<Annonceur> annonceur = new ArrayList<>();
			while (rs.next()) {
				annonceur.add(construireAnnonceur(rs));
			}
			return annonceur;

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
	public int inserer(Annonceur annonceur) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			// Insère le compte
			sql = "INSERT INTO Annonceur ( idAnnonceur, nom, telephone, email, lieuNom, lieuAdresse, lieuCp, lieuVille, siteWeb ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS  );
			stmt.setInt(	1, annonceur.getId() );
			stmt.setString(	2, annonceur.getNom() );
			stmt.setString(	3, annonceur.getTelephone() );
			stmt.setString(	4, annonceur.getEmail() );
			stmt.setString(	5, annonceur.getLieuNom() );
			stmt.setString(	6, annonceur.getLieuAdresse() );
			stmt.setString(	7, annonceur.getLieuCp() );
			stmt.setString(	8, annonceur.getLieuVille() );
			stmt.setString(	9, annonceur.getSiteWeb() );
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			annonceur.setId( rs.getInt(1) );

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (rs != null) rs.close();} catch (SQLException e) {}
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}

		// Retourne l'identifiant
		return annonceur.getId();
	}

	@Override
	public void modifier(Annonceur annonceur) {
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Modifie le compte
			sql = "UPDATE Annonceur SET  nom = ?, telephone = ? , email = ?, lieuNom = ?, lieuAdresse = ?, lieuCp = ?, lieuVille = ?, siteWeb = ? WHERE idAnnonceur =  ?";
			stmt = cn.prepareStatement( sql );
			stmt.setString(	1, annonceur.getNom() );
			stmt.setString(	2, annonceur.getTelephone() );
			stmt.setString(	3, annonceur.getEmail() );
			stmt.setString(	4, annonceur.getLieuNom() );
			stmt.setString(	5, annonceur.getLieuAdresse() );
			stmt.setString(	6, annonceur.getLieuCp() );
			stmt.setString(	7, annonceur.getLieuVille() );
			stmt.setString(	8, annonceur.getSiteWeb() );
			stmt.setInt(	9, annonceur.getId() );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
	}

	@Override
	public void supprimer(int idAnnonceur) {
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Supprime le compte
			sql = "DELETE FROM Annonceur WHERE idAnnonceur = ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setInt( 1, idAnnonceur );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}

	}

	@Override
	public Annonceur retrouver(int idAnnonceur) {
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

	// M� thodes auxiliaires

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

}
