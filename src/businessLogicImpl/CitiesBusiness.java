package businessLogicImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import businessLogic.ICitiesBusiness;
import dataAccessImpl.CitiesDao;
import domainModel.City;
import exceptions.BusinessException;
import exceptions.SQLOperationException;

public class CitiesBusiness implements ICitiesBusiness
{
	private CitiesDao citiesDao;
	
	public CitiesBusiness()
	{
		citiesDao = new CitiesDao();
	}

	@Override
	public boolean create(City city, int provinceId) throws BusinessException
	{
		try
		{
			if (0 < citiesDao.create(city, provinceId))
			{
				return true;
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al crear la localidad.");
		}

		return false;
	}

	@Override
	public City read(int cityId) throws BusinessException
	{
		try
		{
			return citiesDao.read(cityId);			
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al leer la localidad.");
		}
	}

	@Override
	public boolean update(City city) throws BusinessException
	{
		return false;
	}

	@Override
	public boolean delete(int cityId) throws BusinessException
	{
		return false;
	}

	@Override
	public ArrayList<City> list() throws BusinessException
	{
		return null;
	}
}
