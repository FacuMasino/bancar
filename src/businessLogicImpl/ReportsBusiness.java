package businessLogicImpl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
		} catch (SQLException ex)
		{
			throw new SQLOperationException();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido al leer los prestamos solicitados...");
		}
	}

	@Override
	public BigDecimal getOutstandingInstallmentsAmount()
			throws BusinessException
	{
		try
		{
			return reportsDao.getOutstandingInstallmentsAmount();
		} catch (SQLException ex)
		{
			throw new SQLOperationException();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido al obtener la deuda total por préstamos...");
		}
	}

	@Override
	public int getOverdueLoansCount() throws BusinessException
	{
		try
		{
			return reportsDao.getOverdueLoansCount();
		} catch (SQLException ex)
		{
			throw new SQLOperationException();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido al obtener la cantidad de prestamos con cuotas atrasadas...");
		}
	}

	@Override
	public BigDecimal profitsEarned() throws BusinessException
	{
		try
		{
			return reportsDao.profitsEarned();
		} catch (SQLException e)
		{
			throw new SQLOperationException();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido al obtener la ganancias obtenidas por prestamos...");
		}
	}

	@Override
	public BigDecimal profitsToEarn() throws BusinessException
	{
		try
		{
			return reportsDao.profitsToEarn();
		} catch (SQLException e)
		{
			throw new SQLOperationException();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido al obtener las ganancias futuras por prestamos...");
		}
	}

	@Override
	public Map<String, Integer> getClientsByProvince() throws BusinessException
	{
		try
		{
			return reportsDao.getClientsByProvince();
		} 
		catch (SQLException e)
		{
			throw new SQLOperationException();
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido al obtener los clientes por provincia");
		}
	}

	@Override
	public Map<String, BigDecimal> getLoansAmountByMonthPeriod(LocalDate startDate,LocalDate endDate) throws BusinessException
	{
		try
		{
			return reportsDao.getLoansAmountByMonthPeriod(startDate, endDate);
		} 
		catch (SQLException e)
		{
			throw new SQLOperationException();
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido al obtener los montos de prestamos por periodo(MES)");
		}
	}

	@Override
	public Map<String, BigDecimal> getLoansAmountByDayPeriod(LocalDate startDate, LocalDate endDate) throws BusinessException
	{
		try
		{
			return reportsDao.getLoansAmountByDayPeriod(startDate, endDate);
		} 
		catch (SQLException e)
		{
			throw new SQLOperationException();
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido al obtener los montos de prestamos por periodo(DIA)");
		}
	}

	@Override
	public Map<String, BigDecimal> getTransfersAmountByMonthPeriod(LocalDate startDate, LocalDate endDate) throws BusinessException
	{
		try
		{
			return reportsDao.getTransfersAmountByMonthPeriod(startDate, endDate);
		} 
		catch (SQLException e)
		{
			throw new SQLOperationException();
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido al obtener los montos de transferencias por periodo(MES)");
		}
	}

	@Override
	public Map<String, BigDecimal> getTransfersAmountByDayPeriod(LocalDate startDate, LocalDate endDate) throws BusinessException
	{
		try
		{
			return reportsDao.getTransfersAmountByDayPeriod(startDate, endDate);
		} 
		catch (SQLException e)
		{
			throw new SQLOperationException();
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new BusinessException(
					"Ocurrió un error desconocido al obtener los montos de transferencias por periodo(DIA)");
		}
	}
}
