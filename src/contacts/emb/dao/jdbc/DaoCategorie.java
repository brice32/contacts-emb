package contacts.emb.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import contacts.emb.dao.IDaoCategorie;
import contacts.emb.dom.Annonceur;
import contacts.emb.dom.Categorie;

public class DaoCategorie implements IDaoCategorie {

	// Champs

	private DataSource dataSource;

	// Injecteurs
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// Action

	@Override
	public int inserer(Categorie categorie) {
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			// Insère le compte
			sql = "INSERT INTO Categorie ( idCategorie, libelle ) VALUES ( ?, ?)";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS  );
			stmt.setInt(	1, categorie.getIdCategorie() );
			stmt.setString(	2, categorie.getLibelle() );
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			categorie.setIdCategorie( rs.getInt(1) );

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (rs != null) rs.close();} catch (SQLException e) {}
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}

		// Retourne l'identifiant
		return categorie.getIdCategorie();
	}

	@Override
	public void modifier(Categorie categorie) {
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Modifie le compte
			sql = "UPDATE Categorie SET  libelle = ? WHERE idCategorie =  ?";
			stmt = cn.prepareStatement( sql );
			stmt.setString(	1, categorie.getLibelle() );
			stmt.setInt(	2, categorie.getIdCategorie() );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
	}

	@Override
	public void supprimer(int idCategorie) {
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Supprime le compte
			sql = "DELETE FROM Categorie WHERE idCategorie = ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setInt( 1, idCategorie );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}

	}

	@Override
	public Categorie retrouver(int idCategorie) {
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

	@Override
	public List<Categorie> listerTout() {
		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM Categorie ORDER BY libelle";
			stmt = cn.prepareStatement(sql);
			rs = stmt.executeQuery();

			List<Categorie> categorie = new ArrayList<>();
			while (rs.next()) {
				categorie.add(construireCategorie(rs));
			}
			return categorie;

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

		private Categorie construireCategorie(ResultSet rs) throws SQLException {
			Categorie categorie = new Categorie();
			categorie.setIdCategorie(rs.getInt("idCategorie"));
			categorie.setLibelle(rs.getString("libelle"));
			return categorie;
		}

}
