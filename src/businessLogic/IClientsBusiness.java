package businessLogic;

import java.util.ArrayList;
import domainModel.Client;
import exceptions.BusinessException;

public interface IClientsBusiness
{
	public boolean create(Client client) throws BusinessException;
	public Client read(int clientId) throws BusinessException;
	public boolean update(Client client) throws BusinessException;
	public boolean delete(int clientId) throws BusinessException;
	public ArrayList<Client> list() throws BusinessException;
	public int getId(Client client) throws BusinessException;
}
