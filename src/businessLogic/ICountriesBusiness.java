package businessLogic;

import domainModel.Country;
import exceptions.BusinessException;

public interface ICountriesBusiness
{
	public Country read(int countryId) throws BusinessException;
}
