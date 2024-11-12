package dataAccess;

import java.sql.SQLException;
import java.util.ArrayList;
import domainModel.Country;

public interface ICountriesDao
{
	public int create(Country country) throws SQLException;
	public Country read(int countryId) throws SQLException;
	public boolean update(Country country) throws SQLException;
	public boolean delete(int countryId) throws SQLException;
	public ArrayList<Country> list() throws SQLException;
}
