package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dataAccess.IAccountTypesDao;
import domainModel.AccountType;

public class AccountTypesDao implements IAccountTypesDao
{
	private Database db;

	public AccountTypesDao()
	{
		db = new Database();
	}

	@Override
	public AccountType read(int accountTypeId) throws SQLException
	{
		ResultSet rs;
		AccountType accountType = new AccountType();

		try
		{
			db.setPreparedStatement("SELECT * FROM AccountTypes WHERE AccountTypeId = ?");
			db.getPreparedStatement().setInt(1, accountTypeId);
			rs = db.getPreparedStatement().executeQuery();

			if (!rs.next())
			{				
				return null;
			}

			assignResultSet(accountType, rs);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}

		return accountType;
	}
	
	@Override
	public AccountType readByName(String accountTypeName) throws SQLException
	{
		AccountType accountType = new AccountType();
		ResultSet rs;

		try
		{
			db.setPreparedStatement("SELECT * FROM AccountTypes WHERE AccountTypeName = ?");
			db.getPreparedStatement().setString(1, accountTypeName);
			rs = db.getPreparedStatement().executeQuery();

			if (!rs.next())
			{				
				return accountType;
			}

			assignResultSet(accountType, rs);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}

		return accountType;
	}

	@Override
	public ArrayList<AccountType> list() throws SQLException
	{
		ArrayList<AccountType> accountTypes = new ArrayList<AccountType>();
		ResultSet rs;

		try
		{
			db.setPreparedStatement("SELECT * FROM AccountTypes");
			rs = db.getPreparedStatement().executeQuery();

			while (rs.next())
			{
				AccountType accountType = new AccountType();
				assignResultSet(accountType, rs);
				accountTypes.add(accountType);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}

		return accountTypes;
	}

	private void assignResultSet(AccountType accountType, ResultSet rs) throws SQLException
	{
		try
		{
			accountType.setId(rs.getInt("AccountTypeId"));
			accountType.setName(rs.getString("AccountTypeName"));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
}
