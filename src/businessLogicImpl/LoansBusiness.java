package businessLogicImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import businessLogic.IAccountsBusiness;
import businessLogic.IInstallmentsBusiness;
import businessLogic.ILoansBusiness;
import businessLogic.IMovementsBusiness;
import dataAccess.ILoansDao;
import dataAccessImpl.LoansDao;
import domainModel.Account;
import domainModel.Client;
import domainModel.Installment;
import domainModel.Loan;
import domainModel.LoanStatus;
import domainModel.LoanStatusEnum;
import domainModel.LoanType;
import domainModel.Movement;
import domainModel.MovementType;
import domainModel.MovementTypeEnum;
import exceptions.BusinessException;
import exceptions.SQLOperationException;

public class LoansBusiness implements ILoansBusiness 
{
	private ILoansDao loansDao;
	private IInstallmentsBusiness installmentsBusiness;
	private IAccountsBusiness accountsBusiness;
	private IMovementsBusiness movementsBusiness;
	
	public LoansBusiness()
	{
		loansDao = new LoansDao();
		installmentsBusiness = new InstallmentsBusiness();
		accountsBusiness = new AccountsBusiness();
		movementsBusiness = new MovementsBusiness();
	}

	// TODO: PENDIENTE Ningún método valida las reglas de negocio
	
	@Override
	public boolean create(Loan loan) throws BusinessException 
	{
		try
		{		
			return loansDao.create(loan);
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

	@Override
	public Loan read(int loanId) throws BusinessException
	{
		try
		{
			return loansDao.read(loanId);			
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al leer el préstamo.");
		}
	}

	// Este método debería utilizarse solo por 2 motivos:
	// - El Admin aprueba el préstamo y deben generarse las cuotas + actualizar estado
	// - Se cambia el estado a Finalizado porque pagó la última cuota
	@Override
	public boolean update(Loan loan) throws BusinessException
	{
		try
		{
			return loansDao.update(loan);
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al actualizar el préstamo.");
		}
	}
	
	@Override
	public boolean approve(Loan loan) throws BusinessException
	{
		try
		{
			// Generación de cuotas
			if(installmentsBusiness.generate(loan.getLoanId()))
			{
				LoanStatus loanStatus = new LoanStatus();
				loanStatus.setId(LoanStatusEnum.APPROVED.getId());
				
				// Generación de movimiento
				MovementType movementType = new MovementType();
				movementType.setId(MovementTypeEnum.NEW_LOAN.getId());

				Movement movement = new Movement();
				movement.setDetails("Préstamo Nro. " + loan.getLoanId());
				movement.setAmount(loan.getRequestedAmount());			
				movement.setMovementType(movementType);
				movement.setAccount(loan.getAccount());
				
				movementsBusiness.create(movement);
				
				// Acreditación de saldo
				Account creditAccount = loan.getAccount();
				creditAccount.setBalance(creditAccount.getBalance()
						.add(loan.getRequestedAmount()));
				accountsBusiness.update(creditAccount);
				
				// Actualización de estado
				loan.setLoanStatus(loanStatus);
				return update(loan);
			}
			throw new BusinessException
			("No se pudo aprobar el préstamo, error al generar las cuotas.");
		}
		catch (BusinessException ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al aprobar el préstamo.");
		}
	}

	@Override
	public List<Loan> list() throws BusinessException
	{
		try
		{
			return loansDao.list();			
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al obtener los préstamos.");
		}
	}
	
	@Override
	public List<Loan> list(Client client) throws BusinessException
	{
		try {
			return loansDao.list(client);
		} 
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException("Ocurrió un error desconocido al leer los prestamos.");
		}
	}
	
	@Override
	public BigDecimal calcOutstandingBalance(Loan auxLoan)
	{
		
		BigDecimal outstandingBalance = new BigDecimal(0);
		
		for(Installment installment : auxLoan.getPendingInstallments())
		{
			outstandingBalance = outstandingBalance.add(installment.getAmount());
		}
		
		return outstandingBalance;
	}

	@Override
	public List<Loan> filter(LoanStatus loanStatus, List<Loan> list)
		throws BusinessException
	{
		// La función Stream no modifica la lista original, si no
		// que crea un nuevo objeto al cuál se le aplican las transformaciones.
		// Devuelve un nuevo objeto distinto del original.
		return list.stream().filter(loan -> 
							loan.getLoanStatus().getId() == loanStatus.getId())
							.collect(Collectors.toList());
	}
	
	@Override
	public List<Loan> filter(LoanType loanType, List<Loan> list)
		throws BusinessException
	{
		return list.stream().filter(loan -> 
							loan.getLoanType().getId() == loanType.getId())
							.collect(Collectors.toList());
	}
	
	@Override
	public boolean payLoan(Loan loan, int installmentId, Account debitAccount)
			throws BusinessException
	{

		try
		{
			Installment dueInstallment = loan.getPendingInstallments().stream()
					.filter(l -> l.getInstallmentId() == installmentId)
					.findFirst()
					.orElse(null);
			if(dueInstallment == null)
			{
				throw new BusinessException("Ocurrió un error al obtener la cuota.");
			}
			
			// Comprobar saldo
			if (debitAccount.getBalance()
					.compareTo(dueInstallment.getAmount()) < 0)
			{
				throw new BusinessException("Saldo insuficiente.");
			}
			
			// Pagar: generar movimiento, actualizar cuota, descontar saldo
			installmentsBusiness.payInstallment(dueInstallment, debitAccount);
			
			// Comprobar si es última cuota para actualizar estado del préstamo
			if(dueInstallment.getNumber() == loan.getInstallmentsQuantity())
			{
				LoanStatus loanStatus = new LoanStatus();
				loanStatus.setId(LoanStatusEnum.FINISHED.getId());
				loan.setLoanStatus(loanStatus);
				update(loan);
			}
			
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
					"Ocurrió un error desconocido al pagar el préstamo");
		}
	}
	
	public boolean currentLoans (Client client) throws BusinessException
	{
		try
		{
			return loansDao.currentLoans(client);
		}
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error desconocido al verificar préstamos vigentes o en revisión.");
		}
	}
}