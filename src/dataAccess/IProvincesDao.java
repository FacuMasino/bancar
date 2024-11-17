package dataAccess;

import java.sql.SQLException;
import java.util.ArrayList;
import domainModel.Province;

public interface IProvincesDao
{
	public int create(Province province, int countryId) throws SQLException;
	public Province read(int provinceId) throws SQLException;
	public boolean update(Province province, int countryId) throws SQLException;
	public ArrayList<Province> list() throws SQLException;
}
