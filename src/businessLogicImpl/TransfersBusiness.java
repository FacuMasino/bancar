package businessLogicImpl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import businessLogic.ITransfersBusiness;
import dataAccessImpl.TransfersDao;
import domainModel.Movement;
import domainModel.MovementTypeEnum;
import exceptions.BusinessException;
import exceptions.SQLOperationException;

public class TransfersBusiness implements ITransfersBusiness
{
	private TransfersDao transfersDao;
	private MovementTypesBusiness movementTypesBusiness;

	public TransfersBusiness()
	{
		transfersDao = new TransfersDao();
		movementTypesBusiness = new MovementTypesBusiness();
	}

	@Override
	public boolean create(
			Movement movement,
			int originAccountId,
			int destinationAccountId)
					throws BusinessException
	{
		movement.setDateTime(LocalDateTime.now());
		movement.setMovementType(
				movementTypesBusiness.read(
						MovementTypeEnum.TRANSFER.getId()));
		
		try
		{
			return transfersDao.create(movement, originAccountId, destinationAccountId);
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al crear el préstamo.");
		}
	}
}
