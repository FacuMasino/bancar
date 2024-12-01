package businessLogicImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.UUID;

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

	// [EN DESARROLLO] Opcion 1
	// (Importante usar CHAR(36) en la columna de la DB)
	// Posible función para vincular 2 movimientos entre sí
	// Genera una cadena de 36 caracteres (32 más 4 guiones)
    public String generateTrxId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();  // Ejemplo: 123e4567-e89b-12d3-a456-426614174000
    }
    
    // [EN DESARROLLO] Opcion 2
    // (Importante usar CHAR(64) en la columna de DB)
    // Esta función utiliza el algoritmo SHA-256 para codificar
    // una salida única en base a cierta entrada.
    // Esa es la principal diff con la anterior, da la posibilidad
    // de verificar que un nro de trx pertenezca a una transferencia.
    // Ya que si se vuelve a generar utilizando los mismos datos, la salida
    // debería ser idéntica.
    // Ejemplo de uso:
    // String trxId = generateTransactionId("originAccouontId", "destinationAccountId", "2024-11-30T12:00:00");
    String generateTrxId(String... details) 
    		throws BusinessException 
    {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String input = String.join("-", details);
            byte[] hashBytes = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
        	ex.printStackTrace();
            throw new BusinessException("Error al generar el número de transacción.");
        }
    }
    
    // [EN DESARROLLO]
    // En ambos casos para mostrar el nro de transacción al usuario
    // Deberíamos acortarlo ya que es largo (Pero se guarda el original en DB)
    public String getShortTrxId(String fullTransactionId) {
        // Devuelve los últimos 10 caracteres
        return fullTransactionId.substring(fullTransactionId.length() - 10);
    }
}