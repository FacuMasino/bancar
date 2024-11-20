package businessLogicImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import businessLogic.ILoansBusiness;
import dataAccessImpl.LoansDao;
import domainModel.Loan;
import exceptions.BusinessException;
import exceptions.SQLOperationException;

public class LoansBusiness implements ILoansBusiness 
{
	private LoansDao loansDao;
	
	public LoansBusiness()
	{
		loansDao = new LoansDao();
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
	public ArrayList<Loan> list() throws BusinessException
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
	
	public ArrayList<Loan> listByIdAccount (int accountId) throws BusinessException
	{
		try {
			return loansDao.listByIdAccount(accountId);
		} 
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException("Ocurrió un error desconocido al leer los prestamos...");
		}
	}
}
