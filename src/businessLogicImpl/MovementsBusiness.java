package businessLogicImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.UUID;
import businessLogic.IMovementsBusiness;
import dataAccess.IMovementsDao;
import dataAccessImpl.MovementsDao;
import domainModel.Movement;
import exceptions.BusinessException;
import exceptions.SQLOperationException;

public class MovementsBusiness implements IMovementsBusiness
{
	private IMovementsDao movementsDao;

	public MovementsBusiness()
	{
		movementsDao = new MovementsDao();
	}

	@Override
	public boolean create(Movement movement)
			throws BusinessException
	{
		try
		{
			// UUID de transacción
			movement.setTransactionId(generateTrxId());
			
			int newMovementId = movementsDao.create(movement);

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
			ArrayList<Movement> movements = movementsDao.list(accountId);
			
			return movements;
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
	
	// Para obtener 2 movimientos con el mismo TransactionId (Transferencias)
	// TODO: No es lo más óptimo, si llegaramos debería haber una tabla de Transferencias
	@Override
	public ArrayList<Movement> list(String transactionId) throws BusinessException
	{
		try
		{
			ArrayList<Movement> movements = movementsDao.list(transactionId);
			return movements;
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
	public ArrayList<Movement> filterByDate(
			ArrayList<Movement> movements, String filterDate)
					throws BusinessException
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
	public ArrayList<Movement> search(
			int accountId, ArrayList<Movement> movements, String searchInput)
					throws BusinessException
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

	// Función para vincular 2 movimientos entre sí
	// Genera una cadena de 36 caracteres (32 más 4 guiones)
	// El resultado es un Identificador único, que dificilmente se vaya a
	// repetir [+ info acá -> https://dub.sh/ersuXW3]
	@Override
    public String generateTrxId()
    {
        UUID uuid = UUID.randomUUID();
        return uuid.toString(); // Ejemplo: 123e4567-e89b-12d3-a456-426614174000
    }
}