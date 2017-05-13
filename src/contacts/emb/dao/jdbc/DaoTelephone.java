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
//import contacts.emb.dao.mock.DaoPersonne;
import contacts.emb.dom.Personne;
import contacts.emb.dom.Telephone;

public class DaoTelephone implements IDaoTelephone {

	// Champs

		private DataSource		dataSource;

	// Injecteurs

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void insererPourPersonne(Personne personne) {
		// TODO Auto-generated method stub

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO telephone (IdPersonne, Libelle , Numero) VALUES (?,?,?)";
//			stmt = cn.prepareStatement( sql );
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS  );
			for( Telephone telephone   : personne.getTelephones() ) {
				stmt.setInt(	1, personne.getId() );
				stmt.setString(	2, telephone.getLibelle() );
				stmt.setString(	3, telephone.getNumero() );
				stmt.executeUpdate();
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
	public void modifierPourPersonne(Personne personne) {
		// TODO Auto-generated method stub
		Connection			cn			= null;
		PreparedStatement	stmtDelete	= null;
		PreparedStatement	stmtInsert	= null;
		PreparedStatement   stmtUpdate  = null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			for(Telephone telephones : listerPourPersonne(personne)){
//				for(Telephone telephoneBase : )
//				{
//
//				}
				if(personne.getTelephones().contains(telephones)){
					//mise au jour ou ajouter
				}
					else{
////					for(Telephone telephone : personne.getTelephones()){
////						if(telephones.getId()==telephone.getId()){
////
////						}
////					}
//
						//supprimer

						sql = "DELETE FROM telephone WHERE IdTelephone = ? ";
						stmtDelete = cn.prepareStatement(sql);
						stmtDelete.setInt( 1, telephones.getId() );
						stmtDelete.executeUpdate();
				}
			}

			for(Telephone telephones : personne.getTelephones()){
				if(telephones.getId()==0){
					//ajouter
					sql = "INSERT INTO telephone (IdPersonne, Libelle , Numero) VALUES (?,?,?)";
					stmtInsert = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS  );
						stmtInsert.setInt(	1, personne.getId() );
						stmtInsert.setString(	2, telephones.getLibelle() );
						stmtInsert.setString(	3, telephones.getNumero() );
						stmtInsert.executeUpdate();
				}else{
					for(Telephone telephoneDate : listerPourPersonne(personne)){
						if(telephones.equals(telephoneDate)){
							if(telephones.getLibelle().equals(telephoneDate.getLibelle())&&telephones.getNumero().equals(telephoneDate.getNumero())){

							}else{
								sql = "UPDATE telephone SET Libelle = ? , Numero = ? WHERE IdTelephone = ?";
								stmtUpdate = cn.prepareStatement( sql );
								stmtUpdate.setString(1, telephones.getLibelle() );
								stmtUpdate.setString(2, telephones.getNumero() );
								stmtUpdate.setInt(   3, telephones.getId() );
								stmtUpdate.executeUpdate();
							}
						}
					}
//					sql = "UPDATE telephone SET Libelle = ?, Numero = ? WHERE IdTelephone = ?";
//					stmtUpdate = cn.prepareStatement( sql );
//					stmtUpdate.setString(1, telephones.getLibelle() );
//					stmtUpdate.setString(2, telephones.getNumero() );
//					stmtUpdate.setInt( 3, telephones.getId() );
//					stmtUpdate.executeUpdate();

				}
			}


//			sql = "DELETE FROM telephone WHERE IdPersonne = ?";
//			stmtDelete = cn.prepareStatement( sql );
//			stmtDelete.setInt(	1, personne.getId() );
//			stmtDelete.executeUpdate();
//
//			sql = "INSERT INTO telephone (IdPersonne, Libelle , Numero) VALUES (?,?,?)";
//			stmtInsert = cn.prepareStatement( sql );
//			for( Telephone telephone   : personne.getTelephones() ) {
//				stmtInsert.setInt(	1, personne.getId() );
//				stmtInsert.setString(	2, telephone.getLibelle() );
//				stmtInsert.setString(	3, telephone.getNumero() );
//				stmtInsert.executeUpdate();
//			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (stmtDelete != null) stmtDelete.close();} catch (SQLException e) {}
			try { if (stmtInsert != null) stmtInsert.close();} catch (SQLException e) {}
			try { if (stmtUpdate != null) stmtUpdate.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
	}

	@Override
	public void supprimerPourPersonne(int idPersonne) {
		// TODO Auto-generated method stub
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Supprime les rôles
			sql = "DELETE FROM telephone WHERE IdPersonne = ? ";
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
	public List<Telephone> listerPourPersonne(Personne personne) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM telephone WHERE IdPersonne = ? ORDER BY IdTelephone";
			stmt = cn.prepareStatement(sql);
			stmt.setInt( 1, personne.getId() );
			rs = stmt.executeQuery();

			List<Telephone> telephone = new ArrayList<>();
			while (rs.next()) {
				 Telephone tl = new Telephone();
				 tl.setId( rs.getInt( "IdTelephone" ) );
				 tl.setLibelle(rs.getString("Libelle"));
				 tl.setNumero(rs.getString("Numero"));
				 tl.setPersonne(personne);
				telephone.add(tl);
			}
			return telephone;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (rs != null) rs.close();} catch (SQLException e) {}
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
	}

//	private Telephone construireTelephone( ResultSet rs ) throws SQLException {
//		 Telephone telephone = new Telephone();
//		 telephone.setId( rs.getInt( "IdTelephone" ) );
//		 telephone.setLibelle(rs.getString("Libelle"));
//		 telephone.setNumero(rs.getString("Numero"));
//		 telephone.setPersonne(new DaoPersonne().retrouver(rs.getInt("IdPersonne")));
////		 telephone.setPersonne(rs.getInt("IdPersonne"));
////		 telephone.setNom( rs.getString( "Nom" ) );
////		 personne.setPrenom( rs.getString( "Prenom" ) );
////		 personne.setTelephones( daoTelephone.listerPourPersonne( personne ) );
//		 return telephone;
//		}


}

