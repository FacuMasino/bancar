package businessLogicImpl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import businessLogic.IReportsBusiness;
import dataAccess.IReportsDao;
import dataAccessImpl.ReportsDao;
import domainModel.Loan;
import exceptions.BusinessException;
import exceptions.SQLOperationException;

public class ReportsBusiness implements IReportsBusiness
{
	private IReportsDao reportsDao;
	
	
	
	public ReportsBusiness()
	{
		reportsDao = new ReportsDao();
	}



	@Override
	public List<Loan> getLoansByDateRange(Date startDate, Date endDate)
			throws BusinessException
	{
		try
		{
			return reportsDao.getLoansByDateRange(startDate, endDate);
		} 
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido al leer los prestamos solicitados...");
		}
	}



	@Override
	public BigDecimal getOutstandingInstallmentsAmount() throws BusinessException
	{
		try
		{
			return reportsDao.getOutstandingInstallmentsAmount();
		} 
		catch (SQLException ex)
		{
			throw new SQLOperationException();
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido la deuda total por préstamos...");
		}
	}

}
