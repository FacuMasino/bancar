package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dataAccess.ILoansDao;
import domainModel.Loan;
import domainModel.LoanStatus;
import domainModel.LoanType;

public class LoansDao implements ILoansDao 
{
	private Database db;
	private LoanStatus loanStatus; 
	private LoanType loanType;
	
	
//TODO VER QUE FALTA AGREGAR ACÁ
	
	public LoansDao() 
	{
		db = new Database();
		
	}
	
	@Override
	public boolean create(Loan loan) throws SQLException 
	{
		int rows = 0;

		try
		{
		    loanStatus = new LoanStatus();
			loanType = new LoanType();
		
			////auxAccountType = accountTypeDao.readByName(account.getAccountType().getName());
			////////account.setAccountType(auxAccountType); // seteo el accountType completo

			db.setPreparedStatement("{CALL insert_loan(?, ?, ?, ?)}");
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
			db.setPreparedStatement("{CALL update_loan(?, ?, ?, ?)}");
			setUpdateParameters(loan);
			rows = db.getPreparedStatement().executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}

		return (rows > 0);
	}

	@Override
	public boolean delete(int loanId) throws SQLException 
	{
		return false;
		
	}

	@Override
	public ArrayList<Loan> list() throws SQLException 
	{
		ResultSet rsLoans;
		ArrayList<Loan> loans = new ArrayList<Loan>();

		try
		{
			db.setPreparedStatement("SELECT * FROM Loans ");
			rsLoans = db.getPreparedStatement().executeQuery();

			while (rsLoans.next())
				{
				// TODO: IMPORTANTE leer cliente con su ID usando ClientsBusiness, ahora está
				// vacío
				// Client auxClient = new Client();
				// TODO: IMPORTANTE leer tipo de cuenta con su ID usando AccountTypeBusiness,
				// ahora está vacía
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

	@Override
	public int getId(Loan loan) throws SQLException 
	{
		return 0;
	}

	@Override
	public int getLastId() throws SQLException 
	{
		int lastId = 0;

		try 
		{
			db.setPreparedStatement("SELECT MAX(LoanId) from Loans");
			ResultSet rs = db.getPreparedStatement().executeQuery();
			if (rs.next()) {
				lastId = rs.getInt(1);
			}
		} 
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}

		return lastId;
	}
	
	private void setParameters(Loan loan) throws SQLException
	{
		///TODO: Installmentquantity, AccountID ? 
		db.getPreparedStatement().setBigDecimal(1, loan.getRequestedAmount());
		db.getPreparedStatement().setBigDecimal(2, loan.getInterestRate());
		db.getPreparedStatement().setInt(3, loan.getLoanType().getLoanTypeId());
		db.getPreparedStatement().setInt(4, loan.getLoanStatus().getLoanStatusId());
		///db.getPreparedStatement().setInt(5, loan.getClientId());
	}

	private void setUpdateParameters(Loan loan) throws SQLException
	{
		///TODO: Installmentquantity, AccountID ? 
		db.getPreparedStatement().setBigDecimal(1, loan.getRequestedAmount());
		db.getPreparedStatement().setBigDecimal(2, loan.getInterestRate());
		db.getPreparedStatement().setInt(3, loan.getLoanType().getLoanTypeId());
		db.getPreparedStatement().setInt(4, loan.getLoanStatus().getLoanStatusId());
		db.getPreparedStatement().setInt(5, loan.getLoanId());
	}

	private Loan getLoan(ResultSet rs) throws SQLException
	{   
		Loan auxLoan = new Loan();
                               
		try
		{
			auxLoan.setLoanId(rs.getInt("LoanId"));
           /// auxLoan.setClientId(rs.getInt("clientId"));
			auxLoan.setCreationDate(rs.getDate("CreationDate"));
			auxLoan.setRequestedAmount(rs.getBigDecimal("RequestedAmount"));
			auxLoan.setInterestRate(rs.getBigDecimal("InterestRate"));

			///TODO: FALTA EN BD ?   auxLoan.setMonthsLimit(rs.getInt("monthsLimit"));
			//TODO: Hay que elaborar read en Loantype y loanStatus

			LoanType auxLoanType = new LoanType();
			LoanStatus auxLoanStatus = new LoanStatus();		
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
