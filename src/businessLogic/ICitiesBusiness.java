package businessLogic;

import java.util.ArrayList;
import domainModel.City;
import exceptions.BusinessException;

public interface ICitiesBusiness
{
	public boolean create(City city, int provinceId) throws BusinessException;
	public City read(int cityId) throws BusinessException;
	public boolean update(City city) throws BusinessException;
	public boolean delete(int cityId) throws BusinessException;
	public ArrayList<City> list() throws BusinessException;
}
