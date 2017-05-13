package contacts.emb.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import contacts.emb.dao.IDaoTarif;
import contacts.emb.dom.Tarif;

public class DaoTarif implements IDaoTarif  {
	
	// Champs

			private DataSource		dataSource;
			
   // injecteur
	/* (non-Javadoc)
	 * @see contacts.emb.dao.jdbc.IDaoTarif#setDataSource(javax.sql.DataSource)
	 */
	@Override
	public void setDataSource(DataSource dataSource) {
				this.dataSource = dataSource;
	}

	// Actions 
	/* (non-Javadoc)
	 * @see contacts.emb.dao.jdbc.IDaoTarif#creationTarif(contacts.emb.dom.Tarif)
	 */
	@Override
	public int creationTarif(Tarif tarif){
		
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;
		
		try {
			cn = dataSource.getConnection();
        // inserer un tarif
		sql=" INSERT INTO tarif tarif ( date,tarifConference,tarifRelief,tarifStage) VALUES (?,?,?,?)";
		stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS  );
		stmt.setString(1, tarif.getDate());
		stmt.setFloat(2, tarif.getTarifConference());
		stmt.setFloat(3, tarif.getTarifRelief());
		stmt.setFloat(4, tarif.getTarifStage());	
		stmt.executeUpdate();
		
		// recuperation de l'identifiant generer âr la bd
		rs = stmt.getGeneratedKeys();
		rs.next();
		//starif.setDate(rs.getDate(sql));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (rs != null) rs.close();} catch (SQLException e) {}
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
		return tarif.getId();
		
		
	}
	
	// modification
	
	/* (non-Javadoc)
	 * @see contacts.emb.dao.jdbc.IDaoTarif#modifierTarif(contacts.emb.dom.Tarif)
	 */
	@Override
	public void modifierTarif(Tarif tarif){
	
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;
		
		try {
			cn = dataSource.getConnection();
			sql=" UPDATE tarif SET(date,tarifConference,tarifRelief,tarifStage) WHERE idTarif=? ";
			stmt = cn.prepareStatement( sql );
			stmt.setString(1, tarif.getDate());
			stmt.setFloat(2, tarif.getTarifConference());
			stmt.setFloat(3, tarif.getTarifRelief());
			stmt.setFloat(4, tarif.getTarifStage());
			stmt.setInt(5,tarif.getId());
			
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}

	}
	
	// suppression tarif
	
	/* (non-Javadoc)
	 * @see contacts.emb.dao.jdbc.IDaoTarif#supprimer(int)
	 */
	@Override
	public void supprimer(int idTarif)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;
		ResultSet 			rs 		= null;
		
		try {
			cn = dataSource.getConnection();

			// Supprime le tarif selectionné
			sql = "DELETE FROM tarif WHERE idtarif = ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setInt( 1, idTarif );
			stmt.executeUpdate();


		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try { if (stmt != null) stmt.close();} catch (SQLException e) {}
			try { if (cn != null) cn.close();} catch (SQLException e) {}
		}
	}

	
	
	
	
	
	
}
