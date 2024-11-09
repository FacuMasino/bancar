package businessLogicImpl;

import java.util.ArrayList;
import businessLogic.IClientsBusiness;
import dataAccessImpl.ClientsDao;
import domainModel.Client;

public class ClientsBusiness implements IClientsBusiness {

private ClientsDao clientsDao;
	
	public ClientsBusiness()
	{
		clientsDao = new ClientsDao();
	}

	@Override
	public boolean create(Client client)
	{
		return clientsDao.create(client);
	}

	@Override
	public Client read(int clientId)
	{
		return clientsDao.read(clientId);
	}

	@Override
	public void update(Client client)
	{
		clientsDao.update(client);
	}

	@Override
	public void delete(int clientId)
	{
		clientsDao.delete(clientId);
	}

	@Override
	public ArrayList<Client> list()
	{
		return clientsDao.list();
	}

	@Override
	public int getId(Client client)
	{
		return clientsDao.getId(client);
	}
}

}
