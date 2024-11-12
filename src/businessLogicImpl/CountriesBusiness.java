package businessLogicImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import businessLogic.ICountriesBusiness;
import dataAccessImpl.CountriesDao;
import domainModel.Country;
import exceptions.BusinessException;
import exceptions.SQLOperationException;

public class CountriesBusiness implements ICountriesBusiness
{
	private CountriesDao countriesDao;
	
	public CountriesBusiness()
	{
		countriesDao = new CountriesDao();
	}

	@Override
	public boolean create(Country country) throws BusinessException
	{
		try
		{
			if (0 < countriesDao.create(country))
			{
				return true;
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al crear el país.");
		}

		return false;
	}

	@Override
	public Country read(int countryId) throws BusinessException
	{
		try
		{
			return countriesDao.read(countryId);			
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al leer el país.");
		}
	}

	@Override
	public boolean update(Country country) throws BusinessException
	{
		return false;
	}

	@Override
	public boolean delete(int countryId) throws BusinessException
	{
		return false;
	}

	@Override
	public ArrayList<Country> list() throws BusinessException
	{
		return null;
	}
}
