package businessLogic;

import java.util.ArrayList;
import domainModel.User;
import exceptions.BusinessException;

public interface IUsersBusiness
{
	public boolean create(User user) throws BusinessException;
	public User read(int userId) throws BusinessException;
	public boolean update(User user) throws BusinessException;
	public boolean delete(int userId) throws BusinessException;
	public ArrayList<User> list() throws BusinessException;
	public User validateCredentials(String username, String password) throws BusinessException;
}
