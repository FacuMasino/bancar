package businessLogicImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
	public boolean create(Movement movement, int accountId)
			throws BusinessException
	{
		try
		{
			int newMovementId = movementsDao.create(movement, accountId);
			if (0 < newMovementId)
			{
				movement.setId(newMovementId);
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
			throw new BusinessException(
					"Ocurrió un error desconocido al leer el movimiento.");
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
			throw new BusinessException(
					"Ocurrió un error desconocido al leer los movimientos.");
		}
	}

	@Override
	public ArrayList<Movement> list(int accountId, int movTypeId)
			throws BusinessException
	{
		try
		{
			return movementsDao.list(accountId, movTypeId);
		} 
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido al filtar por tipo de movimiento.");
		}
	}

	@Override
	public ArrayList<Movement> filterByDate(ArrayList<Movement> movements,
			String filterDate) throws BusinessException
	{
		try
		{		
			LocalDate filterDateLocal = LocalDate.parse(filterDate);
			
			ArrayList<Movement> filteredMovements = new ArrayList<>();
			
			for (Movement movement : movements)
			{
				
				LocalDate movementDate = movement.getDateTime().toLocalDate();
				
				if (movementDate.equals(filterDateLocal))
				{
					filteredMovements.add(movement);
				}
			}
			
			return filteredMovements;
		}
		catch (DateTimeParseException ex)
		{
			throw new BusinessException(
					"El formato de fecha provisto es inválido.");
		}
	}
	
	@Override
	public ArrayList<Movement> search(int accountId, ArrayList<Movement> movements,
			String searchInput) throws BusinessException
	{
		try
		{
			return movementsDao.search(accountId, movements, searchInput);
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido al realizar la búsqueda.");
		}
	}

	// TODO: Eliminar este método y reemplazar todos sus llamados por list(int
	// accountId) de los servlets
	// (Doble click en el nombre del método y click en Open Call Hierarchy para
	// ver llamados)
	public ArrayList<Movement> listByIdAccount(int accountId)
			throws BusinessException
	{
		return list(accountId);
	}
}