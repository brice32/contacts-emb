package contacts.emb.dao.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import contacts.commun.util.Roles;
import contacts.emb.dao.IDaoRole;
import contacts.emb.dom.Compte;

public class DaoRole implements IDaoRole {


	// Champs

	private DataSource		dataSource;


	// Injecteurs

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}


	// Actions

	@Override
	public void insererPourCompte( Compte compte )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO posseder (idCompte, idRole) VALUES (?,?)";
			stmt = cn.prepareStatement( sql );
			for( String role : compte.getRoles() ) {
				stmt.setInt(	1, compte.getId() );

				 if(role.equals(Roles.ADMINISTRATEUR)){
					 stmt.setInt(	2,  1);
				 }else if(role.equals(Roles.MODERATEUR)){
					 stmt.setInt(	2,  2);
				 }else if(role.equals(Roles.SECRETAIRE)){
					 stmt.setInt(	2,  3);
				 }else{
					 throw new IOException("DaoRole.java insererPourCompte role erreu");
				 }


				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try { if (rs != null) rs.close();} catch (SQLException e) {}
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
	}


	@Override
	public void modifierPourCompte( Compte compte )  {

		Connection			cn			= null;
		PreparedStatement	stmtDelete	= null;
		PreparedStatement	stmtInsert	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			sql = "DELETE FROM posseder WHERE idCompte = ?";
			stmtDelete = cn.prepareStatement( sql );
			stmtDelete.setInt(	1, compte.getId() );
			stmtDelete.executeUpdate();

			sql = "INSERT INTO posseder (idCompte, idRole) VALUES (?,?)";
			stmtInsert = cn.prepareStatement( sql );
			for( String role : compte.getRoles() ) {
				stmtInsert.setInt(	1, compte.getId() );
				 if(role.equals(Roles.ADMINISTRATEUR)){
					 stmtInsert.setInt(	2,  1);
				 }else if(role.equals(Roles.MODERATEUR)){
					 stmtInsert.setInt(	2,  2);
				 }else if(role.equals(Roles.SECRETAIRE)){
					 stmtInsert.setInt(	2,  3);
				 }else{
					 throw new IOException("DaoRole.java modifierPourCompte role erreu");
				 }
				stmtInsert.executeUpdate();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try { if (stmtDelete != null) stmtDelete.close();} catch (SQLException e) {}
			try { if (stmtInsert != null) stmtInsert.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
	}


	@Override
	public void supprimerPourCompte(int idCompte) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Supprime les rôles
			sql = "DELETE FROM posseder  WHERE idCompte = ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setInt( 1, idCompte );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
	}


	@Override
	public List<String> listerPourCompte(int idCompte) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

//			sql = "SELECT * FROM posseder WHERE IdCompte = ? ORDER BY idRole";
			sql = "SELECT * FROM (Compte LEFT JOIN posseder ON Compte.`idCompte`=posseder.`idCompte`)LEFT JOIN Role ON posseder.`idRole`=Role.`libelle` WHERE Compte.idCompte = ?";
			stmt = cn.prepareStatement(sql);
			stmt.setInt( 1, idCompte );
			rs = stmt.executeQuery();

			List<String> roles = new ArrayList<>();
			while (rs.next()) {
				roles.add( rs.getString("libelle") );
			}
			return roles;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (rs != null) rs.close();} catch (SQLException e) {}
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
	}

}
