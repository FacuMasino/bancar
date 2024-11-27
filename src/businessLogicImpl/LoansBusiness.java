package businessLogicImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import businessLogic.IInstallmentsBusiness;
import businessLogic.ILoansBusiness;
import dataAccess.ILoansDao;
import dataAccessImpl.LoansDao;
import domainModel.Account;
import domainModel.Client;
import domainModel.Installment;
import domainModel.Loan;
import domainModel.LoanStatus;
import domainModel.LoanStatusEnum;
import domainModel.LoanType;
import exceptions.BusinessException;
import exceptions.SQLOperationException;

public class LoansBusiness implements ILoansBusiness 
{
	private ILoansDao loansDao;
	private IInstallmentsBusiness installmentsBusiness;
	
	public LoansBusiness()
	{
		loansDao = new LoansDao();
		installmentsBusiness = new InstallmentsBusiness();
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
			if(installmentsBusiness.generate(loan.getLoanId()))
			{
				LoanStatus loanStatus = new LoanStatus();
				loanStatus.setId(2); // ESTADO APROBADO
				
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
		return list.stream().filter(loan -> 
							loan.getLoanStatus().getId() == loanStatus.getId())
							.collect(Collectors.toCollection(ArrayList::new));
	}
	
	@Override
	public List<Loan> filter(LoanType loanType, List<Loan> list)
		throws BusinessException
	{
		return list.stream().filter(loan -> 
							loan.getLoanType().getId() == loanType.getId())
							.collect(Collectors.toCollection(ArrayList::new));
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
}
