package businessLogicImpl;

import java.util.ArrayList;
import businessLogic.IUsersBusiness;
import domainModel.User;
import exceptions.BusinessException;

public class UsersBusiness implements IUsersBusiness
{
	@Override
	public boolean create(User user) throws BusinessException
	{
		return false;
	}

	@Override
	public User read(int userId) throws BusinessException
	{
		return null;
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
}
