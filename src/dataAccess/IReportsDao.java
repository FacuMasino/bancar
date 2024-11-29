package dataAccess;

import java.util.List;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

import domainModel.Loan;

public interface IReportsDao
{
	public List<Loan> getLoansByDateRange(Date startDate, Date endDate) throws SQLException;
	public BigDecimal getOutstandingInstallmentsAmount() throws SQLException;
}
