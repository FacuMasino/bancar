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
			db.setPreparedStatement("Select * from Clients where ClientId = ?;");
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

	// TODO: Desarrollar los métodos update() en los DAO correspondientes
	// para la actualización de la información del cliente en las tablas relacionadas.
	// No es necesario modificar nada en este método, ya que los handleId() se encargan.
	// Para realizar esto, se recomienda primero: probar los métodos create(),
	// read() y list() y definir responsabilidades de las capas en la arquitectura.
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

	// TODO: Desarrollar los métodos delete() en los DAO correspondientes
	// para la baja lógica de la información del cliente de las tablas relacionadas.
	// Es necesario, además, implementar la lógica en este método.
	// Para realizar esto, se recomienda primero: probar los métodos create(),
	// read() y list() y definir responsabilidades de las capas en la arquitectura.
	@Override
	public boolean delete(int clientId) throws SQLException
	{
		int rows = 0;
		
		try
		{
			db.setPreparedStatement("UPDATE Clients SET IsActive = 0 WHERE ClientId = ?;");
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
		ResultSet rs;
		ArrayList<Client> clients = new ArrayList<Client>();
		
		try
		{
			db.setPreparedStatement("Select * from Clients;");
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
