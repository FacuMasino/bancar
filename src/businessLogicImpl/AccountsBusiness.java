package businessLogicImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import businessLogic.IAccountsBusiness;
import businessLogic.IMovementsBusiness;
import dataAccess.IAccountsDao;
import dataAccess.ILoansDao;
import dataAccessImpl.AccountsDao;
import dataAccessImpl.LoansDao;
import domainModel.Account;
import domainModel.Client;
import domainModel.Movement;
import domainModel.MovementType;
import domainModel.MovementTypeEnum;
import exceptions.BusinessException;
import exceptions.SQLOperationException;

public class AccountsBusiness implements IAccountsBusiness
{
	private IAccountsDao accountsDao;
	private ILoansDao loansDao;
	private Client client;
	private Account account;
	private IMovementsBusiness movementsBusiness;
	
	public AccountsBusiness()
	{
		accountsDao = new AccountsDao();
		loansDao = new LoansDao();
		movementsBusiness = new MovementsBusiness();
	}
	
	@Override
	public boolean create(Account account) throws BusinessException
	{
		ArrayList <Account> accounts = new ArrayList <Account> ();
		
		try
		{
			accounts = accountsDao.list(account.getClient().getClientId());
			
			if (accounts.size()==3) 
			{
				throw new BusinessException ("El cliente no puede tener más de 3 cuentas activas.");	
			}

			int newAccountId = accountsDao.getLastId() + 1;
			account.setCbu(generateCBU(newAccountId));
			
			if (0 < accountsDao.create(account))
			{
				account.setId(newAccountId);
				
				Movement movement = new Movement();
				MovementType movementType = new MovementType();
				movementType.setId(MovementTypeEnum.NEW_ACCOUNT.getId());
				
				movement.setMovementType(movementType);
				movement.setAmount(account.getBalance());
				movement.setDetails("Apertura de cuenta");
				movement.setAccount(account);
				movementsBusiness.create(movement);
				
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
			throw new SQLOperationException(ex.getMessage());
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
			account = accountsDao.read(accountId);
			ArrayList<Account> accountsList = new ArrayList<Account>();
			client = account.getClient();
			int clientId = client.getId();
			accountsList = accountsDao.list(clientId);

			if (accountsList.size() == 1 && loansDao.currentLoans(client))
			{
				// Caso saldo negativo
				if (account.getBalance().compareTo(BigDecimal.ZERO) < 0)
				{
					throw new BusinessException(
							"Existen prestamos vigentes y la cuenta tiene saldo negativo.");
				}
				throw new BusinessException("El cliente posee prestamos sin saldar.");
			}
			
			// No se permite la eliminación con saldo negativo
			if (account.getBalance().compareTo(BigDecimal.ZERO) < 0)
			{
				throw new BusinessException("Cuenta con saldo negativo.");
			}
			
			// TODO: Debemos permitir que se "elimine" una cuenta con saldo ?
			return accountsDao.delete(accountId);
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
			throw new BusinessException(
					"Ocurrió un error desconocido al eliminar la cuenta.");
		}
    }
	
	@Override
	public boolean delete(ArrayList<Account> accounts) throws BusinessException
	{
		try
		{
			int countDeleted = 0;

			for (Account account : accounts)
			{
				delete(account.getId());
				countDeleted++;
			}
			return countDeleted == accounts.size();
		} 
		catch (BusinessException ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido al eliminar las cuentas del cliente.");
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
	
	/**
	 * Si no tiene cuentas devolverá una lista vacía, se debe verificar
	 * En cada llamado según corresponda si la lista está vacía o no.
	 */
	@Override
	public ArrayList<Account> list(int clientId) throws BusinessException
	{
		try
		{
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
	
	@Override
	public int findId(String cbu) throws BusinessException
	{
		try
		{
			int accountId = accountsDao.findId(cbu);
			if (accountId == 0)
			{
				throw new BusinessException("El CBU no existe.");
			}
			return accountId;
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException(ex.getMessage());
		}
	}

	/*
	 * Genera un CBU ficticio basado en el nro de cuenta (ID)
	 */
	@Override
	public String generateCBU(int accountId)
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