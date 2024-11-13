package dataAccess;

import java.sql.SQLException;
import domainModel.Role;

public interface IRolesDao
{
	public Role read(int roleId) throws SQLException;
}
