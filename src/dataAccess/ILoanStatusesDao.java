package dataAccess;

import java.sql.SQLException;
import java.util.List;

import domainModel.LoanStatus;

public interface ILoanStatusesDao
{
	public LoanStatus read(int loanTypeId) throws SQLException;
	public List<LoanStatus> list() throws SQLException;
}
