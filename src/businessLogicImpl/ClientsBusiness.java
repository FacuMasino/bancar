package businessLogicImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import businessLogic.IClientsBusiness;
import businessLogic.ILoansBusiness;
import dataAccessImpl.ClientsDao;
import dataAccessImpl.LoansDao;
import domainModel.Account;
import domainModel.Client;
import exceptions.BusinessException;
import exceptions.InvalidFieldsException;
import exceptions.SQLOperationException;
import utils.Validator;

public class ClientsBusiness implements IClientsBusiness
{
	private ClientsDao clientsDao;
	private UsersBusiness usersBusiness;
	private AccountsBusiness accountsBusiness;
	private ILoansBusiness loansBusiness;
	
	public ClientsBusiness()
	{
		clientsDao = new ClientsDao();
		usersBusiness = new UsersBusiness();
		accountsBusiness = new AccountsBusiness();
		loansBusiness = new LoansBusiness();
	}

	@Override
	public boolean create(Client client) throws BusinessException
	{
		validateDni(client);
		usersBusiness.validateUsername(client);
		
		try
		{
			if ("No informa".equals(client.getSex()))
			{
			    client.setSex(null);
			}

			List<String> invalidFields = Validator.validateClientFields(client);

			if(!invalidFields.isEmpty())
			{
				throw new InvalidFieldsException(invalidFields);
			}
			
			if (0 < clientsDao.create(client))
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
			throw new BusinessException(
					"Ocurrió un error desconocido al crear el cliente.");
		}
		
		return false;
	}

	@Override
	public Client read(int clientId) throws BusinessException 
	{
		try 
		{
			return clientsDao.read(clientId);
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido al leer el cliente.");
		}
	}

	@Override
	public boolean update(Client client) throws BusinessException 
	{
		validateDni(client);
		usersBusiness.validateUsername(client);

		try
		{
			if ("No informa".equals(client.getSex()))
			{
			    client.setSex(null);
			}

			List<String> invalidFields = Validator.validateClientFields(client);

			if(!invalidFields.isEmpty())
			{
				throw new InvalidFieldsException(invalidFields);
			}
			
			return clientsDao.update(client);
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido al actualizar los datos del cliente.");
		}
	}

	@Override
	public boolean toggleActiveStatus(int clientId, boolean currentActiveStatus)
			throws BusinessException
	{
		try
		{
			if (currentActiveStatus)
			{
				validationsBeforeDelete(clientId);
				
				// Rechazar todos los préstamos que aún no se aprobaron
				Client client = read(clientId);
				loansBusiness.rejectAll(client);
			}
			
			return clientsDao.toggleActiveStatus(clientId, currentActiveStatus);
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (BusinessException ex)
		{
			System.err.println("Error al eliminar el cliente: " + ex.getMessage());
		    throw ex;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido al eliminar el cliente.");
		}
		
	}

	@Override
	public ArrayList<Client> list() throws BusinessException 
	{
		try
		{
			return clientsDao.list();
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido al obtener los clientes.");
		}
	}
	
	public ArrayList<Client> listActiveClients() throws BusinessException 
	{
		try
		{	
			//aplico filtro de clientes activos a la lista de clientes general.
			return (ArrayList<Client>) clientsDao.list().stream().filter(client->client.getActiveStatus() == true).collect(Collectors.toList());
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido al obtener los clientes activos.");
		}
	}
	
	public int findClientId(int userId) throws BusinessException
	{
		try
		{
			return clientsDao.findClientId(userId);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido al buscar el ID de cliente en la BD.");
		}
	}
	
	public int findClientId(Account account) throws BusinessException
	{
		try
		{
			return clientsDao.findClientId(account);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLOperationException(ex.getMessage());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido al buscar el ID de cliente en la BD.");
		}
	}
	
	private void validateDni(Client client) throws BusinessException
	{
		try
		{
			if (client.getClientId() != clientsDao.findClientId(client.getDni()))
			{
				throw new BusinessException(
						"El DNI ingresado pertenece a otro cliente.");
			}
		}
		catch (BusinessException ex)
		{
			throw ex;
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido al validar el DNI.");
		}
	}
	
	// TODO: Estas validaciones deberían hacerse individualmente en cada negocio
	// Cada validación en su negocio correspondiente, ejemplo:
	// accountsBusiness.delete tendrá sus validaciones y devolverá una excepción
	// según corresponda. Esto evita validaciones repetidas
	private void validationsBeforeDelete(int clientId) throws BusinessException
	{
		int CountNegativeAccounts = 0;
		ArrayList<Account> accounts = accountsBusiness.list(clientId);
		Client client;
		try
		{
			client = clientsDao.read(clientId);
		
		for (Account account : accounts)
		{
			if (account.getBalance().compareTo(BigDecimal.ZERO) < 0)
			{
				CountNegativeAccounts++;
			}
		}
		if (CountNegativeAccounts > 0)
		{
			if (CountNegativeAccounts > 1)
			{
				throw new BusinessException(
						"No es posible procesar la baja del cliente. Registra cuentas con deuda.");
			} 
			else
			{
				throw new BusinessException(
						"No es posible procesar la baja del cliente. Registra cuenta con deuda.");
			}
		}
		
		if (loansBusiness.currentLoans(client))
        	{
        		throw new BusinessException
        		( "No es posible procesar la baja del cliente. Tiene prestamos vigentes.");
        	}
	    }
		 catch (SQLException e)
		{
			e.printStackTrace();
			throw new SQLOperationException(e.getMessage());
		}
	}
	
	// TODO: Eliminar este método y reemplazar todos sus llamados por cliBiz.read(cliBiz.findClientId(userId))
	// (Doble click en el nombre del método y click en Open Call Hierarchy para ver llamados)
	public Client findClientByUserId(int userId) throws BusinessException
	{
		return read(findClientId(userId));
	}
	
	}