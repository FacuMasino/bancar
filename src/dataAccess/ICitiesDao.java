package dataAccess;

import java.sql.SQLException;
import domainModel.City;

public interface ICitiesDao
{
	public int create(City city, int provinceId) throws SQLException;
	public City read(int cityId) throws SQLException;
	public boolean update(City city) throws SQLException;
}
