package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dataAccess.IUsersDao;
import domainModel.User;

public class UsersDao implements IUsersDao
{
	private Database db;

	public UsersDao()
	{
		db = new Database();
	}

	@Override
	public int create(User user) throws SQLException
	{
		try
		{
			db.setCallableStatement("{CALL insert_user(?, ?, ?)}");
			setParameters(user, false);
			db.getCallableStatement().executeUpdate();
			return db.getCallableStatement().getInt(1);
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public User read(int userId) throws SQLException
	{
		User user = new User();
		ResultSet rs;
		
		try
		{
			db.setPreparedStatement("Select * from Users where UserId = ?");
			db.getPreparedStatement().setInt(1, userId);
			rs = db.getPreparedStatement().executeQuery();
			
			if(!rs.next())
			{
				return null;
			}
			
			assignResultSet(user, rs);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		
		return user;
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
	
	private void setParameters(User user, boolean isUpdate) throws SQLException
	{
		if (isUpdate)
		{
			db.getCallableStatement().setInt(1, user.getUserId());
		}
		else
		{
			db.getCallableStatement().registerOutParameter(1, java.sql.Types.INTEGER);
		}

		db.getPreparedStatement().setString(2, user.getUsername());
		db.getPreparedStatement().setString(3, user.getPassword());
	}
	
	private void assignResultSet(User user, ResultSet rs) throws SQLException
	{
		try
		{
			user.setUserId(rs.getInt("UserId"));
			user.setUsername(rs.getString("Username"));
			user.setPassword(rs.getString("UserPassword"));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
}
