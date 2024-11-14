package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dataAccess.IClientsDao;
import domainModel.Client;

public class ClientsDao implements IClientsDao
{	
	private Database db;
	private CountriesDao countriesDao;
	private AddressesDao addressesDao;
	private UsersDao usersDao;

	public ClientsDao()
	{
		db = new Database();
		countriesDao = new CountriesDao();
		addressesDao = new AddressesDao();
		usersDao = new UsersDao();
	}
	
	public int create(Client client) throws SQLException
	{
		countriesDao.handleId(client.getNationality());
		addressesDao.handleId(client.getAddress());
		usersDao.handleId(client);

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
			db.setPreparedStatement("SELECT * FROM Clients WHERE ClientId = ?;");
			db.getPreparedStatement().setInt(1, clientId);
			rs = db.getPreparedStatement().executeQuery();
			
			if(!rs.next())
			{
				return null;
			}
			
			assignResultSet(client, rs);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		
		return client;
	}
	
	@Override
	public Client readByUserId(int userId) throws SQLException
	{
		return read(getClientId(userId));
	}

	@Override
	public boolean update(Client client) throws SQLException
	{
		countriesDao.handleId(client.getNationality());
		addressesDao.handleId(client.getAddress());
		usersDao.handleId(client);
		
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

	public boolean toggleActiveStatus(int clientId, boolean currentActiveStatus) throws SQLException
	{
		int userId = usersDao.getUserId(clientId);
		boolean rtn = usersDao.toggleActiveStatus(userId, currentActiveStatus);
		
		if (rtn == false)
		{
			return rtn;
		}
		
		int rows = 0;
		
		try
		{
			db.setPreparedStatement("UPDATE Clients SET ActiveStatus = ? WHERE ClientId = ?;");
			db.getPreparedStatement().setBoolean(1, !currentActiveStatus);
			db.getPreparedStatement().setInt(2, clientId);
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
		ResultSet rs;
		ArrayList<Client> clients = new ArrayList<Client>();
		
		try
		{
			db.setPreparedStatement("SELECT * FROM Clients;");
			rs = db.getPreparedStatement().executeQuery();
			
			while(rs.next())
			{
				Client client = new Client();
				assignResultSet(client, rs);
				clients.add(client);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return clients;
	}
	
	public int getClientId(int userId) throws SQLException
	{
		ResultSet rs;
		
		try
		{
			db.setPreparedStatement("SELECT ClientId FROM Clients WHERE UserId = ?;");
			db.getPreparedStatement().setInt(1, userId);
			rs = db.getPreparedStatement().executeQuery();
			
			if(!rs.next())
			{
				return 0;
			}
			
			return rs.getInt("ClientId");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
	
	private void setParameters(Client client, boolean isUpdate) throws SQLException
	{
		if (isUpdate)
		{
			db.getCallableStatement().setInt(1, client.getClientId());
		}
		else
		{
			db.getCallableStatement().registerOutParameter(1, java.sql.Types.INTEGER);
		}

		db.getCallableStatement().setString(2, client.getDni());
		db.getCallableStatement().setString(3, client.getCuil());
		db.getCallableStatement().setString(4, client.getFirstName());
		db.getCallableStatement().setString(5, client.getLastName());
		db.getCallableStatement().setString(6, client.getSex());
		db.getCallableStatement().setString(7, client.getEmail());
		db.getCallableStatement().setString(8, client.getPhone());
		db.getCallableStatement().setDate(9, client.getBirthDate());
		db.getCallableStatement().setInt(10, client.getNationality().getId());
		db.getCallableStatement().setInt (11, client.getAddress().getId());
		db.getCallableStatement().setInt (12, client.getUserId());
	}
	
	private void assignResultSet(Client client, ResultSet rs) throws SQLException
	{
		try
		{
			client.setClientId(rs.getInt("ClientId"));
			client.setActiveStatus(rs.getBoolean("ActiveStatus"));
			client.setDni(rs.getString("Dni"));
			client.setCuil(rs.getString("Cuil"));
			client.setFirstName(rs.getString("FirstName"));
			client.setLastName(rs.getString("LastName"));
			client.setSex(rs.getString("Sex"));
			client.setEmail(rs.getString("Email"));
			client.setPhone(rs.getString("Phone"));
			client.setBirthDate(rs.getDate("BirthDate"));
			
			int nationalityId = rs.getInt("NationalityId");
			client.setNationality(countriesDao.read(nationalityId));
			
			int addressId = rs.getInt("AddressId");
			client.setAddress(addressesDao.read(addressId));
			
			int userId = rs.getInt("UserId");
			client.setUser(usersDao.read(userId));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
}
