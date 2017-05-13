package contacts.emb.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import contacts.emb.dao.IDaoPersonne;
import contacts.emb.dao.IDaoTelephone;
import contacts.emb.dom.Compte;
import contacts.emb.dom.Personne;

public class DaoPersonne implements IDaoPersonne {

	// Champs

		private DataSource		dataSource;
		private IDaoTelephone		daoTelephone;


		// Injecteurs

		public void setDataSource(DataSource dataSource) {
			this.dataSource = dataSource;
		}

		public void setDaoTelephone(IDaoTelephone daoTelephone) {
			this.daoTelephone = daoTelephone;
		}

		// Actions

		@Override
		public int inserer(Personne personne)  {

			Connection			cn		= null;
			PreparedStatement	stmt	= null;
			ResultSet 			rs 		= null;
			String				sql;

			try {
				cn = dataSource.getConnection();

				// Insère le compte
				sql = "INSERT INTO personne ( Nom, Prenom ) VALUES ( ?, ? )";
				stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS  );
				stmt.setString(	1, personne.getNom() );
				stmt.setString(	2, personne.getPrenom() );
				stmt.executeUpdate();

				// Récupère l'identifiant généré par le SGBD
				rs = stmt.getGeneratedKeys();
				rs.next();
				personne.setId( rs.getInt(1) );

			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				try { if (rs != null) rs.close();} catch (SQLException e) {}
				try { if (stmt != null) stmt.close();} catch (SQLException e) {}
				try { if (cn != null) cn.close();} catch (SQLException e) {}
			}

			// Insère les rôles
			daoTelephone.insererPourPersonne(personne);

			// Retourne l'identifiant
			return personne.getId();
		}


		@Override
		public void modifier(Personne personne)  {

			Connection			cn		= null;
			PreparedStatement	stmt	= null;
			String 				sql;

			try {
				cn = dataSource.getConnection();

				// Modifie le compte
				sql = "UPDATE personne SET Nom = ?, Prenom = ? WHERE IdPersonne = ?";
				stmt = cn.prepareStatement( sql );
				stmt.setString(1, personne.getNom() );
				stmt.setString(2, personne.getPrenom() );
				stmt.setInt( 3, personne.getId() );
				stmt.executeUpdate();

			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				try { if (stmt != null) stmt.close();} catch (SQLException e) {}
				try { if (cn != null) cn.close();} catch (SQLException e) {}
			}

			// Modifie les rôles
			daoTelephone.modifierPourPersonne(personne);
		}


		@Override
		public void supprimer(int idPersonne)  {

			Connection			cn		= null;
			PreparedStatement	stmt	= null;
			String 				sql;
			ResultSet 			rs 		= null;
			// Supprime les rôles
			daoTelephone.supprimerPourPersonne( idPersonne );

			try {
				cn = dataSource.getConnection();

				// Supprime le compte
				sql = "DELETE FROM personne WHERE idPersonne = ? ";
				stmt = cn.prepareStatement(sql);
				stmt.setInt( 1, idPersonne );
				stmt.executeUpdate();


			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				try { if (stmt != null) stmt.close();} catch (SQLException e) {}
				try { if (cn != null) cn.close();} catch (SQLException e) {}
			}
		}


		@Override
		public Personne retrouver(int idPersonne)  {

			Connection			cn		= null;
			PreparedStatement	stmt	= null;
			ResultSet 			rs 		= null;
			String				sql;

			try {
				cn = dataSource.getConnection();

				sql = "SELECT * FROM personne WHERE IdPersonne = ?";
	            stmt = cn.prepareStatement(sql);
	            stmt.setInt(1, idPersonne);
	            rs = stmt.executeQuery();

	            if ( rs.next() ) {
	                return construirePersonne(rs);
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
		public List<Personne> listerTout()   {

			Connection			cn		= null;
			PreparedStatement	stmt	= null;
			ResultSet 			rs 		= null;
			String				sql;

			try {
				cn = dataSource.getConnection();

				sql = "SELECT * FROM personne ORDER BY Nom,Prenom";
				stmt = cn.prepareStatement(sql);
				rs = stmt.executeQuery();

				List<Personne> personne = new ArrayList<>();
				while (rs.next()) {
					personne.add( construirePersonne(rs) );
				}
				return personne;

			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				try { if (rs != null) rs.close();} catch (SQLException e) {}
				try { if (stmt != null) stmt.close();} catch (SQLException e) {}
				try { if (cn != null) cn.close();} catch (SQLException e) {}
			}
		}



//		@Override
//		public Compte validerAuthentification(String pseudo, String motDePasse)  {
//
//			Connection			cn		= null;
//			PreparedStatement	stmt	= null;
//			ResultSet 			rs 		= null;
//			String				sql;
//
//			try {
//				cn = dataSource.getConnection();
//
//				sql = "SELECT * FROM compte WHERE pseudo = ? AND MotDePasse = ?";
//				stmt = cn.prepareStatement(sql);
//				stmt.setString(1, pseudo);
//				stmt.setString(2, motDePasse);
//				rs = stmt.executeQuery();
//
//				if (rs.next()) {
//					return construireCompte(rs);
//				} else {
//					return null;
//				}
//			} catch (SQLException e) {
//				throw new RuntimeException(e);
//			} finally {
//				try { if (rs != null) rs.close();} catch (SQLException e) {}
//				try { if (stmt != null) stmt.close();} catch (SQLException e) {}
//				try { if (cn != null) cn.close();} catch (SQLException e) {}
//			}
//		}


//		@Override
//		public boolean verifierUnicitePseudo( String pseudo, int idCompte )   {
//
//			Connection			cn		= null;
//			PreparedStatement	stmt	= null;
//			ResultSet 			rs 		= null;
//			String				sql;
//
//			try {
//				cn = dataSource.getConnection();
//
//				sql = "SELECT COUNT(*) AS nbComptes"
//					+ " FROM compte WHERE pseudo = ? AND IdCompte <> ?";
//				stmt = cn.prepareStatement(sql);
//				stmt.setString(	1, pseudo );
//				stmt.setInt(	2, idCompte );
//				rs = stmt.executeQuery();
//
//				rs.next();
//				return rs.getInt("nbComptes") == 0;
//
//			} catch (SQLException e) {
//				throw new RuntimeException(e);
//			} finally {
//				try { if (rs != null) rs.close();} catch (SQLException e) {}
//				try { if (stmt != null) stmt.close();} catch (SQLException e) {}
//				try { if (cn != null) cn.close();} catch (SQLException e) {}
//			}
//		}

		// Méthodes auxiliaires

		private Personne construirePersonne( ResultSet rs ) throws SQLException {
			 Personne personne = new Personne();
			 personne.setId( rs.getInt( "IdPersonne" ) );
			 personne.setNom( rs.getString( "Nom" ) );
			 personne.setPrenom( rs.getString( "Prenom" ) );
			 personne.setTelephones( daoTelephone.listerPourPersonne( personne ) );
			 return personne;
			}


//	@Override
//	public int inserer(Personne personne) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void modifier(Personne personne) {
//		// TODO Auto-generated method stub
//
//	}

//	@Override
//	public void supprimer(int idPersonne) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public Personne retrouver(int idPersonne) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Personne> listerTout() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
