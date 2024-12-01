package businessLogicImpl;

import java.sql.SQLException;
import java.util.ArrayList;

import businessLogic.IMovementTypesBusiness;
import dataAccessImpl.MovementTypesDao;
import domainModel.MovementType;
import exceptions.BusinessException;
import exceptions.SQLOperationException;

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
	
	@Override
	public ArrayList<MovementType> list() throws BusinessException
	{
		try
		{
			return movementTypesDao.list();
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al obtener los tipos de movimiento.");
		}
	}
}