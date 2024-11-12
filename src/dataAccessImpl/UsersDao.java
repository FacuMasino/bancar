package dataAccessImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import dataAccess.IUsersDao;
import domainModel.User;

public class UsersDao implements IUsersDao
{
	@Override
	public int create(User user) throws SQLException
	{
		return 0;
	}

	@Override
	public User read(int userId) throws SQLException
	{
		return null;
	}

	@Override
	public boolean update(User user) throws SQLException
	{
		return false;
	}

	@Override
	public boolean delete(int userId) throws SQLException
	{
		return false;
	}

	@Override
	public ArrayList<User> list() throws SQLException
	{
		return null;
	}
}
