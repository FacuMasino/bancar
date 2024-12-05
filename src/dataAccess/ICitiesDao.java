package dataAccess;

import java.sql.SQLException;
import domainModel.City;

public interface ICitiesDao
{
	public int getProvinceId();
	public void setProvinceId(int provinceId);
	public int create(City city, int provinceId) throws SQLException;
	public City read(int cityId) throws SQLException;
	public boolean update(City city, int provinceId) throws SQLException;
	public int findId(City city, int provinceId) throws SQLException;
	public int findId(String cityName, int provinceId) throws SQLException;
	public void handleId(City city, int provinceId) throws SQLException;
}
