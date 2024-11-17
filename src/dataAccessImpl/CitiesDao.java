package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import dataAccess.ICitiesDao;
import domainModel.City;

public class CitiesDao extends Dao<City> implements ICitiesDao
{	
	private int provinceId;

	public CitiesDao()
	{
		
	}
	
	public int getProvinceId()
	{
		return provinceId;
	}


	public void setProvinceId(int provinceId)
	{
		this.provinceId = provinceId;
	}

	@Override
	protected int create(City city) throws SQLException
	{
		return create(city, getProvinceId());
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
	protected boolean update(City city) throws SQLException
	{
		return update(city, getProvinceId());
	}
	
	@Override
	public boolean update(City city, int provinceId) throws SQLException
	{
		int rows = 0;
		
		try
		{
			db.setCallableStatement("{CALL update_city(?, ?, ?, ?)}");
			setParameters(city, provinceId, true);
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
	protected int findId(City city) throws SQLException
	{
		return findId(city, getProvinceId());
	}
	
	public int findId(City city, int provinceId) throws SQLException
	{
		return findId(city.getName(), provinceId);
	}
	
	public int findId(String cityName, int provinceId) throws SQLException
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
		setProvinceId(provinceId);
		super.handleId(city);
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
