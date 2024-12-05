package dataAccess;

import java.sql.SQLException;
import domainModel.Address;

public interface IAddressesDao
{
	public int create(Address address) throws SQLException;
	public Address read(int addressId) throws SQLException;
	public boolean update(Address address) throws SQLException;
	public int findId(Address address) throws SQLException;
	public int findId(String streetName, String streetNumber, String flat, int cityId) throws SQLException;
	public void handleId(Address address) throws SQLException;
}
