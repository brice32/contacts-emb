package contacts.emb.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import contacts.emb.dao.IDaoMouvement;
import contacts.emb.dom.Annonceur;
import contacts.emb.dom.Mouvement;

public class DaoMouvement implements IDaoMouvement {

	// Champs

	private DataSource dataSource;

	// Injecteurs
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public int inserer(Mouvement mouvement) {
		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatHeure = new SimpleDateFormat("HH:mm:ss");
		try {
			cn = dataSource.getConnection();

			// Insère le compte
			sql = "INSERT INTO Mouvement ( idMouvement, idAnnonceur, montant, date, heure, solde, libelle, description ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )";
			stmt = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, mouvement.getIdMouvement());
			stmt.setInt(2, mouvement.getAnnonceur().getId());
			stmt.setFloat(3, mouvement.getMontant());
			stmt.setString(4, formatDate.format(new Date()));
			stmt.setString(5, formatHeure.format(new Date()));
			stmt.setFloat(6, retrouverSoldeParIdAnnnonceur(mouvement.getAnnonceur().getId()) + mouvement.getMontant());
			stmt.setString(7, mouvement.getLibelle());
			stmt.setString(8, mouvement.getDescription());
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			mouvement.setIdMouvement(rs.getInt(1));

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

		// Retourne l'identifiant
		return mouvement.getIdMouvement();
	}

	@Override
	public void modifier(Mouvement mouvement) {
		// TODO Auto-generated method stub

	}

	@Override
	public void supprimer(int idMouvement) {
		// TODO Auto-generated method stub

	}

	@Override
	public Annonceur retrouver(int idMouvement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Mouvement> listerTout() {
		// TODO Auto-generated method stub
		return null;
	}

	public float retrouverSoldeParIdAnnnonceur(int idAnnonceur) {
		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM Mouvement WHERE idAnnonceur = ? ORDER BY date DESC,heure DESC";
			stmt = cn.prepareStatement(sql);
			stmt.setInt(1, idAnnonceur);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getFloat("solde");
			} else {
				return 0;
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
	public Mouvement retouverDerniereMouvement(int idAnnonceur) {
		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM Mouvement WHERE idAnnonceur = ? ORDER BY date DESC,heure DESC";
			stmt = cn.prepareStatement(sql);
			stmt.setInt(1, idAnnonceur);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return construireMouvement(rs);
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
	public List<Mouvement> retouverListe(int idAnnonceur) {
		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM Mouvement WHERE idAnnonceur = ? ORDER BY date DESC,heure DESC";
			stmt = cn.prepareStatement(sql);
			stmt.setInt(1, idAnnonceur);
			rs = stmt.executeQuery();
			List<Mouvement> mouvements = new ArrayList<>();
			Mouvement mouvement = new Mouvement();
			Annonceur annonceur = retrouverAnnonceur(idAnnonceur);
			while (rs.next()) {
				mouvement=construireMouvement(rs);
				mouvement.setAnnonceur(annonceur);
				mouvements.add(mouvement);
			}
			return mouvements;
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

	private Mouvement construireMouvement(ResultSet rs) throws SQLException {
		Mouvement mouvement = new Mouvement();
		mouvement.setIdMouvement(rs.getInt("idMouvement"));
		// mouvement.setAnnonceur(rs.getInt("idAnnonceur"));
		mouvement.setMontant(rs.getFloat("montant"));
		mouvement.setDate(rs.getString("date"));
		mouvement.setHeure(rs.getString("heure"));
		mouvement.setSolde(rs.getFloat("solde"));
		mouvement.setLibelle(rs.getString("libelle"));
		mouvement.setDescription(rs.getString("description"));
		return mouvement;
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


}
