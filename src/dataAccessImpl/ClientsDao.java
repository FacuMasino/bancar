package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dataAccess.IClientsDao;
import domainModel.Address;
import domainModel.Client;
import domainModel.Country;

public class ClientsDao implements IClientsDao
{	
	private Database db;
	private CountriesDao countriesDao;
	private AddressesDao addressesDao;

	public ClientsDao()
	{
		db = new Database();
		countriesDao = new CountriesDao();
		addressesDao = new AddressesDao();
	}
	
	public int create(Client client) throws SQLException
	{
		countriesDao.handleId(client.getNationality());
		addressesDao.handleId(client.getAddress());

		try
		{
			db.setCallableStatement("{CALL insert_client(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			setParameters(client, false);
			db.getCallableStatement().executeUpdate();
			return db.getCallableStatement().getInt(1);
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public Client read(int clientId) throws SQLException
	{
		Client client = new Client();
		ResultSet rs;
		
		try
		{
			db.setPreparedStatement("Select * from Clients where ClientId = ?");
			db.getPreparedStatement().setInt(1, clientId);
			rs = db.getPreparedStatement().executeQuery();
			
			if(!rs.next())
			{
				return null;
			}
			
			assignResultSet(client, rs);
			
			int nationalityId = rs.getInt("NationalityId");
			client.setNationality(countriesDao.read(nationalityId));
			
			int addressId = rs.getInt("AddressId");
			client.setAddress(addressesDao.read(addressId));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		
		return client;
	}

	@Override
	public boolean update(Client client) throws SQLException
	{
		int rows = 0;
		
		try
		{
			db.setPreparedStatement("{CALL update_client(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			setParameters(client, true);
			rows = db.getPreparedStatement().executeUpdate();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		
		return (rows > 0);
	}

	@Override
	public boolean delete(int clientId) throws SQLException
	{
		int rows = 0;
		
		try
		{
			db.setPreparedStatement("UPDATE Clients SET IsActive = 0 WHERE ClientId = ?");
			db.getPreparedStatement().setInt(1, clientId);
			rows = db.getPreparedStatement().executeUpdate();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		
		return (rows > 0);
	}

	@Override
	public ArrayList<Client> list() throws SQLException
	{
		ResultSet rsClients;
		ArrayList<Client> clients = new ArrayList<Client>();
		
		try
		{
			db.setPreparedStatement("Select * from Clients WHERE isActive = 1");
			rsClients = db.getPreparedStatement().executeQuery();
			
			while(rsClients.next())
			{
				Country auxNationality = new Country ();
				Address auxAddress = new Address ();
				clients.add(getClient(rsClients, auxNationality, auxAddress));
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return clients;
	}
	
	private void setParameters(Client client, boolean isUpdate) throws SQLException
	{
		if (isUpdate)
		{
			db.getCallableStatement().setInt(1, client.getId());
		}
		else
		{
			db.getCallableStatement().registerOutParameter(1, java.sql.Types.INTEGER);
		}

		db.getPreparedStatement().setString(2, client.getDni());
		db.getPreparedStatement().setString(3, client.getCuil());
		db.getPreparedStatement().setString(4, client.getFirstName());
		db.getPreparedStatement().setString(5, client.getLastName());
		db.getPreparedStatement().setString(6, client.getSex());
		db.getPreparedStatement().setString(7, client.getEmail());
		db.getPreparedStatement().setString(8, client.getPhone());
		db.getPreparedStatement().setDate(9, client.getBirthDate());
		db.getPreparedStatement().setInt(10, client.getNationality().getId());
		db.getPreparedStatement().setInt (11, client.getAddress().getId());
	}
	
	private void assignResultSet(Client client, ResultSet rs) throws SQLException
	{
		try
		{
			client.setId(rs.getInt("ClientId"));
			client.setActive(rs.getBoolean("IsActive"));
			client.setDni(rs.getString("Dni"));
			client.setCuil(rs.getString("Cuil"));
			client.setFirstName(rs.getString("FirtsName"));
			client.setLastName(rs.getString("LastName"));
			client.setSex(rs.getString("Sex"));
			client.setEmail(rs.getString("Email"));
			client.setPhone(rs.getString("Phone"));
			client.setBirthDate(rs.getDate("BirthDate"));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
}
