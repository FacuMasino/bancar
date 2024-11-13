package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dataAccess.IUsersDao;
import domainModel.User;

public class UsersDao implements IUsersDao
{
	private Database db;
	private RolesDao rolesDao;

	public UsersDao()
	{
		db = new Database();
		rolesDao = new RolesDao();
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
	
	public int getUserId(User user) throws SQLException
	{
		return getUserId(user.getPassword());
	}
	
	public int getUserId(String username) throws SQLException
	{
		ResultSet rs;
		
		try
		{
			db.setPreparedStatement("SELECT UserId FROM Useres WHERE Username= ?;");
			db.getPreparedStatement().setString(1, username);
			rs = db.getPreparedStatement().executeQuery();
			
			if(!rs.next())
			{
				return 0;
			}
			
			return rs.getInt("UserId");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
	
	public void handleId(User user) throws SQLException
	{
		try
		{
			if (user != null)
		    {
		        int foundId = getUserId(user);

		        if (foundId == 0)
		        {
		        	user.setUserId(create(user));
		        }
		        else if (foundId == user.getUserId())
		        {
		            update(user);
		        }
		        else
		        {
		        	user.setUserId(foundId);
		        }
		    }
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
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
			
			int roleId = rs.getInt("RoleId");
			user.setRole(rolesDao.read(roleId));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
}
