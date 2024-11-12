package businessLogic;

import java.util.ArrayList;
import domainModel.Address;
import exceptions.BusinessException;

public interface IAddressesBusiness
{
	public boolean create(Address address) throws BusinessException;
	public Address read(int addressId) throws BusinessException;
	public boolean update(Address address) throws BusinessException;
	public boolean delete(int addressId) throws BusinessException;
	public ArrayList<Address> list() throws BusinessException;
}
