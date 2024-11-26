package dataAccess;

import java.sql.SQLException;
import java.util.ArrayList;
import domainModel.Client;

public interface IClientsDao
{
	public int create(Client client) throws SQLException;
	public Client read(int clientId) throws SQLException;
	public boolean update(Client client) throws SQLException;
	public boolean toggleActiveStatus(int clientId, boolean currentActiveStatus) throws SQLException;
	public ArrayList<Client> list() throws SQLException;
}
