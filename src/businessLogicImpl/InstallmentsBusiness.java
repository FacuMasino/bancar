package businessLogicImpl;

import java.sql.SQLException;

import businessLogic.IAccountsBusiness;
import businessLogic.IInstallmentsBusiness;
import businessLogic.IMovementsBusiness;
import dataAccess.IInstallmentsDao;
import dataAccessImpl.InstallmentsDao;
import domainModel.Account;
import domainModel.Installment;
import domainModel.Movement;
import domainModel.MovementType;
import domainModel.MovementTypeEnum;
import exceptions.BusinessException;
import exceptions.SQLOperationException;

public class InstallmentsBusiness implements IInstallmentsBusiness
{
	private IInstallmentsDao installmentsDao;
	private IMovementsBusiness movementsBusiness;
	private IAccountsBusiness accountsBusiness;
	
	public InstallmentsBusiness() 
	{
		installmentsDao = new InstallmentsDao();
		movementsBusiness = new MovementsBusiness();
		accountsBusiness = new AccountsBusiness();
	}
	
	@Override
	public Installment read(int installmentId) throws BusinessException
	{
		try {
			return installmentsDao.read(installmentId);
		} 
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException("Ocurrió un error desconocido al obtener la cuota.");
		}
	}
	
	@Override
	public Installment read(Movement movement) throws BusinessException
	{
		try {
			return installmentsDao.read(movement);
		} 
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException("Ocurrió un error desconocido al obtener la cuota.");
		}
	}
	
	@Override
	public boolean update(Installment installment) throws BusinessException
	{
		try {
			return installmentsDao.update(installment);
		} 
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException("Ocurrió un error desconocido al actualizar la cuota.");
		}
	}

	@Override
	public boolean generate(int loanId) throws BusinessException
	{
		try {
			return installmentsDao.generate(loanId);
		} 
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException("Ocurrió un error desconocido al generar las cuotas.");
		}
	}

	@Override
	public boolean payInstallment(Installment installment, Account debitAccount)
			throws BusinessException
	{
		try
		{
			MovementType movementType = new MovementType();
			movementType.setId(MovementTypeEnum.LOAN_PAYMENT.getId());
			
			Movement movement = new Movement();
			movement.setDetails("Cuota " + installment.getNumber()
					+ " - Préstamo " + installment.getLoanId());
			movement.setAmount(installment.getAmount().negate());			
			movement.setMovementType(movementType);
			movement.setAccount(debitAccount);
			
			// El siguiente método asigna el Id por referencia al objeto Movement
			movementsBusiness.create(movement);
			
			installment.setMovement(movement);
			
			// Asignar id de movimiento a la cuota
			update(installment);

			// Actualizar saldo de la cuenta
			debitAccount.setBalance(
					debitAccount.getBalance().subtract(installment.getAmount()));
			accountsBusiness.update(debitAccount);
			
			return true;
		} 
		catch (BusinessException ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido al procesar el pago de la cuota.");
		}
		
	}

}
