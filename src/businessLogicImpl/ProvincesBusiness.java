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
