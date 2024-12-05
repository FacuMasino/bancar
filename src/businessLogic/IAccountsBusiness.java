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
	public boolean delete(ArrayList<Account> accounts) throws BusinessException;
	public ArrayList<Account> list() throws BusinessException;
	public ArrayList<Account> list(int clientId) throws BusinessException;
	public int findId(String cbu) throws BusinessException;
	public String generateCBU(int accountId);
}
