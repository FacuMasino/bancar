package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dataAccess.IProvincesDao;
import domainModel.Province;

public class ProvincesDao implements IProvincesDao
{
	private Database db;
	
	public ProvincesDao()
	{
		db = new Database();
	}

	@Override
	public int create(Province province, int countryId) throws SQLException
	{
		try
		{
			db.setCallableStatement("{CALL insert_province(?, ?, ?)}");
			setParameters(province, countryId, false);
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
	public Province read(int provinceId) throws SQLException
	{
		Province province = new Province();
		ResultSet rs;
		
		try
		{
			db.setPreparedStatement("Select * from Provinces where ProvinceId = ?;");
			db.getPreparedStatement().setInt(1, provinceId);
			rs = db.getPreparedStatement().executeQuery();
			
			if(!rs.next())
			{
				return null;
			}
			
			assignResultSet(province, rs);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		
		return province;
	}

	@Override
	public boolean update(Province province) throws SQLException
	{
		return false;
	}

	@Override
	public boolean delete(int provinceId) throws SQLException
	{
		return false;
	}

	@Override
	public ArrayList<Province> list() throws SQLException
	{
		ResultSet rs;
		ArrayList<Province> provinces = new ArrayList<Province>();
		
		try
		{
			db.setPreparedStatement("SELECT * FROM Provinces;");
			rs = db.getPreparedStatement().executeQuery();
			
			while(rs.next())
			{
				Province province = new Province();
				assignResultSet(province, rs);
				provinces.add(province);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return provinces;
	}
	
	public int getId(Province province, int countryId) throws SQLException
	{
		return getId(province.getName(), countryId);
	}
	
	public int getId(String provinceName, int countryId) throws SQLException
	{
		ResultSet rs;
		
		try
		{
			db.setPreparedStatement("SELECT ProvinceId FROM Provinces WHERE ProvinceName = ? AND CountryId = ?;");
			db.getPreparedStatement().setString(1, provinceName);
			db.getPreparedStatement().setInt(2, countryId);
			rs = db.getPreparedStatement().executeQuery();
			
			if(!rs.next())
			{
				return 0;
			}
			
			return rs.getInt("ProvinceId");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
	
	public int getId(int cityId) throws SQLException
	{
		ResultSet rs;
		
		try
		{
			db.setPreparedStatement("SELECT ProvinceId FROM Cities WHERE CityId = ?;");
			db.getPreparedStatement().setInt(1, cityId);
			rs = db.getPreparedStatement().executeQuery();
			
			if(!rs.next())
			{
				return 0;
			}
			
			return rs.getInt("ProvinceId");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
	
	public void handleId(Province province, int countryId) throws SQLException
	{
		try
		{
			if (province != null)
		    {
		        int foundId = getId(province, countryId);

		        if (foundId == 0)
		        {
		        	province.setId(create(province, countryId));
		        }
		        else if (foundId == province.getId())
		        {
		            update(province);
		        }
		        else
		        {
		        	province.setId(foundId);
		        }
		    }
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
	
	private void setParameters(Province province, int countryId, boolean isUpdate) throws SQLException
	{
		if (isUpdate)
		{
			db.getCallableStatement().setInt(1, province.getId());
		}
		else
		{
			db.getCallableStatement().registerOutParameter(1, java.sql.Types.INTEGER);
		}

		db.getCallableStatement().setString(2, province.getName());
		db.getCallableStatement().setInt(3, countryId);
	}
	
	private void assignResultSet(Province province, ResultSet rs) throws SQLException
	{
		try
		{
			province.setId(rs.getInt("ProvinceId"));
			province.setName(rs.getString("ProvinceName"));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
}
