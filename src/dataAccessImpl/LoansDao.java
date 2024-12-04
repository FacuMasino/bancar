package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dataAccess.IAccountsDao;
import dataAccess.IClientsDao;
import dataAccess.IInstallmentsDao;
import dataAccess.ILoanStatusesDao;
import dataAccess.ILoanTypesDao;
import dataAccess.ILoansDao;
import domainModel.Client;
import domainModel.Installment;
import domainModel.Loan;
import domainModel.LoanStatus;
import domainModel.LoanType;

public class LoansDao implements ILoansDao 
{
	private Database db;
	private ILoanTypesDao loanTypesDao;
	private ILoanStatusesDao loanStatusesDao;
	private IInstallmentsDao installmentsDao;
	private IClientsDao clientsDao;
	private IAccountsDao accountsDao;
	
	public LoansDao() 
	{
		db = new Database();
		loanTypesDao = new LoanTypesDao();
		loanStatusesDao = new LoanStatusesDao();
		installmentsDao = new InstallmentsDao();
		clientsDao = new ClientsDao();
		accountsDao = new AccountsDao();
	}
	
	@Override
	public boolean create(Loan loan) throws SQLException 
	{
		int rows = 0;

		try
		{
			db.setPreparedStatement("{CALL insert_loan(?, ?, ?, ?, ?, ?, ?)}");
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
	public List<Loan> list() throws SQLException 
	{
		ResultSet rsLoans;
		List<Loan> loans = new ArrayList<Loan>();

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
		db.getPreparedStatement().setInt(4, loan.getLoanType().getId());
		db.getPreparedStatement().setInt(5, loan.getLoanStatus().getId());
		db.getPreparedStatement().setInt(6, loan.getClient().getClientId());
		db.getPreparedStatement().setInt(7, loan.getAccount().getId());
	}

	private void setUpdateParameters(Loan loan) throws SQLException
	{	
		//TODO: bug?
		db.getPreparedStatement().setInt(1, loan.getLoanStatus().getId());
		db.getPreparedStatement().setInt(2, loan.getLoanId());
	}
	
	//Se cambia a protected para acceder desde otras clases del package(y no repetir codigo)
	protected Loan getLoan(ResultSet rs) throws SQLException
	{   
		Loan auxLoan = new Loan();
                               
		try
		{
			auxLoan.setLoanId(rs.getInt("LoanId"));
			auxLoan.setCreationDate(rs.getDate("CreationDate"));
			auxLoan.setRequestedAmount(rs.getBigDecimal("RequestedAmount"));
			auxLoan.setInterestRate(rs.getBigDecimal("InterestRate"));
			auxLoan.setInstallmentsQuantity(rs.getInt("InstallmentsQuantity"));
			
			int loanTypeId = rs.getInt("LoanTypeId");
			auxLoan.setLoanType(loanTypesDao.read(loanTypeId));
			
			int loanStatusId = rs.getInt("LoanStatusId");
			auxLoan.setLoanStatus(loanStatusesDao.read(loanStatusId));
			
			int clientId = rs.getInt("ClientId");
			auxLoan.setClient(clientsDao.read(clientId));
			
			int accountId = rs.getInt("AccountId");
			auxLoan.setAccount(accountsDao.read(accountId));
			
			auxLoan.setInstallments(installmentsDao.listByLoanId(auxLoan.getLoanId()));
			auxLoan.setPendingInstallments(installmentsDao.listPendingsByLoanId(auxLoan.getLoanId()));
		}
		catch (SQLException ex) 
		{
			ex.printStackTrace();
			throw ex;
		}

		return auxLoan;
	}
	
	@Override
	public List<Loan> list (Client client) throws SQLException
	{

		ResultSet rsLoans;
		List<Loan> auxLoansList = new ArrayList<Loan>();

		try
		{
			db.setPreparedStatement("SELECT * FROM Loans WHERE ClientId = ?");
			db.getPreparedStatement().setInt(1, client.getClientId());
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
	
	@Override
	public boolean currentLoans(Client client) throws SQLException
	{	
		ResultSet rsLoans;

		try
		{
			int countLoans = 0; 
			
			db.setPreparedStatement("SELECT * FROM Loans WHERE ClientId = ? AND LoanStatusId = 2");
			db.getPreparedStatement().setInt(1, client.getClientId());
			rsLoans = db.getPreparedStatement().executeQuery();

			while (rsLoans.next()) 
			{
				countLoans ++;
			}
			
			return countLoans > 0;  
	
		} 
		catch (SQLException ex) 
		{
			ex.printStackTrace();
			throw ex;
		}
	
	}
}