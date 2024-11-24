package businessLogicImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import businessLogic.ILoanStatusesBusiness;
import dataAccess.ILoanStatusesDao;
import dataAccessImpl.LoanStatusesDao;
import domainModel.LoanStatus;
import exceptions.BusinessException;
import exceptions.SQLOperationException;

public class LoanStatusesBusiness implements ILoanStatusesBusiness
{
	private ILoanStatusesDao loanStatusesDao;
	
	public LoanStatusesBusiness()
	{
		loanStatusesDao = new LoanStatusesDao();
	}

	@Override
	public List<LoanStatus> list() throws BusinessException
	{
		try
		{
			return loanStatusesDao.list();
		} 
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new SQLOperationException();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException
				("Ocurrió un error al obtener "
						+ "los estados de préstamos disponibles.");
		}
	}

}
