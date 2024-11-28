package businessLogic;

import domainModel.Account;
import domainModel.Movement;
import exceptions.BusinessException;

public interface ITransfersBusiness
{
	public boolean create(Movement movement, Account originAccount, Account destinationAccount) throws BusinessException;
}
