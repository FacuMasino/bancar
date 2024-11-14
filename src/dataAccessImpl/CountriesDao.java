package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import dataAccess.ICountriesDao;
import domainModel.Country;

public class CountriesDao implements ICountriesDao
{
	private Database db;
	
	public CountriesDao()
	{
		db = new Database();
	}

	@Override
	public int create(Country country) throws SQLException
	{
		try
		{
			db.setCallableStatement("{CALL insert_country(?, ?)}");
			setParameters(country, false);
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
	public Country read(int countryId) throws SQLException
	{
		Country country = new Country();
		ResultSet rs;
		
		try
		{
			db.setPreparedStatement("Select * from Countries where CountryId = ?;");
			db.getPreparedStatement().setInt(1, countryId);
			rs = db.getPreparedStatement().executeQuery();
			
			if(!rs.next())
			{
				return null;
			}
			
			assignResultSet(country, rs);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		
		return country;
	}

	@Override
	public boolean update(Country country) throws SQLException
	{
		return false;
	}
	
	public int getId(Country country) throws SQLException
	{
		return getId(country.getName());
	}
	
	public int getId(String countryName) throws SQLException
	{
		ResultSet rs;
		
		try
		{
			db.setPreparedStatement("SELECT CountryId FROM Countries WHERE CountryName = ?;");
			db.getPreparedStatement().setString(1, countryName);
			rs = db.getPreparedStatement().executeQuery();
			
			if(!rs.next())
			{
				return 0;
			}
			
			return rs.getInt("CountryId");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
	
	public int getId(int provinceId) throws SQLException
	{
		ResultSet rs;
		
		try
		{
			db.setPreparedStatement("SELECT CountryId FROM Provinces WHERE ProvinceId = ?;");
			db.getPreparedStatement().setInt(1, provinceId);
			rs = db.getPreparedStatement().executeQuery();
			
			if(!rs.next())
			{
				return 0;
			}
			
			return rs.getInt("CountryId");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
	
	public void handleId(Country country) throws SQLException
	{
		try
		{
			if (country != null)
		    {
		        int foundId = getId(country);

		        if (foundId == 0)
		        {
		            country.setId(create(country));
		        }
		        else if (foundId == country.getId())
		        {
		            update(country);
		        }
		        else
		        {
		        	country.setId(foundId);
		        }
		    }
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
	
	private void setParameters(Country country, boolean isUpdate) throws SQLException
	{
		if (isUpdate)
		{
			db.getCallableStatement().setInt(1, country.getId());
		}
		else
		{
			db.getCallableStatement().registerOutParameter(1, java.sql.Types.INTEGER);
		}

		db.getCallableStatement().setString(2, country.getName());
	}
	
	private void assignResultSet(Country country, ResultSet rs) throws SQLException
	{
		try
		{
			country.setId(rs.getInt("CountryId"));
			country.setName(rs.getString("CountryName"));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
}
