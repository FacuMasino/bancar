package dataAccess;

import java.sql.SQLException;

import domainModel.LoanStatus;

public interface ILoanStatusesDao
{
	public LoanStatus read(int loanTypeId) throws SQLException;
}
