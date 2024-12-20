package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import dataAccess.IAddressesDao;
import dataAccess.ICitiesDao;
import dataAccess.ICountriesDao;
import dataAccess.IProvincesDao;
import domainModel.Address;

public class AddressesDao extends Dao<Address> implements IAddressesDao
{
	private ICitiesDao citiesDao;
	private IProvincesDao provincesDao;
	private ICountriesDao countriesDao;
	
	public AddressesDao()
	{
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
		countriesDao.handleId(address.getCountry());
		provincesDao.handleId(address.getProvince(), address.getCountry().getId());
		citiesDao.handleId(address.getCity(), address.getProvince().getId());
		
		int rows = 0;
		
		try
		{
			db.setCallableStatement("{CALL update_address(?, ?, ?, ?, ?, ?)}");
			setParameters(address, true);
			rows = db.getCallableStatement().executeUpdate();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		
		return (rows > 0);
	}
	
	@Override
	public int findId(Address address) throws SQLException
	{
		return findId(
				address.getStreetName(),
				address.getStreetNumber(),
				address.getFlat(),
				address.getCity().getId());
	}
	
	@Override
	public int findId(String streetName, String streetNumber, String flat, int cityId) throws SQLException
	{
		ResultSet rs;
		
		try
		{
			db.setPreparedStatement("CALL find_address_id(?, ?, ?, ?)");
			db.getPreparedStatement().setString(1, streetName);
			db.getPreparedStatement().setString(2, streetNumber);
			db.getPreparedStatement().setString(3, flat);
			db.getPreparedStatement().setInt(4, cityId);
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
	
	@Override
	public void handleId(Address address) throws SQLException
	{
		super.handleId(address);
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
			
			int cityId = rs.getInt("CityId");
			address.setCity(citiesDao.read(cityId));

			int provinceId = provincesDao.findId(cityId);
			address.setProvince(provincesDao.read(provinceId));

			int countryId = countriesDao.findId(provinceId);
			address.setCountry(countriesDao.read(countryId));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
}
