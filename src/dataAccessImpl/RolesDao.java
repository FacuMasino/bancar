package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import dataAccess.IRolesDao;
import domainModel.Role;

public class RolesDao implements IRolesDao
{
	private Database db;

	public RolesDao()
	{
		db = new Database();
	}
	
	@Override
	public Role read(int roleId) throws SQLException
	{
		Role role = new Role();
		ResultSet rs;
		
		try
		{
			db.setPreparedStatement("Select * from Roles where RoleId = ?");
			db.getPreparedStatement().setInt(1, roleId);
			rs = db.getPreparedStatement().executeQuery();
			
			if(!rs.next())
			{
				return null;
			}
			
			assignResultSet(role, rs);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		
		return role;
	}
	
	private void assignResultSet(Role role, ResultSet rs) throws SQLException
	{
		try
		{
			role.setId(rs.getInt("RoleId"));
			role.setName(rs.getString("RoleName"));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
}
