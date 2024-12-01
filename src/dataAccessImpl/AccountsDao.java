package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dataAccess.IAccountsDao;
import dataAccess.IClientsDao;
import domainModel.Account;
import domainModel.AccountType;

public class AccountsDao implements IAccountsDao
{
	private Database db;
	private AccountTypesDao accountTypesDao;
	private IClientsDao clientsDao;
	
	public AccountsDao()
	{
		db = new Database();
		accountTypesDao = new AccountTypesDao();
		clientsDao = new ClientsDao();
	}

	@Override
	public int create(Account account) throws SQLException
	{
		try
		{
			AccountType accountType;
			accountType = accountTypesDao.readByName(account.getAccountType().getName());
			account.setAccountType(accountType);

			db.setCallableStatement("{CALL insert_account(?, ?, ?, ?, ?)}");
	  		setParameters(account, false);
	        db.getCallableStatement().executeUpdate();
	        return db.getCallableStatement().getInt(1);
		}
		catch (SQLException ex) 
		{
			ex.printStackTrace();
			throw ex;
		}
	}

	@Override
	public Account read(int accountId) throws SQLException
	{
		Account account = new Account();
		ResultSet rs;

		try
		{
			db.setPreparedStatement("SELECT * FROM Accounts WHERE AccountId = ?");
			db.getPreparedStatement().setInt(1, accountId);
			rs = db.getPreparedStatement().executeQuery();

			if (!rs.next())
			{
				return null;
			}

			assignResultSet(account, rs);

		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}

		return account;
	}

	@Override
	public boolean update(Account account) throws SQLException
	{
		int rows = 0;

		try
		{
			db.setCallableStatement("{CALL update_account(?, ?, ?, ?)}");
			setParameters(account, true);
			rows = db.getCallableStatement().executeUpdate();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}

		return (rows > 0);
	}

	@Override
	public boolean delete(int accountId) throws SQLException
	{
		int rows = 0;

		try
		{
			db.setPreparedStatement("UPDATE Accounts SET ActiveStatus = 0 WHERE AccountId = ?");
			db.getPreparedStatement().setInt(1, accountId);
			rows = db.getPreparedStatement().executeUpdate();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}

		return (rows > 0);
	}

	@Override
	public ArrayList<Account> list() throws SQLException
	{
		ResultSet rs;
		ArrayList<Account> accounts = new ArrayList<Account>();

		try
		{
			db.setPreparedStatement("SELECT * FROM Accounts WHERE ActiveStatus = 1");
			rs = db.getPreparedStatement().executeQuery();

			while (rs.next())
			{
				Account account = new Account();
				assignResultSet(account, rs);
				accounts.add(account);
			}
		}
		catch (SQLException ex) 
		{
			ex.printStackTrace();
			throw ex;
		}

		return accounts;
	}
	
	@Override
	public ArrayList<Account> list(int clientId) throws SQLException
	{
		ArrayList<Account> accounts = new ArrayList<Account>();
		ResultSet rs;

		try
		{
			db.setPreparedStatement("SELECT * FROM Accounts WHERE ClientId = ? AND ActiveStatus = 1");
			db.getPreparedStatement().setInt(1, clientId);
			rs = db.getPreparedStatement().executeQuery();

			while (rs.next())
			{
				Account account = new Account();
				assignResultSet(account, rs);
				accounts.add(account);
			}
		}
		catch (SQLException ex) 
		{
			ex.printStackTrace();
			throw ex;
		}

		return accounts;
	}
	
	public int findId(String cbu) throws SQLException
	{
		ResultSet rs;
		
		try
		{
			db.setPreparedStatement("SELECT AccountId FROM Accounts WHERE Cbu = ?");
			db.getPreparedStatement().setString(1, cbu);
			rs = db.getPreparedStatement().executeQuery();
			
			if(!rs.next())
			{
				return 0;
			}
			
			return rs.getInt("AccountId");
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
	
	@Override
	public int getLastId() throws SQLException
	{
		int lastId = 0;

		try
		{
			db.setPreparedStatement("SELECT MAX(AccountId) FROM Accounts");
			ResultSet rs = db.getPreparedStatement().executeQuery();
			
			if (rs.next())
			{
				lastId = rs.getInt(1);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}

		return lastId;
	}

	private void setParameters(Account account, boolean isUpdate) throws SQLException
	{
		if (isUpdate)
		{
			db.getCallableStatement().setInt(1, account.getId());
		}
		else
		{
			db.getCallableStatement().registerOutParameter(1, java.sql.Types.INTEGER);
		}

	    db.getCallableStatement().setString(2, account.getCbu());
	    db.getCallableStatement().setBigDecimal(3, account.getBalance());
	    db.getCallableStatement().setInt(4, account.getAccountType().getId());
	    
	    if (!isUpdate) // TODO: En realidad, es posible prescindir de este if, pero create() y update() deberían tener clientId como parámetro de entrada.
	    {
	    	db.getCallableStatement().setInt(5, account.getClient().getClientId());
	    }
	}

	private void assignResultSet(Account account, ResultSet rs) throws SQLException
	{
		try
		{
			account.setId(rs.getInt("AccountId"));
			account.setActiveStatus(rs.getBoolean("ActiveStatus"));
			account.setCbu(rs.getString("Cbu"));
			account.setCreationDate(rs.getDate("CreationDate"));
			account.setBalance(rs.getBigDecimal("Balance"));

			int accountTypeId = rs.getInt("AccountTypeId");
			account.setAccountType(accountTypesDao.read(accountTypeId));

			int clientId = rs.getInt("ClientId");
			account.setClient(clientsDao.read(clientId));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
}
