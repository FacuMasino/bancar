package businessLogicImpl;

import java.sql.SQLException;

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

}
