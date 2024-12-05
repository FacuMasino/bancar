package dataAccess;

import java.sql.SQLException;
import java.util.ArrayList;
import domainModel.Province;

public interface IProvincesDao
{
	public int getCountryId();
	public void setCountryId(int countryId)
	public int create(Province province, int countryId) throws SQLException;
	public Province read(int provinceId) throws SQLException;
	public boolean update(Province province, int countryId) throws SQLException;
	public ArrayList<Province> list() throws SQLException;
	public int findId(Province province, int countryId) throws SQLException;
	public int findId(String provinceName, int countryId) throws SQLException;
	public int findId(int cityId) throws SQLException;
	public void handleId(Province province, int countryId) throws SQLException;
}
