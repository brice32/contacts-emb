package contacts.emb.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;


import contacts.emb.dao.IDaoRubrique;

import contacts.emb.dom.Rubrique;

public class DaoRubrique implements IDaoRubrique{

private DataSource		dataSource;



	// Injecteurs

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}



	// Actions

	@Override
	public int inserer(Rubrique rubrique)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			// Insère le compte
			sql = "INSERT INTO Rubrique ( nom ) VALUES ( ? )";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS  );
			stmt.setString(	1, rubrique.getNom() );
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			rubrique.setId( rs.getInt(1) );

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (rs != null) rs.close();} catch (SQLException e) {}
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}



		// Retourne l'identifiant
		return rubrique.getId();
	}


	@Override
	public void modifier(Rubrique rubrique)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Modifie le compte
			sql = "UPDATE Rubrique SET nom = ? WHERE idRubrique = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setString(1, rubrique.getNom() );
			stmt.setInt( 2, rubrique.getId() );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}


	}


	@Override
	public void supprimer(int idRubrique)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;
		ResultSet 			rs 		= null;
		// Supprime les rôles


		try {
			cn = dataSource.getConnection();

			// Supprime le compte
			sql = "DELETE FROM Rubrique WHERE idRubrique = ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setInt( 1, idRubrique );
			stmt.executeUpdate();


		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
	}


	@Override
	public Rubrique retrouver (int idRubrique)  {

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


	@Override
	public List<Rubrique> listerTout()   {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM Rubrique ORDER BY nom";
			stmt = cn.prepareStatement(sql);
			rs = stmt.executeQuery();

			List<Rubrique> Rubrique = new ArrayList<>();
			while (rs.next()) {
				Rubrique.add( construireRubrique(rs) );
			}
			return Rubrique ;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (rs != null) rs.close();} catch (SQLException e) {}
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
	}





	// Méthodes auxiliaires

	private Rubrique construireRubrique( ResultSet rs ) throws SQLException {
		Rubrique Rubrique = new Rubrique();
		 Rubrique.setId( rs.getInt( "idRubrique" ) );
		 Rubrique.setNom( rs.getString( "nom" ) );

		 return Rubrique;
		}





}
