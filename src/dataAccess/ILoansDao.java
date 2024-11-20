package dataAccess;

import java.sql.SQLException;
import java.util.ArrayList;
import domainModel.Loan;

public interface ILoansDao 
{
	public boolean create(Loan loan) throws SQLException;
	public Loan read(int loanId) throws SQLException;
	public boolean update(Loan loan) throws SQLException;
	public ArrayList<Loan> list() throws SQLException;
	public ArrayList<Loan> listByIdAccount(int accountId) throws SQLException;
}
