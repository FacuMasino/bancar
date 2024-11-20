package dataAccess;

import java.sql.SQLException;
import java.util.ArrayList;

import domainModel.LoanType;

public interface ILoanTypesDao
{
	public LoanType read(int loanTypeId) throws SQLException;
	public ArrayList<LoanType> list() throws SQLException;
}
