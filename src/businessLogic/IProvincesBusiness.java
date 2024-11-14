package businessLogic;

import java.util.ArrayList;
import domainModel.Province;
import exceptions.BusinessException;

public interface IProvincesBusiness
{
	public ArrayList<Province> list() throws BusinessException;
}
