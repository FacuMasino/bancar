package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dataAccess.ILoansDao;
import domainModel.Installment;
import domainModel.Loan;
import domainModel.LoanStatus;
import domainModel.LoanType;

public class LoansDao implements ILoansDao 
{
	private Database db;
	private LoanTypesDao loanTypesDao;
	private LoanStatusesDao loanStatusesDao;
	private InstallmentsDao installmentsDao;
	
	public LoansDao() 
	{
		db = new Database();
		loanTypesDao = new LoanTypesDao();
		loanStatusesDao = new LoanStatusesDao();
		installmentsDao = new InstallmentsDao();
	}
	
	@Override
	public boolean create(Loan loan) throws SQLException 
	{
		int rows = 0;

		try
		{
			db.setPreparedStatement("{CALL insert_loan(?, ?, ?, ?, ?, ?)}");
			setParameters(loan);
			rows = db.getPreparedStatement().executeUpdate();
		} 
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}

		return (rows > 0);
	}

	@Override
	public Loan read(int loanId) throws SQLException
	{
		
		ResultSet rsLoan;
		Loan auxLoan= null; 

		try
		{
			db.setPreparedStatement("SELECT * FROM Loans WHERE LoanId = ?");
			db.getPreparedStatement().setInt(1, loanId);
			rsLoan = db.getPreparedStatement().executeQuery();

			if (!rsLoan.next()) 
			{
				return auxLoan;
			}

			auxLoan= getLoan(rsLoan);
  
		}
		catch (SQLException ex) 
		{
			ex.printStackTrace();
			throw ex;
		}

		return auxLoan;
	}

	@Override
	public boolean update(Loan loan) throws SQLException 
	{
		int rows = 0;

		try {
			db.setPreparedStatement("UPDATE Loans SET LoanStatusId = ? WHERE LoanId = ?");
			setUpdateParameters(loan);
			rows = db.getPreparedStatement().executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}

		return (rows > 0);
	}

	@Override
	public ArrayList<Loan> list() throws SQLException 
	{
		ResultSet rsLoans;
		ArrayList<Loan> loans = new ArrayList<Loan>();

		try
		{
			db.setPreparedStatement("SELECT * FROM Loans");
			rsLoans = db.getPreparedStatement().executeQuery();

			while (rsLoans.next())
			{
				loans.add(getLoan(rsLoans));
			}
		}
		
		catch (SQLException ex) 
		{
			ex.printStackTrace();
			throw ex;
		}

		return loans;
	}
	
	private void setParameters(Loan loan) throws SQLException
	{
		db.getPreparedStatement().setInt(1, loan.getInstallmentsQuantity());
		db.getPreparedStatement().setBigDecimal(2, loan.getRequestedAmount());
		db.getPreparedStatement().setBigDecimal(3, loan.getInterestRate());
		db.getPreparedStatement().setInt(4, loan.getLoanType().getLoanTypeId());
		db.getPreparedStatement().setInt(5, loan.getLoanStatus().getId());
		db.getPreparedStatement().setInt(6, loan.getAccountId());
	}

	private void setUpdateParameters(Loan loan) throws SQLException
	{
		db.getPreparedStatement().setInt(1, loan.getLoanId());
		db.getPreparedStatement().setInt(2, loan.getLoanStatus().getId());
	}

	private Loan getLoan(ResultSet rs) throws SQLException
	{   
		Loan auxLoan = new Loan();
                               
		try
		{
			auxLoan.setLoanId(rs.getInt("LoanId"));
			auxLoan.setCreationDate(rs.getDate("CreationDate"));
			auxLoan.setRequestedAmount(rs.getBigDecimal("RequestedAmount"));
			auxLoan.setInterestRate(rs.getBigDecimal("InterestRate"));
			auxLoan.setAccountId(rs.getInt("AccountId"));
			
			int loanTypeId = rs.getInt("LoanTypeId");
			auxLoan.setLoanType(loanTypesDao.read(loanTypeId));
			
			int loanStatusId = rs.getInt("LoanStatusId");
			auxLoan.setLoanStatus(loanStatusesDao.read(loanStatusId));
			
			auxLoan.setInstallments(installmentsDao.listByLoanId(auxLoan.getLoanId()));
		}
		catch (SQLException ex) 
		{
			ex.printStackTrace();
			throw ex;
		}

		return auxLoan;
	}


	public ArrayList<Loan> listByIdAccount (int accountId) throws SQLException
	{

		ResultSet rsLoans;
		ArrayList<Loan> auxLoansList = new ArrayList<Loan>();

		try {
			
			db.setPreparedStatement("SELECT * FROM Loans WHERE AccountId = ?");
			db.getPreparedStatement().setInt(1, accountId);
			rsLoans = db.getPreparedStatement().executeQuery();

			while (rsLoans.next()) 
			{
				auxLoansList.add(getLoan(rsLoans));
			}

		} 
		catch (SQLException ex) 
		{
			ex.printStackTrace();
			throw ex;
		}

		return auxLoansList;
	}
}
