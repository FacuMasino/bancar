package dataAccess;

import java.sql.SQLException;
import java.util.ArrayList;
import domainModel.Client;

public interface IClientsDao
{
	public boolean create(Client client) throws SQLException;
	public Client read(int accountId) throws SQLException;
	public boolean update(Client client) throws SQLException;
	public boolean delete(int clientId) throws SQLException;
	public ArrayList<Client> list() throws SQLException;
	public int getId(Client client) throws SQLException;
}
