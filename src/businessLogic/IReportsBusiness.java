package businessLogic;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import domainModel.Loan;
import exceptions.BusinessException;

public interface IReportsBusiness
{
	public List<Loan> getLoansByDateRange(Date startDate, Date endDate) throws BusinessException;
	public BigDecimal getOutstandingInstallmentsAmount() throws BusinessException;
}
