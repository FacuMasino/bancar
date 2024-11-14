package dataAccess;

import java.sql.SQLException;
import domainModel.Country;

public interface ICountriesDao
{
	public int create(Country country) throws SQLException;
	public Country read(int countryId) throws SQLException;
	public boolean update(Country country) throws SQLException;
}
