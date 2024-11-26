package businessLogic;

import domainModel.Movement;
import exceptions.BusinessException;

public interface ITransfersBusiness
{
	public boolean create(
			Movement movement,
			int originAccountId,
			int destinationAccountId)
					throws BusinessException;
}
