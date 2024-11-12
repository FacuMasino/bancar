package businessLogic;

import java.util.ArrayList;
import domainModel.Country;
import exceptions.BusinessException;

public interface ICountriesBusiness
{
	public boolean create(Country country) throws BusinessException;
	public Country read(int countryId) throws BusinessException;
	public boolean update(Country country) throws BusinessException;
	public boolean delete(int countryId) throws BusinessException;
	public ArrayList<Country> list() throws BusinessException;
}
