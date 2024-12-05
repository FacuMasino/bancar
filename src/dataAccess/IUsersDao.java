package dataAccess;

import java.sql.SQLException;
import domainModel.User;

public interface IUsersDao
{
	public int create(User user) throws SQLException;
	public User read(int userId) throws SQLException;
	public User read(String username) throws SQLException;
	public boolean update(User user) throws SQLException;
	public int findUserId(User user) throws SQLException;
	public int findUserId(String username) throws SQLException;
	public int findUserId(int clientId) throws SQLException;
	public void handleId(User user) throws SQLException;
}
