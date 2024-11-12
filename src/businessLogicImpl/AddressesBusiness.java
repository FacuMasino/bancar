package businessLogicImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import businessLogic.IAddressesBusiness;
import dataAccessImpl.AddressesDao;
import domainModel.Address;
import exceptions.BusinessException;
import exceptions.SQLOperationException;

public class AddressesBusiness implements IAddressesBusiness
{
	private AddressesDao addressesDao;
	
	public AddressesBusiness()
	{
		addressesDao = new AddressesDao();
	}

	@Override
	public boolean create(Address address) throws BusinessException
	{
		try
		{
			if (0 < addressesDao.create(address))
			{
				return true;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public Address read(int addressId) throws BusinessException
	{
		try
		{
			return addressesDao.read(addressId);			
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al leer el domicilio.");
		}
	}

	@Override
	public boolean update(Address address) throws BusinessException
	{
		return false;
	}

	@Override
	public boolean delete(int addressId) throws BusinessException
	{
		return false;
	}

	@Override
	public ArrayList<Address> list() throws BusinessException
	{
		return null;
	}
}
