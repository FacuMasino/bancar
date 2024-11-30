package businessLogicImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import businessLogic.IAccountsBusiness;
import dataAccessImpl.AccountsDao;
import domainModel.Account;
import domainModel.Message.MessageType;
import exceptions.BusinessException;
import exceptions.SQLOperationException;
import utils.Helper;

public class AccountsBusiness implements IAccountsBusiness
{
	private AccountsDao accountsDao;
	
	public AccountsBusiness()
	{
		accountsDao = new AccountsDao();
	}
	
	@Override
	public boolean create(Account account) throws BusinessException
	{
		ArrayList <Account> accounts = new ArrayList <Account> ();
		
		
		try
		{
			accounts = accountsDao.list(account.getClientId());
			
			if (accounts.size()==3) 
			{
				throw new BusinessException ("El cliente no puede tener más de 3 cuentas activas.");	
			}

			int newAccountId = accountsDao.getLastId() + 1;
			account.setCbu(generateCBU(newAccountId));

			if (0 < accountsDao.create(account))
			{
				return true;
			}
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		
		catch (BusinessException ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al crear la cuenta.");
		}
		
		return false;
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
            Account account = accountsDao.read(accountId);
        
            if (account.getBalance().compareTo(BigDecimal.ZERO) >= 0)
            { 
                return accountsDao.delete(accountId);   
            }      
            else
            {
                    throw new BusinessException
                            ( "No se puede eliminar una cuenta con saldo negativo.");
             }
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
	public ArrayList<Account> list(int clientId) throws BusinessException
	{
		//TODO: revisar este list...que pasa si estoy listando todos las cuentas de todos los clientes?
		//cuando encuentre un cliente sin cuentas me tira la ex?
		//TODO: parche: Exception comentada para que funcione el listado de clientes
		try
		{
			ArrayList<Account> accounts = accountsDao.list(clientId);
			if(accounts.isEmpty())
			{
				//TODO: seño... Esta EXCEPTION ME ESTA MOLESTANDO....
				//throw new BusinessException("El cliente no posee cuentas activas");
			}
			return accountsDao.list(clientId);
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException("Ocurrió un error desconocido al leer las cuentas.");
		}
	}
	
	public int findId(String cbu)
	{
		try
		{
			return accountsDao.findId(cbu);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return 0;
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