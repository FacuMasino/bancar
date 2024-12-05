package businessLogicImpl;

import businessLogic.IRolesBusiness;
import dataAccess.IRolesDao;
import dataAccessImpl.RolesDao;
import domainModel.Role;
import java.sql.SQLException;
import exceptions.BusinessException;
import exceptions.SQLOperationException;

public class RolesBusiness implements IRolesBusiness
{
	private IRolesDao rolesDao;
	
	public RolesBusiness()
	{
		rolesDao = new RolesDao();
	}
	
	@Override
	public Role read(int roleId) throws BusinessException 
	{
		try 
		{
			return rolesDao.read(roleId);
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al leer el rol.");
		}
	}
}
