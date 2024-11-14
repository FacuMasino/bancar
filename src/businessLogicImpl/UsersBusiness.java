package businessLogicImpl;

import java.sql.SQLException;
import java.util.ArrayList;
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

	@Override
	public boolean create(User user) throws BusinessException
	{
		try
		{
			if (0 < usersDao.create(user))
			{
				return true;
			}
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al crear el usuario.");
		}
		
		return false;
	}

	@Override
	public User read(int userId) throws BusinessException
	{
		try 
		{
			return usersDao.read(userId);		
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al leer el usuario.");
		}
	}

	@Override
	public boolean update(User user) throws BusinessException
	{
		return false;
	}

	@Override
	public boolean delete(int userId) throws BusinessException
	{
		return false;
	}

	@Override
	public ArrayList<User> list() throws BusinessException
	{
		return null;
	}
	
	public User validateCredentials(String username, String password) 
			throws BusinessException
	{
		try 
		{
			User auxUser = usersDao.read(username);
			if(!auxUser.getPassword().equals(password))
			{
				throw new BusinessException("La contraseña ingresada es incorrecta");
			}
			return auxUser;
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (BusinessException ex) {
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
