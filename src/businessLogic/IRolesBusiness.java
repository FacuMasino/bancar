package businessLogic;

import exceptions.BusinessException;
import domainModel.Role;

public interface IRolesBusiness
{
	public Role read(int roleId) throws BusinessException;
}
