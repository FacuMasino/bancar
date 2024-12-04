package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dataAccess.IClientsDao;
import domainModel.Account;
import domainModel.Client;
import domainModel.User;

public class ClientsDao extends Dao<Client> implements IClientsDao
{	
	private CountriesDao countriesDao;
	private AddressesDao addressesDao;
	private UsersDao usersDao;

	public ClientsDao()
	{
		countriesDao = new CountriesDao();
		addressesDao = new AddressesDao();
		usersDao = new UsersDao();
	}
	
	public int create(Client client) throws SQLException
	{
		handleUserId(client);
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
	public boolean update(Client client) throws SQLException
	{
		handleUserId(client);
		countriesDao.handleId(client.getNationality());
		addressesDao.handleId(client.getAddress());
		
		int rows = 0;
		
		try
		{
			db.setCallableStatement("{CALL update_client(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			setParameters(client, true);
			rows = db.getCallableStatement().executeUpdate();
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
		int rows = 0;
		
		try
		{
			db.setPreparedStatement("UPDATE Clients SET ActiveStatus = ? WHERE ClientId = ?;");
			db.getPreparedStatement().setBoolean(1, !currentActiveStatus);
			db.getPreparedStatement().setInt(2, clientId);
			rows = db.getPreparedStatement().executeUpdate();
			AccountsDao accountsDao = new AccountsDao();
			ArrayList<Account> accountsList = new ArrayList<>();
				
				accountsList =	accountsDao.list(clientId);
			
			 for (Account account : accountsList) {
				 
				 int accountId = account.getId();
					accountsDao.delete(accountId)  ;
		        }
		
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
	
	@Override
	protected int findId(Client client) throws SQLException
	{
		return findClientId(client.getDni());
	}
	
	public int findClientId(String dni) throws SQLException
	{
		ResultSet rs;
		
		try
		{
			db.setPreparedStatement("SELECT ClientId FROM Clients WHERE Dni = ?;");
			db.getPreparedStatement().setString(1, dni);
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

	public int findClientId(int userId) throws SQLException
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
	
	public int findClientId(Account account) throws SQLException
	{
		ResultSet rs;
		
		try
		{
			db.setPreparedStatement("SELECT ClientId FROM Accounts WHERE AccountId = ?;");
			db.getPreparedStatement().setInt(1, account.getId());
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
	
	private void handleUserId(Client client) throws SQLException
	{
		User user = new User();
		user.setUserId(client.getUserId());
		user.setUsername(client.getUsername());
		user.setPassword(client.getPassword());
		user.setRole(client.getRole());
		usersDao.handleId(user);
		client.setUserId(user.getUserId());
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
