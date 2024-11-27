package businessLogicImpl;

import java.sql.SQLException;
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
		movement.setMovementType(
				movementTypesBusiness.read(
						MovementTypeEnum.TRANSFER.getId()));
		
		try
		{
			if (0 < transfersDao.create(movement, originAccountId, destinationAccountId))
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
			throw new BusinessException
				("Ocurrió un error desconocido al realizar la transferencia.");
		}
		
		return false;
	}
}
