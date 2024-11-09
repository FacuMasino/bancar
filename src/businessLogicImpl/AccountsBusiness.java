package businessLogicImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import businessLogic.IAccountsBusiness;
import dataAccessImpl.AccountsDao;
import domainModel.Account;
import exceptions.BusinessException;
import exceptions.SQLOperationException;

public class AccountsBusiness implements IAccountsBusiness
{
	private AccountsDao accountsDao;
	
	public AccountsBusiness()
	{
		accountsDao = new AccountsDao();
	}

	// TODO: PENDIENTE Ningún método valida las reglas de negocio
	
	@Override
	public boolean create(Account account) throws BusinessException 
	{
		try
		{
			return accountsDao.create(account);
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al crear la cuenta");
		}
	}

	@Override
	public Account read(int accountId) throws BusinessException
	{
		try 
		{
			return accountsDao.read(accountId);			
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al leer la cuenta");
		}
	}

	@Override
	public boolean update(Account account) throws BusinessException
	{
		try
		{
			return accountsDao.update(account);
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al actualizar la cuenta");
		}
	}

	@Override
	public boolean delete(int accountId) throws BusinessException
	{
		try
		{
			return accountsDao.delete(accountId);			
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al eliminar la cuenta");
		}
	}

	@Override
	public ArrayList<Account> list() throws BusinessException
	{
		try
		{
			return accountsDao.list();			
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al obtener las cuentas");
		}
	}

	@Override
	public int getId(Account account) throws BusinessException
	{
		try 
		{
			return accountsDao.getId(account);			
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al obtener ID de cuenta");
		}
	}
}
