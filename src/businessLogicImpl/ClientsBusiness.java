package businessLogicImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import businessLogic.IClientsBusiness;
import dataAccessImpl.ClientsDao;
import domainModel.Client;
import exceptions.BusinessException;
import exceptions.SQLOperationException;

public class ClientsBusiness implements IClientsBusiness
{
	private ClientsDao clientsDao;
	
	public ClientsBusiness()
	{
		clientsDao = new ClientsDao();
	}

	@Override
	public boolean create(Client client) throws BusinessException
	{
		try
		{
			if (0 < clientsDao.create(client))
			{
				return true;
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
				("Ocurrió un error desconocido al crear el cliente.");
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
			throw new BusinessException
				("Ocurrió un error desconocido al leer el cliente.");
		}
	}

	@Override
	public boolean update(Client client) throws BusinessException 
	{
		try
		{
			return clientsDao.update(client);
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al actualizar los datos del cliente.");
		}
	}

	@Override
	public boolean toggleActiveStatus(int clientId, boolean currentActiveStatus) throws BusinessException
	{
		try
		{
			return clientsDao.toggleActiveStatus(clientId, currentActiveStatus);
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al eliminar el cliente.");
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
			throw new BusinessException
				("Ocurrió un error desconocido al obtener los clientes.");
		}
	}

	@Override
	public Client findClientByUserId(int userId) throws BusinessException {
		try 
		{
			Client client = clientsDao.readByUserId(userId);
			if(client == null)
			{
				throw new BusinessException
					("No se encontró un cliente asociado a este usuario");
			}
			return client;
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
				("Ocurrió un error desconocido al leer el cliente.");
		}
	}
}
