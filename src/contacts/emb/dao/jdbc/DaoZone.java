package contacts.emb.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;


import contacts.emb.dao.IDaoZone;

import contacts.emb.dom.Zone;

public  class DaoZone implements IDaoZone{
	// Champs

			private DataSource		dataSource;



			// Injecteurs

			public void setDataSource(DataSource dataSource) {
				this.dataSource = dataSource;
			}



			// Actions

			@Override
			public int inserer(Zone zone)  {

				Connection			cn		= null;
				PreparedStatement	stmt	= null;
				ResultSet 			rs 		= null;
				String				sql;

				try {
					cn = dataSource.getConnection();

					// Insère le compte
					sql = "INSERT INTO Zone ( nom ) VALUES ( ? )";
					stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS  );
					stmt.setString(	1, zone.getNom() );
					stmt.executeUpdate();

					// Récupère l'identifiant généré par le SGBD
					rs = stmt.getGeneratedKeys();
					rs.next();
					zone.setId( rs.getInt(1) );

				} catch (SQLException e) {
					throw new RuntimeException(e);
				} finally {
					try { if (rs != null) rs.close();} catch (SQLException e) {}
					try { if (stmt != null) stmt.close();} catch (SQLException e) {}
					try { if (cn != null) cn.close();} catch (SQLException e) {}
				}



				// Retourne l'identifiant
				return zone.getId();
			}


			@Override
			public void modifier(Zone zone)  {

				Connection			cn		= null;
				PreparedStatement	stmt	= null;
				String 				sql;

				try {
					cn = dataSource.getConnection();

					// Modifie le compte
					sql = "UPDATE Zone SET nom = ? WHERE idZone = ?";
					stmt = cn.prepareStatement( sql );
					stmt.setString(1, zone.getNom() );
					stmt.setInt( 2, zone.getId() );
					stmt.executeUpdate();

				} catch (SQLException e) {
					throw new RuntimeException(e);
				} finally {
					try { if (stmt != null) stmt.close();} catch (SQLException e) {}
					try { if (cn != null) cn.close();} catch (SQLException e) {}
				}


			}


			@Override
			public void supprimer(int idZone)  {

				Connection			cn		= null;
				PreparedStatement	stmt	= null;
				String 				sql;
				ResultSet 			rs 		= null;
				// Supprime les rôles


				try {
					cn = dataSource.getConnection();

					// Supprime le compte
					sql = "DELETE FROM Zone WHERE idZone = ? ";
					stmt = cn.prepareStatement(sql);
					stmt.setInt( 1, idZone );
					stmt.executeUpdate();


				} catch (SQLException e) {
					throw new RuntimeException(e);
				} finally {
					try { if (stmt != null) stmt.close();} catch (SQLException e) {}
					try { if (cn != null) cn.close();} catch (SQLException e) {}
				}
			}


			@Override
			public Zone retrouver(int idZone)  {

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


			@Override
			public List<Zone> listerTout()   {

				Connection			cn		= null;
				PreparedStatement	stmt	= null;
				ResultSet 			rs 		= null;
				String				sql;

				try {
					cn = dataSource.getConnection();

					sql = "SELECT * FROM Zone ORDER BY nom";
					stmt = cn.prepareStatement(sql);
					rs = stmt.executeQuery();

					List<Zone> Zone = new ArrayList<>();
					while (rs.next()) {
						Zone.add( construireZone(rs) );
					}
					return Zone;

				} catch (SQLException e) {
					throw new RuntimeException(e);
				} finally {
					try { if (rs != null) rs.close();} catch (SQLException e) {}
					try { if (stmt != null) stmt.close();} catch (SQLException e) {}
					try { if (cn != null) cn.close();} catch (SQLException e) {}
				}
			}





			// Méthodes auxiliaires

			private Zone construireZone( ResultSet rs ) throws SQLException {
				 Zone zone = new Zone();
				 zone.setId( rs.getInt( "idZone" ) );
				 zone.setNom( rs.getString( "nom" ) );

				 return zone;
				}


}






