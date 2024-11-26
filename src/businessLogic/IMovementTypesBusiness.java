package businessLogic;

import domainModel.MovementType;
import exceptions.BusinessException;

public interface IMovementTypesBusiness
{
	public MovementType read(int movementTypeId) throws BusinessException;
}
