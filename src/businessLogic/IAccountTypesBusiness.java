package businessLogic;

import java.util.ArrayList;
import domainModel.AccountType;
import exceptions.BusinessException;

public interface IAccountTypesBusiness 
{
	public AccountType read(int accountTypeId) throws BusinessException;
	public ArrayList<AccountType> list() throws BusinessException;
	public int getId(AccountType accountType) throws BusinessException;
}
