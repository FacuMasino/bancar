package businessLogic;

import java.util.ArrayList;
import domainModel.Province;
import exceptions.BusinessException;

public interface IProvincesBusiness
{
	public boolean create(Province province, int countryId) throws BusinessException;
	public Province read(int provinceId) throws BusinessException;
	public boolean update(Province province) throws BusinessException;
	public boolean delete(int provinceId) throws BusinessException;
	public ArrayList<Province> list() throws BusinessException;
}
