package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dataAccess.ICitiesDao;
import domainModel.City;

public class CitiesDao implements ICitiesDao
{
	private Database db;
	
	public CitiesDao()
	{
		db = new Database();
	}

	@Override
	public int create(City city, int provinceId) throws SQLException
	{
		try
		{
			db.setCallableStatement("{CALL insert_city(?, ?, ?, ?)}");
			setParameters(city, provinceId, false);
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
	public City read(int cityId) throws SQLException
	{
		City city = new City();
		ResultSet rs;
		
		try
		{
			db.setPreparedStatement("Select * from Cities where CityId = ?;");
			db.getPreparedStatement().setInt(1, cityId);
			rs = db.getPreparedStatement().executeQuery();
			
			if(!rs.next())
			{
				return null;
			}
			
			assignResultSet(city, rs);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		
		return city;
	}

	@Override
	public boolean update(City city) throws SQLException
	{
		return false;
	}

	@Override
	public boolean delete(int cityId) throws SQLException
	{
		return false;
	}

	@Override
	public ArrayList<City> list() throws SQLException
	{
		return null;
	}
	
	public int getId(City city, int provinceId) throws SQLException
	{
		return getId(city.getName(), provinceId);
	}
	
	public int getId(String cityName, int provinceId) throws SQLException
	{
		ResultSet rs;
		
		try
		{
			db.setPreparedStatement("SELECT CityId FROM Cities WHERE CityName = ? AND ProvinceId = ?;");
			db.getPreparedStatement().setString(1, cityName);
			db.getPreparedStatement().setInt(2, provinceId);
			rs = db.getPreparedStatement().executeQuery();
			
			if(!rs.next())
			{
				return 0;
			}
			
			return rs.getInt("CityId");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
	
	public void handleId(City city, int provinceId) throws SQLException
	{
		try
		{
			if (city != null)
		    {
		        int foundId = getId(city, provinceId);

		        if (foundId == 0)
		        {
		        	city.setId(create(city, provinceId));
		        }
		        else if (foundId == city.getId())
		        {
		            update(city);
		        }
		        else
		        {
		        	city.setId(foundId);
		        }
		    }
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
	
	private void setParameters(City city, int provinceId, boolean isUpdate) throws SQLException
	{
		if (isUpdate)
		{
			db.getCallableStatement().setInt(1, city.getId());
		}
		else
		{
			db.getCallableStatement().registerOutParameter(1, java.sql.Types.INTEGER);
		}

		db.getCallableStatement().setString(2, city.getName());
		db.getCallableStatement().setString(3, city.getZipCode());
		db.getCallableStatement().setInt(4, provinceId);
	}
	
	private void assignResultSet(City city, ResultSet rs) throws SQLException
	{
		try
		{
			city.setId(rs.getInt("CityId"));
			city.setName(rs.getString("CityName"));
			city.setZipCode(rs.getString("ZipCode"));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
}
