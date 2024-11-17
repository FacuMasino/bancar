package businessLogic;

import java.util.ArrayList;
import domainModel.Province;
import exceptions.BusinessException;

public interface IProvincesBusiness
{
	Province read(int provinceId) throws BusinessException;
	public ArrayList<Province> list() throws BusinessException;
}
