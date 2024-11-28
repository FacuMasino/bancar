package businessLogicImpl;

import java.sql.SQLException;
import businessLogic.ITransfersBusiness;
import dataAccessImpl.TransfersDao;
import domainModel.Account;
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
	public boolean create(Movement movement, Account originAccount, Account destinationAccount)
					throws BusinessException
	{
		movement.setMovementType(movementTypesBusiness.read(MovementTypeEnum.TRANSFER.getId()));
		
		try
		{
			if (0 < transfersDao.create(movement, originAccount.getId(), destinationAccount.getId()))
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
					"Ocurrió un error desconocido al realizar la transferencia.");
		}
		
		return false;
	}
	
	public void validate(Movement movement, Account originAccount, Account destinationAccount)
					throws BusinessException
	{
		if (destinationAccount == null)
		{
			throw new BusinessException("La cuenta de destino no existe.");
		}

		if (movement.getAmount().doubleValue() <= 0)
		{
			throw new BusinessException("El monto debe ser mayor a cero.");
		}
		
		if (originAccount.getBalance().doubleValue() < movement.getAmount().doubleValue())
		{
			throw new BusinessException("El saldo en la cuenta de origen no es suficiente.");
		}
	}
}
