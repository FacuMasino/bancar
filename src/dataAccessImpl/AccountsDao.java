package dataAccessImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import dataAccess.IAccountsDao;
import domainModel.Account;

public class AccountsDao extends DataAccessObject implements IAccountsDao
{
	private Database db;
	
	public AccountsDao()
	{
		db = new Database();
	}

	@Override
	public boolean create(Account account)
	{
		callDriver();

		int rows = 0;
		
		try
		{
			db.setPreparedStatement("{CALL insert_account(?, ?, ?, ?)}");
			setParameters(account);
			rows = db.getPreparedStatement().executeUpdate();
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		
		return (rows > 0);
	}

	@Override
	public Account read(int accountId)
	{
		return null;
	}

	@Override
	public void update(Account account)
	{
		
	}

	@Override
	public void delete(int accountId)
	{
		
	}

	@Override
	public ArrayList<Account> list()
	{
		return null;
	}

	@Override
	public int getId(Account account)
	{
		return 0;
	}
	
	private void setParameters(Account account) throws SQLException
	{
		db.getPreparedStatement().setString(1, account.getCbu());
		db.getPreparedStatement().setBigDecimal(2, account.getBalance());
		db.getPreparedStatement().setInt(3, account.getAccountType().getId());
		db.getPreparedStatement().setInt(4, account.getClient().getId());
	}
}
