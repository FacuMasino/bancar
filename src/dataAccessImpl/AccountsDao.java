package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dataAccess.IAccountsDao;
import domainModel.Account;
import domainModel.AccountType;
import domainModel.Client;

public class AccountsDao implements IAccountsDao {
	private Database db;
	private AccountTypesDao accountTypeDao;

	public AccountsDao() {
		db = new Database();
		accountTypeDao = new AccountTypesDao();
	}

	@Override
	public boolean create(Account account) throws SQLException {
		int rows = 0;

		try {
			AccountType auxAccountType = new AccountType(); // creo un objeto auxAccountType auxiliar.
			auxAccountType = accountTypeDao.readByName(account.getAccountType().getName());

			account.setAccountType(auxAccountType); // seteo el accountType completo

			db.setPreparedStatement("{CALL insert_account(?, ?, ?, ?)}");
			setParameters(account);
			rows = db.getPreparedStatement().executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}

		return (rows > 0);
	}

	@Override
	public Account read(int accountId) throws SQLException {
		ResultSet rsAccount;
		// El negocio debe verificar que lo devuelto != null
		Account auxAccount = null;

		try {
			db.setPreparedStatement("SELECT * FROM Accounts WHERE AccountId = ?");
			db.getPreparedStatement().setInt(1, accountId);
			rsAccount = db.getPreparedStatement().executeQuery();

			if (!rsAccount.next()) {
				return auxAccount;
			}

			// TODO: IMPORTANTE leer cliente con su ID usando ClientsBusiness, ahora está
			// vacío
			// Client auxClient = new Client();
			// TODO: IMPORTANTE leer tipo de cuenta con su ID usando AccountTypeBusiness,
			// ahora está vacía
			// AccountType auxAccType = new AccountType();

			auxAccount = getAccount(rsAccount);

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}

		return auxAccount;
	}

	@Override
	public boolean update(Account account) throws SQLException {
		int rows = 0;

		try {
			db.setPreparedStatement("{CALL update_account(?, ?, ?, ?)}");
			setUpdateParameters(account);
			rows = db.getPreparedStatement().executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}

		return (rows > 0);
	}

	@Override
	public boolean delete(int accountId) throws SQLException {
		int rows = 0;

		try {
			db.setPreparedStatement("UPDATE Accounts SET ActiveStatus = 0 WHERE AccountId = ?");
			db.getPreparedStatement().setInt(1, accountId);
			rows = db.getPreparedStatement().executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}

		return (rows > 0);
	}

	@Override
	public ArrayList<Account> list() throws SQLException {
		ResultSet rsAccounts;
		ArrayList<Account> accounts = new ArrayList<Account>();

		try {
			db.setPreparedStatement("SELECT * FROM Accounts WHERE ActiveStatus = 1");
			rsAccounts = db.getPreparedStatement().executeQuery();

			while (rsAccounts.next()) {
				// TODO: IMPORTANTE leer cliente con su ID usando ClientsBusiness, ahora está
				// vacío
				// Client auxClient = new Client();
				// TODO: IMPORTANTE leer tipo de cuenta con su ID usando AccountTypeBusiness,
				// ahora está vacía
				accounts.add(getAccount(rsAccounts));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}

		return accounts;
	}

	@Override
	public int getId(Account account) throws SQLException {
		return 0;
	}

	@Override
	public int getLastId() throws SQLException {
		int lastId = 0;

		try {
			db.setPreparedStatement("SELECT MAX(AccountId) from Accounts");
			ResultSet rs = db.getPreparedStatement().executeQuery();
			if (rs.next()) {
				lastId = rs.getInt(1);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}

		return lastId;
	}

	private void setParameters(Account account) throws SQLException {
		db.getPreparedStatement().setString(1, account.getCbu());
		db.getPreparedStatement().setBigDecimal(2, account.getBalance());
		db.getPreparedStatement().setInt(3, account.getAccountType().getId());
		db.getPreparedStatement().setInt(4, account.getClientId());
	}

	private void setUpdateParameters(Account account) throws SQLException {
		db.getPreparedStatement().setInt(1, account.getId());
		db.getPreparedStatement().setString(2, account.getCbu());
		db.getPreparedStatement().setBigDecimal(3, account.getBalance());
		db.getPreparedStatement().setInt(4, account.getAccountType().getId());
	}

	private Account getAccount(ResultSet rs) throws SQLException {
		Account auxAccount = new Account();

		try {
			auxAccount.setId(rs.getInt("AccountId"));
			auxAccount.setActiveStatus(rs.getBoolean("ActiveStatus"));
			auxAccount.setCbu(rs.getString("Cbu"));
			auxAccount.setCreationDate(rs.getDate("CreationDate"));
			auxAccount.setBalance(rs.getBigDecimal("Balance"));

			AccountType auxAccType = new AccountType();
			auxAccType = accountTypeDao.read(rs.getInt("AccountTypeId"));

			auxAccount.setClientId(rs.getInt("ClientId"));

			auxAccount.setAccountType(auxAccType);

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}

		return auxAccount;
	}

	@Override
	public ArrayList<Account> listByIdClient(int clientId) throws SQLException {
		// TODO fixea gonzo...
		ResultSet rsAccounts;
		// El negocio debe verificar que lo devuelto != null
		ArrayList<Account> auxAccountsList = new ArrayList<Account>();

		try {
			db.setPreparedStatement("SELECT * FROM Accounts WHERE ClientId = ? And ActiveStatus = 1;");
			db.getPreparedStatement().setInt(1, clientId);
			rsAccounts = db.getPreparedStatement().executeQuery();

			while (rsAccounts.next()) {
				auxAccountsList.add(getAccount(rsAccounts));
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}

		return auxAccountsList;
	}
}
