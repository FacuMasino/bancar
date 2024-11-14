package businessLogic;

import domainModel.User;
import exceptions.BusinessException;

public interface IUsersBusiness
{
	public User validateCredentials(String username, String password) throws BusinessException;
}
