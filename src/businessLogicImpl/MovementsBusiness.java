package businessLogicImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import businessLogic.IMovementsBusiness;
import dataAccessImpl.MovementsDao;
import domainModel.Movement;
import exceptions.BusinessException;
import exceptions.SQLOperationException;

public class MovementsBusiness implements IMovementsBusiness
{
	private MovementsDao movementsDao;
	
	public MovementsBusiness()
	{
		movementsDao = new MovementsDao();
	}

	@Override
	public boolean create(Movement movement, int accountId) throws BusinessException
	{
		try
		{
			if (0 < movementsDao.create(movement, accountId))
			{
				return true;
			}
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido al crear el movimiento.");
		}
		
		return false;
	}

	@Override
	public Movement read(int movementId) throws BusinessException
	{
		try 
		{
			return movementsDao.read(movementId);
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al leer el movimiento.");
		}
	}

	@Override
	public ArrayList<Movement> list(int accountId) throws BusinessException
	{
		try
		{
			return movementsDao.list(accountId);
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException("Ocurrió un error desconocido al leer los movimientos.");
		}
	}
	
	// TODO: Eliminar este método y reemplazar todos sus llamados por list(int accountId) de los servlets
	// (Doble click en el nombre del método y click en Open Call Hierarchy para ver llamados)
	public ArrayList<Movement> listByIdAccount(int accountId) throws BusinessException
	{
		return list(accountId);
	}
}
