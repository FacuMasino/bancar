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
			db.setPreparedStatement("SELECT * FROM Users WHERE UserId = ?");
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
	public User read(String username) throws SQLException
	{
		return read(getUserId(username));
	}
	
	@Override
	public boolean update(User user) throws SQLException
	{
		return false;
	}

	@Override
	public boolean toggleActiveStatus(int userId, boolean currentActiveStatus) throws SQLException
	{
		int rows = 0;
		
		try
		{
			db.setPreparedStatement("UPDATE Users SET ActiveStatus = ? WHERE UserId = ?;");
			db.getPreparedStatement().setBoolean(1, !currentActiveStatus);
			db.getPreparedStatement().setInt(2, userId);
			rows = db.getPreparedStatement().executeUpdate();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		
		return (rows > 0);
	}
	
	public int getUserId(User user) throws SQLException
	{
		return getUserId(user.getUsername());
	}
	
	public int getUserId(String username) throws SQLException
	{
		ResultSet rs;
		
		try
		{
			db.setPreparedStatement("SELECT UserId FROM Users WHERE Username = ?;");
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
	
	public int getUserId(int clientId) throws SQLException
	{
		ResultSet rs;
		
		try
		{
			db.setPreparedStatement("SELECT UserId FROM Clients WHERE ClientId = ?;");
			db.getPreparedStatement().setInt(1, clientId);
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

		db.getCallableStatement().setString(2, user.getUsername());
		db.getCallableStatement().setString(3, user.getPassword());
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
