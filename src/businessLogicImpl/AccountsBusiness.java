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
			// Asignar CBU
			int nextId = accountsDao.getLastId() + 1;
			account.setCbu(generateCBU(nextId));
			
			return accountsDao.create(account);
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al crear la cuenta.");
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
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al leer la cuenta.");
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
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al actualizar la cuenta.");
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
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al eliminar la cuenta.");
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
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al obtener las cuentas.");
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
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al obtener ID de cuenta.");
		}
	}
	
	/*
	 * Genera un CBU ficticio basado en el nro de cuenta (ID)
	 */
	private String generateCBU(int accountId)
	{
		String entity = "919"; // Pos 1-3
		String branch = "0001"; // Pos 4-7
		String firstDV = "9"; // Pos 8,  DV = digito verificador
		String lastDV = "1"; // Pos 22
		// Completar el ID de la cuenta con 0 a la izquierda para llegar a 13 posiciones
		String accNumber = String.format("%013d", accountId);
		
		return entity + branch + firstDV + accNumber + lastDV;
	}
}
