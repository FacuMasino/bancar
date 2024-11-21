package businessLogicImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import businessLogic.IAccountTypesBusiness;
import dataAccessImpl.AccountTypesDao;
import domainModel.AccountType;
import exceptions.BusinessException;
import exceptions.SQLOperationException;

public class AccountTypesBusiness implements IAccountTypesBusiness
{
	private AccountTypesDao accountTypesDao;

	public AccountTypesBusiness()
	{
		accountTypesDao = new AccountTypesDao();
	}

	@Override
	public AccountType read(int accountTypeId) throws BusinessException
	{
		try
		{
			AccountType accountType = accountTypesDao.read(accountTypeId);
			
			if (accountType == null)
			{
				throw new BusinessException("No existe el tipo de cuenta.");
			}
			
			return accountTypesDao.read(accountTypeId);
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException("Ocurrió un error desconocido al leer el tipo de cuenta.");
		}
	}

	@Override
	public ArrayList<AccountType> list() throws BusinessException
	{
		try
		{
			return accountTypesDao.list();
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException("Ocurrió un error desconocido al obtener los tipos de cuenta.");
		}
	}

	@Override
	public int getId(AccountType accountType) throws BusinessException
	{
		try
		{	
			return accountTypesDao.getId(accountType);
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException("Ocurrió un error desconocido al obtener ID de cuenta.");
		}
	}
}
