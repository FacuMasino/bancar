package businessLogic;

import java.util.ArrayList;
import domainModel.Account;

public interface IAccountsBusiness
{
	public boolean create(Account account);
	public Account read(int accountId);
	public void update(Account account);
	public void delete(int accountId);
	public ArrayList<Account> list();
	public int getId(Account account);
}
