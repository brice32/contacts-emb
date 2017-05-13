package contacts.emb.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import contacts.emb.dao.IManagerTransaction;


public class ManagerTransaction implements IManagerTransaction {
	
	
	// Logger
	private static final Logger logger = Logger.getLogger(ManagerTransaction.class.getName());
	
	
	// Champs
	
	private DataSource	dataSource;  
	
	
	// Injecteurs
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	// Constructeur
	
//	public ManagerTransaction( DataSource dataSource  ) {
//		this.dataSource = dataSource;
//	}
	
	
	
	// Actions

	@Override
	public void begin() {
		try {
			dataSource.getConnection().setAutoCommit(false);
			logger.finer("Transaction BEGIN");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void commit() {
		try {
			Connection conneciton = dataSource.getConnection();
			conneciton.commit();
			conneciton.setAutoCommit(false);
			logger.finer("Transaction COMMIT");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void rollback() {
		try {
			Connection conneciton = dataSource.getConnection();
			conneciton.rollback();
			conneciton.setAutoCommit(false);
			logger.finer("Transaction ROLLBACK");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
