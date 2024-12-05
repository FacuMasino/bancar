package businessLogic;

import java.util.ArrayList;

import domainModel.Account;
import domainModel.Client;
import exceptions.BusinessException;

public interface IClientsBusiness
{
	public boolean create(Client client) throws BusinessException;
	public Client read(int clientId) throws BusinessException;
	public boolean update(Client client) throws BusinessException;
	public boolean toggleActiveStatus(int clientId, boolean currentActiveStatus) throws BusinessException;
	public ArrayList<Client> list() throws BusinessException;
	public ArrayList<Client> listActiveClients() throws BusinessException;
	public int findClientId(int userId) throws BusinessException;
	public int findClientId(Account account) throws BusinessException;
}
