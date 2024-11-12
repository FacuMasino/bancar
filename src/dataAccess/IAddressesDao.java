package dataAccess;

import java.sql.SQLException;
import java.util.ArrayList;
import domainModel.Address;

public interface IAddressesDao
{
	public int create(Address address) throws SQLException;
	public Address read(int addressId) throws SQLException;
	public boolean update(Address address) throws SQLException;
	public boolean delete(int addressId) throws SQLException;
	public ArrayList<Address> list() throws SQLException;
}
