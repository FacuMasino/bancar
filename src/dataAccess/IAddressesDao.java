package dataAccess;

import java.sql.SQLException;
import domainModel.Address;

public interface IAddressesDao
{
	public int create(Address address) throws SQLException;
	public Address read(int addressId) throws SQLException;
	public boolean update(Address address) throws SQLException;
}
