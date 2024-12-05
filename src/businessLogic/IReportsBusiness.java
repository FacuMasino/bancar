package businessLogic;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import domainModel.Loan;
import exceptions.BusinessException;

public interface IReportsBusiness
{
	public List<Loan> getLoansByDateRange(Date startDate, Date endDate) throws BusinessException;
	public BigDecimal getOutstandingInstallmentsAmount() throws BusinessException;
	public int getOverdueLoansCount() throws BusinessException;
	public BigDecimal profitsEarned() throws BusinessException;
	public BigDecimal profitsToEarn() throws BusinessException;
	public Map<String,Integer> getClientsByProvince() throws BusinessException;
	public Map<String,BigDecimal> getLoansAmountByMonthPeriod(LocalDate startDate, LocalDate endDate) throws BusinessException;
	public Map<String,BigDecimal> getLoansAmountByDayPeriod(LocalDate startDate, LocalDate endDate) throws BusinessException;
	public Map<String,BigDecimal> getTransfersAmountByMonthPeriod(LocalDate startDate, LocalDate endDate) throws BusinessException;
	public Map<String,BigDecimal> getTransfersAmountByDayPeriod(LocalDate startDate, LocalDate endDate) throws BusinessException;
}
