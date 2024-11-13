package businessLogicImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import businessLogic.IProvincesBusiness;
import dataAccessImpl.ProvincesDao;
import domainModel.Province;
import exceptions.BusinessException;
import exceptions.SQLOperationException;

public class ProvincesBusiness implements IProvincesBusiness
{
	private ProvincesDao provincesDao;
	
	public ProvincesBusiness()
	{
		provincesDao = new ProvincesDao();
	}

	@Override
	public boolean create(Province province, int countryId) throws BusinessException
	{
		try
		{
			if (0 < provincesDao.create(province, countryId))
			{
				return true;
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al crear la provincia.");
		}

		return false;
	}

	@Override
	public Province read(int provinceId) throws BusinessException
	{
		try
		{
			return provincesDao.read(provinceId);			
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al leer la provincia.");
		}
	}

	@Override
	public boolean update(Province province) throws BusinessException
	{
		return false;
	}

	@Override
	public boolean delete(int provinceId) throws BusinessException
	{
		return false;
	}

	@Override
	public ArrayList<Province> list() throws BusinessException
	{
		try
		{
			return provincesDao.list();
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al obtener las provincias.");
		}
	}
}
