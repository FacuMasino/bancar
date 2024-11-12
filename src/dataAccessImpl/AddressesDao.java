package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dataAccess.IAddressesDao;
import domainModel.Address;

public class AddressesDao implements IAddressesDao
{
	private Database db;
	private CitiesDao citiesDao;
	private ProvincesDao provincesDao;
	private CountriesDao countriesDao;
	
	public AddressesDao()
	{
		db = new Database();
		citiesDao = new CitiesDao();
		provincesDao = new ProvincesDao();
		countriesDao = new CountriesDao();
	}

	@Override	
	public int create(Address address) throws SQLException
	{
		countriesDao.handleId(address.getCountry());
		provincesDao.handleId(address.getProvince(), address.getCountry().getId());
		citiesDao.handleId(address.getCity(), address.getProvince().getId());
		
		try
		{
			db.setCallableStatement("{CALL insert_address(?, ?, ?, ?, ?, ?)}");
			setParameters(address, false);
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
	public Address read(int addressId) throws SQLException
	{
		Address address = new Address();
		ResultSet rs;
		
		try
		{
			db.setPreparedStatement("Select * from Addresses where AddressId = ?");
			db.getPreparedStatement().setInt(1, addressId);
			rs = db.getPreparedStatement().executeQuery();
			
			if(!rs.next())
			{
				return null;
			}
			
			assignResultSet(address, rs);
			
			int cityId = rs.getInt("CityId");
			address.setCity(citiesDao.read(cityId));

			int provinceId = provincesDao.getId(cityId);
			address.setProvince(provincesDao.read(provinceId));

			int countryId = countriesDao.getId(provinceId);
			address.setCountry(countriesDao.read(countryId));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		
		return address;
	}

	@Override
	public boolean update(Address address) throws SQLException
	{
		return false;
	}

	@Override
	public boolean delete(int addressId) throws SQLException
	{
		return false;
	}

	@Override
	public ArrayList<Address> list() throws SQLException
	{
		return null;
	}
	
	public int getId(Address address) throws SQLException
	{
		return getId(
				address.getStreetName(),
				address.getStreetNumber(),
				address.getCity().getId());
	}
	
	public int getId(String streetName, String streetNumber, int cityId) throws SQLException
	{
		ResultSet rs;
		
		try
		{
			db.setPreparedStatement("SELECT AddressId FROM Addresses WHERE StreetName = ? AND StreetNumber = ? AND CityId = ?;");
			db.getPreparedStatement().setString(1, streetName);
			db.getPreparedStatement().setString(2, streetNumber);
			db.getPreparedStatement().setInt(3, cityId);
			rs = db.getPreparedStatement().executeQuery();
			
			if(!rs.next())
			{
				return 0;
			}
			
			return rs.getInt("AddressId");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
	
	public void handleId(Address address) throws SQLException
	{
		try
		{
			if (address != null)
		    {
		        int foundId = getId(address);

		        if (foundId == 0)
		        {
		        	address.setId(create(address));
		        }
		        else if (foundId == address.getId())
		        {
		            update(address);
		        }
		        else
		        {
		        	address.setId(foundId);
		        }
		    }
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
	
	private void setParameters(Address address, boolean isUpdate) throws SQLException
	{
		if (isUpdate)
		{
			db.getCallableStatement().setInt(1, address.getId());
		}
		else
		{
			db.getCallableStatement().registerOutParameter(1, java.sql.Types.INTEGER);
		}

		db.getCallableStatement().setString(2, address.getStreetName());
		db.getCallableStatement().setString(3, address.getStreetNumber());
		db.getCallableStatement().setString(4, address.getFlat());
		db.getCallableStatement().setString(5, address.getDetails());
		db.getCallableStatement().setInt(6, address.getCity().getId());
	}
	
	private void assignResultSet(Address address, ResultSet rs) throws SQLException
	{
		try
		{
			address.setId(rs.getInt("AddressId"));
			address.setStreetName(rs.getString("StreetName"));
			address.setStreetNumber(rs.getString("StreetNumber"));
			address.setFlat(rs.getString("Flat"));
			address.setDetails(rs.getString("Details"));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
}
