package dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import domainModel.Client;
import domainModel.Loan;

public interface ILoansDao 
{
	public boolean create(Loan loan) throws SQLException;
	public Loan read(int loanId) throws SQLException;
	public boolean update(Loan loan) throws SQLException;
	public List<Loan> list() throws SQLException;
	public List<Loan> list(Client client) throws SQLException;
	public boolean currentLoans (Client client) throws SQLException;
	public Loan getLoan(ResultSet rs) throws SQLException;
}