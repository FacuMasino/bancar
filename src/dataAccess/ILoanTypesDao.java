package dataAccess;

import java.sql.SQLException;
import java.util.List;

import domainModel.LoanType;

public interface ILoanTypesDao
{
	public LoanType read(int loanTypeId) throws SQLException;
	public List<LoanType> list() throws SQLException;
}
