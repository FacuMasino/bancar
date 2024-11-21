package businessLogic;

import java.util.ArrayList;
import domainModel.Account;
import exceptions.BusinessException;

public interface IAccountsBusiness
{
	public boolean create(Account account) throws BusinessException;
	public Account read(int accountId) throws BusinessException;
	public boolean update(Account account) throws BusinessException;
	public boolean delete(int accountId) throws BusinessException;
	public ArrayList<Account> list() throws BusinessException;
	public ArrayList<Account> listByIdClient(int clientId) throws BusinessException;
}
