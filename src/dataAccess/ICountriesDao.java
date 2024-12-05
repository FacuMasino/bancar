package dataAccess;

import java.sql.SQLException;
import domainModel.Country;

public interface ICountriesDao
{
	public int create(Country country) throws SQLException;
	public Country read(int countryId) throws SQLException;
	public boolean update(Country country) throws SQLException;
	public int findId(Country country) throws SQLException;
	public int findId(String countryName) throws SQLException;
	public int findId(int provinceId) throws SQLException;
	public void handleId(Country country) throws SQLException;
}
