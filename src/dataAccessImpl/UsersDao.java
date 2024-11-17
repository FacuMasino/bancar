package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import dataAccess.IUsersDao;
import domainModel.User;

public class UsersDao extends Dao<User> implements IUsersDao
{
	private RolesDao rolesDao;

	public UsersDao()
	{
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
		return read(findUserId(username));
	}
	
	@Override
	public boolean update(User user) throws SQLException
	{
		return false;
	}
	
	@Override
	protected int findId(User user) throws SQLException
	{
		return findUserId(user);
	}
	
	public int findUserId(User user) throws SQLException
	{
		return findUserId(user.getUsername());
	}
	
	public int findUserId(String username) throws SQLException
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
	
	public int findUserId(int clientId) throws SQLException
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
