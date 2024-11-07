package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dataAccess.IAccountsDao;
import domainModel.Account;
import domainModel.AccountType;
import domainModel.Client;

public class AccountsDao implements IAccountsDao
{
	private Database db;

	public AccountsDao()
	{
		db = new Database();
	}

	@Override
	public boolean create(Account account)
	{
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
		ResultSet rsAccount;
		// El negocio debe verificar que lo devuelto != null
		Account auxAccount = null;
		
		try {
			db.setPreparedStatement("Select * from Accounts where AccountId = ?");
			db.getPreparedStatement().setInt(1, accountId);
			rsAccount = db.getPreparedStatement().executeQuery();
			
			// TODO: IMPORTANTE leer cliente con su ID usando ClientsBusiness, ahora está vacío
			Client auxClient = new Client();
			// TODO: IMPORTANTE leer tipo de cuenta con su ID usando AccountTypeBusiness, ahora está vacía
			AccountType auxAccType = new AccountType();
				
			auxAccount = getAccount(rsAccount, auxClient, auxAccType);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return auxAccount;
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
		ResultSet rsAccounts;
		ArrayList<Account> accounts = new ArrayList<Account>();
		
		try {
			db.setPreparedStatement("Select * from Accounts");
			rsAccounts = db.getPreparedStatement().executeQuery();
			
			while(rsAccounts.next()) {
				
				// TODO: IMPORTANTE leer cliente con su ID usando ClientsBusiness, ahora está vacío
				Client auxClient = new Client();
				// TODO: IMPORTANTE leer tipo de cuenta con su ID usando AccountTypeBusiness, ahora está vacía
				AccountType auxAccType = new AccountType();
				
				accounts.add(getAccount(rsAccounts, auxClient, auxAccType));
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return accounts;
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
	
	private Account getAccount(ResultSet rs, Client client, AccountType accType) throws SQLException {
		
		Account auxAccount = new Account();
		
		auxAccount.setAccountType(accType);
		auxAccount.setActive(rs.getBoolean("IsActive"));
		auxAccount.setBalance(rs.getBigDecimal("Balance"));
		auxAccount.setCbu(rs.getString("Cbu"));
		auxAccount.setClient(client);
		auxAccount.setCreationDate(rs.getDate("CreationDate"));
		auxAccount.setId(rs.getInt("AccountId"));
		
		return auxAccount;
	}
}
