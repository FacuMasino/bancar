package businessLogicImpl;

import java.sql.SQLException;

import businessLogic.IMovementsBusiness;
import businessLogic.ITransfersBusiness;
import dataAccessImpl.TransfersDao;
import domainModel.Account;
import domainModel.Client;
import domainModel.Movement;
import domainModel.MovementTypeEnum;
import exceptions.BusinessException;
import exceptions.SQLOperationException;

public class TransfersBusiness implements ITransfersBusiness
{
	private TransfersDao transfersDao;
	private MovementTypesBusiness movementTypesBusiness;
	private IMovementsBusiness movementsBusiness;
	private ClientsBusiness clientsBusiness;
	
	public TransfersBusiness()
	{
		transfersDao = new TransfersDao();
		movementTypesBusiness = new MovementTypesBusiness();
		movementsBusiness = new MovementsBusiness();
		clientsBusiness= new ClientsBusiness();
		
	}

	@Override
	public boolean create(Movement movement, Account originAccount, Account destinationAccount)
					throws BusinessException
	{
		movement.setTransactionId(movementsBusiness.generateTrxId());
		movement.setMovementType(movementTypesBusiness.read(MovementTypeEnum.TRANSFER.getId()));
		
		try
		{
			if (0 < transfersDao.create(movement, originAccount.getId(), destinationAccount.getId()))
			{
				//Acortar id de transacción
				String shortTrxId = movementsBusiness
						.getShortTrxId(movement.getTransactionId());
				movement.setTransactionId(shortTrxId);
				
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

		int destionationClientId= destinationAccount.getClientId();
		Client destinationClient = new Client();
		destinationClient = clientsBusiness.read(destionationClientId);
		
		//TODO: Evaluar si inactivamos las cuentas al dar de baja cliente, eso va a cambiar la validacion
		if (!destinationClient.getActiveStatus() && destinationAccount.getActiveStatus())
		{
			throw new BusinessException("El destinatario de la transferencia se encuentra dado de baja.");
		}
		if (!destinationAccount.getActiveStatus()) 
		{
			throw new BusinessException("La cuenta de destino se encuentra dada de baja.");
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
