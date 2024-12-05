package dataAccess;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import domainModel.Loan;

public interface IReportsDao
{
	public List<Loan> getLoansByDateRange(Date startDate, Date endDate) throws SQLException;
	public BigDecimal getOutstandingInstallmentsAmount() throws SQLException;
	public int getOverdueLoansCount() throws SQLException;
	public BigDecimal profitsEarned() throws SQLException;
	public BigDecimal profitsToEarn() throws SQLException;
	public Map<String,Integer> getClientsByProvince() throws SQLException;
	public Map<String,BigDecimal> getLoansAmountByMonthPeriod(LocalDate startDate, LocalDate endDate) throws SQLException;
	public Map<String,BigDecimal> getLoansAmountByDayPeriod(LocalDate startDate, LocalDate endDate) throws SQLException;
	public Map<String,BigDecimal> getTransfersAmountByMonthPeriod(LocalDate startDate, LocalDate endDate) throws SQLException;
	public Map<String,BigDecimal> getTransfersAmountByDayPeriod(LocalDate startDate, LocalDate endDate) throws SQLException;
}
