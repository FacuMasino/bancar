package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dataAccess.IAccountTypesDao;
import domainModel.Account;
import domainModel.AccountType;


public class AccountTypesDao implements IAccountTypesDao {
	private Database db;

	public AccountTypesDao() {
		db = new Database();
	}

	@Override
	public boolean create(AccountType accountType) throws SQLException {
		int rows = 0;

		try {
			db.setPreparedStatement("INSERT INTO accounttypes(accountTypeName) VALUES(?)");
			db.getPreparedStatement().setString(1, accountType.getName());
			rows = db.getPreparedStatement().executeUpdate();

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return (rows > 0);
	}

	@Override
	public AccountType read(int accountTypeId) throws SQLException {
		ResultSet rsAccountType;
		// El negocio debe verificar que lo devuelto != null
		AccountType auxAccountType = null;

		try {
			db.setPreparedStatement("Select * from AccountTypes where AccountTypeId = ?");
			db.getPreparedStatement().setInt(1, accountTypeId);
			rsAccountType = db.getPreparedStatement().executeQuery();

			if (!rsAccountType.next())
				return auxAccountType; // no se encontró, devuelve null

			auxAccountType = getAccountType(rsAccountType);
		} catch (Exception ex) {

			ex.printStackTrace();
			throw ex;
		}

		return auxAccountType;
	}

	@Override
	public ArrayList<AccountType> list() throws SQLException {
		ArrayList<AccountType> auxAccountTypeList = new ArrayList<AccountType>();
		ResultSet rsAccountType;

		try {
			db.setPreparedStatement("SELECT * from AccountTypes");
			rsAccountType = db.getPreparedStatement().executeQuery();

			while (rsAccountType.next()) {
				/*if (!rsAccountType.next())
					return auxAccountTypeList; // devuelve null si esta vacia*/

				AccountType auxAccType = new AccountType();
				auxAccType = getAccountType(rsAccountType);

				auxAccountTypeList.add(auxAccType);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}

		return auxAccountTypeList;
	}

	@Override
	public int getId(AccountType accountType) throws SQLException {

		// Negocio deberia de verificar que NO sea 0
		ResultSet rsAccountType;
		int auxId = 0;
		try {
			db.setPreparedStatement("SELECT AccountTypeId from AccountTypes where AccountTypeName = ?");
			db.getPreparedStatement().setString(1, accountType.getName());
			rsAccountType = db.getPreparedStatement().executeQuery();
			if (!rsAccountType.next()) {
				return auxId;
			}
			auxId = rsAccountType.getInt(1);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}

		return auxId;
	}

	private AccountType getAccountType(ResultSet rsAccountType) throws SQLException {
		AccountType auxAccountType = new AccountType();

		try {
			auxAccountType.setId(rsAccountType.getInt("AccountTypeId"));
			auxAccountType.setName(rsAccountType.getString("AccountTypeName"));
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}

		return auxAccountType;
	}

	@Override
	public AccountType readByName(String accountTypeName) throws SQLException {
		
		ResultSet rsAccountType;
		AccountType auxAccountType = null;

		try {
			db.setPreparedStatement("Select * from AccountTypes where AccountTypeName = ?");
			db.getPreparedStatement().setString(1, accountTypeName);
			rsAccountType = db.getPreparedStatement().executeQuery();

			if (!rsAccountType.next())
				return auxAccountType; // no se encontró, devuelve null

			auxAccountType = getAccountType(rsAccountType);
		} catch (Exception ex) {

			ex.printStackTrace();
			throw ex;
		}

		return auxAccountType;
	}
}





