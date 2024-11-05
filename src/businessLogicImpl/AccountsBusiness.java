package businessLogicImpl;

import java.util.ArrayList;
import businessLogic.IAccountsBusiness;
import dataAccessImpl.AccountsDao;
import domainModel.Account;

public class AccountsBusiness implements IAccountsBusiness
{
	private AccountsDao accountsDao;
	
	public AccountsBusiness()
	{
		accountsDao = new AccountsDao();
	}

	@Override
	public boolean create(Account account)
	{
		return accountsDao.create(account);
	}

	@Override
	public Account read(int accountId)
	{
		return accountsDao.read(accountId);
	}

	@Override
	public void update(Account account)
	{
		accountsDao.update(account);
	}

	@Override
	public void delete(int accountId)
	{
		accountsDao.delete(accountId);
	}

	@Override
	public ArrayList<Account> list()
	{
		return accountsDao.list();
	}

	@Override
	public int getId(Account account)
	{
		return accountsDao.getId(account);
	}
}
