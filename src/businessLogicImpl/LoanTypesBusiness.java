package businessLogicImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import businessLogic.ILoanTypesBusiness;
import dataAccess.ILoanTypesDao;
import dataAccessImpl.LoanTypesDao;
import domainModel.LoanType;
import exceptions.BusinessException;
import exceptions.SQLOperationException;

public class LoanTypesBusiness implements ILoanTypesBusiness
{

	private ILoanTypesDao loanTypesDao;
	
	public LoanTypesBusiness()
	{
		loanTypesDao = new LoanTypesDao();
	}
	
	@Override
	public List<LoanType> list() throws BusinessException
	{
		try
		{
			return loanTypesDao.list();
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
						+ "los tipos de préstamos disponibles.");
		}
		
	}

}
