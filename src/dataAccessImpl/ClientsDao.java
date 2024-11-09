package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dataAccess.IClientsDao;
import domainModel.Address;
import domainModel.Client;
import domainModel.Country;

public class ClientsDao implements IClientsDao {
	
	private Database db;

	public ClientsDao()
	{
		db = new Database();
	}
	
	public boolean create(Client client) throws SQLException
	{//TODO: PENDIENTE CREACION DEL USUARIO A LA VEZ QUE SE CREA CLIENTE
		int rows = 0;
		
		try
		{
			db.setPreparedStatement("{CALL insert_client(?, ?, ?, ?, ?, ?, ?, ?, ? ,? )}");
			setParameters(client);
			rows = db.getPreparedStatement().executeUpdate();
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		
		return (rows > 0);
	}

	@Override
	public Client read(int clientId) throws SQLException
	{
		ResultSet rsClient;
		// El negocio debe verificar que lo devuelto != null
		Client auxClient = null;
		
		try {
			db.setPreparedStatement("Select * from Clients where ClientId = ?");
			db.getPreparedStatement().setInt(1, clientId);
			rsClient = db.getPreparedStatement().executeQuery();
			
			if(!rsClient.next()) return auxClient; // no se encontró, devuelve null
			
			// TODO: IMPORTANTE leer pais con su ID usando CountryBussiness, ahora está vacía
			// TODO: IMPORTANTE leer direccion con su ID usando AddressBussiness, ahora está vacía
					
			Country auxNationality = new Country();
			Address auxAddress = new Address();
			
			auxClient = getClient(rsClient, auxNationality,auxAddress);
		}
		catch (Exception ex) {
			
			ex.printStackTrace();
			throw ex;
		}
		
		return auxClient;
	}

	@Override
	public boolean update(Client client) throws SQLException
	{
		int rows = 0;
		
		try 
		{
			db.setPreparedStatement("{CALL update_client(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			setUpdateParameters(client);
			rows = db.getPreparedStatement().executeUpdate();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}
		
		return (rows > 0);	
	}

	@Override
	public boolean delete(int clientId) throws SQLException
	{//TODO: PENDIENTE ELIMINACION DE INFO ASOCIADA A ESE CLIENTE (USUARIO, DIRECCION)
		int rows = 0;
		
		try 
		{
			db.setPreparedStatement("UPDATE Clients SET IsActive = 0 WHERE ClientId = ?");
			db.getPreparedStatement().setInt(1, clientId);
			rows = db.getPreparedStatement().executeUpdate();
		}
		catch (SQLException ex) {
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
		
		try {
			db.setPreparedStatement("Select * from Clients WHERE isActive = 1");
			rsClients = db.getPreparedStatement().executeQuery();
			
			while(rsClients.next()) {
				
				// TODO: IMPORTANTE leer pais con su ID usando CountryBussiness, ahora está vacía
				Country auxNationality = new Country ();
				// TODO: IMPORTANTE leer direccion con su ID usando AddressBussiness, ahora está vacía
				Address auxAddress = new Address ();
				
				clients.add(getClient(rsClients, auxNationality, auxAddress));
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return clients;
	}

	@Override
	public int getId (Client client) throws SQLException
	{
		return 0; 
	}
	

	
	private void setParameters(Client client) throws SQLException
	{
		db.getPreparedStatement().setString(1, client.getDni());
		db.getPreparedStatement().setString(2, client.getCuil());
		db.getPreparedStatement().setString(3, client.getFirstName());
		db.getPreparedStatement().setString(4, client.getLastName());
		db.getPreparedStatement().setString(5, client.getSex());
		db.getPreparedStatement().setString(6, client.getEmail());
		db.getPreparedStatement().setString(7, client.getPhone());
		db.getPreparedStatement().setDate(8, client.getBirthDate());
		db.getPreparedStatement().setInt(9, client.getNationality().getId());
		db.getPreparedStatement().setInt (10, client.getAddress().getId());
		
	}
	
	private void setUpdateParameters(Client client) throws SQLException
	{
		db.getPreparedStatement().setString(1, client.getDni());
		db.getPreparedStatement().setString(2, client.getCuil());
		db.getPreparedStatement().setString(3, client.getFirstName());
		db.getPreparedStatement().setString(4, client.getLastName());
		db.getPreparedStatement().setString(5, client.getSex());
		db.getPreparedStatement().setString(6, client.getEmail());
		db.getPreparedStatement().setString(7, client.getPhone());
		db.getPreparedStatement().setDate(8, client.getBirthDate());
		db.getPreparedStatement().setInt(9, client.getNationality().getId());
		db.getPreparedStatement().setInt (10, client.getAddress().getId());
		db.getPreparedStatement().setInt(11, client.getId());
		
	}
	
	private Client getClient(ResultSet rs, Country nationality, Address address) throws SQLException {
		
		Client auxClient = new Client ();
		
		try { 
			 
			auxClient.setDni(rs.getString("Dni"));
			auxClient.setActive(rs.getBoolean("IsActive"));
			auxClient.setCuil(rs.getString("Cuil"));
			auxClient.setFirstName(rs.getString("FirtsName"));
			auxClient.setLastName(rs.getString("LastName"));
			auxClient.setSex(rs.getString("Sex"));
			auxClient.setEmail(rs.getString("Email"));
			auxClient.setPhone(rs.getString("Phone"));
			auxClient.setBirthDate(rs.getDate("BirthDate"));
			auxClient.setNationality(nationality);
			auxClient.setAddress(address);
			auxClient.setId(rs.getInt("ClientId"));
		
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		
		return auxClient;
	}
}

