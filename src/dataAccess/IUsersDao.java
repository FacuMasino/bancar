package dataAccess;

import java.sql.SQLException;
import java.util.ArrayList;
import domainModel.User;

public interface IUsersDao
{
	public int create(User user) throws SQLException;
	public User read(int userId) throws SQLException;
	public boolean update(User user) throws SQLException;
	public boolean delete(int userId) throws SQLException;
	public ArrayList<User> list() throws SQLException;
	public User read(String username) throws SQLException;
}
