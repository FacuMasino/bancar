package businessLogic;

import java.util.ArrayList;

import domainModel.MovementType;
import exceptions.BusinessException;

public interface IMovementTypesBusiness
{
	public MovementType read(int movementTypeId) throws BusinessException;
	public ArrayList<MovementType> list() throws BusinessException;
}
