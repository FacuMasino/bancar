package dataAccess;

import java.util.ArrayList;
import domainModel.Client;

public interface IClientsDao {
	
	public boolean create(Client client);
	public Client read(int accountId);
	public void update(Client  client);
	public void delete(int clientId);
	public ArrayList<Client > list();
	public int getId(Client client);
}
