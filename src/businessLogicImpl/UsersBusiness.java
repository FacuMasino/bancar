package businessLogicImpl;

import java.sql.SQLException;
import businessLogic.IUsersBusiness;
import dataAccessImpl.UsersDao;
import domainModel.User;
import exceptions.BusinessException;
import exceptions.SQLOperationException;

public class UsersBusiness implements IUsersBusiness
{
	private UsersDao usersDao;
	
	public UsersBusiness()
	{
		usersDao = new UsersDao();
	}
	
	public User validateCredentials(String username, String password) 
			throws BusinessException
	{
		try 
		{
			User auxUser = usersDao.read(username);

			if(auxUser != null && !auxUser.getPassword().equals(password))
			{
				throw new BusinessException("La contraseña ingresada es incorrecta");
			}

			return auxUser;
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (BusinessException ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al leer el usuario.");
		}
	}
}
