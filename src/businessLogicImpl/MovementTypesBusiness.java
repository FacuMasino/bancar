package businessLogicImpl;

import java.sql.SQLException;
import businessLogic.IMovementTypesBusiness;
import dataAccessImpl.MovementTypesDao;
import domainModel.MovementType;
import exceptions.BusinessException;

public class MovementTypesBusiness implements IMovementTypesBusiness
{
	private MovementTypesDao movementTypesDao;
	
	public MovementTypesBusiness()
	{
		movementTypesDao = new MovementTypesDao();
	}

	@Override
	public MovementType read(int movementTypeId) throws BusinessException
	{
		try
		{
			return movementTypesDao.read(movementTypeId);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido al leer el tipo de movimiento.");
		}
		
		return null;
	}
}
