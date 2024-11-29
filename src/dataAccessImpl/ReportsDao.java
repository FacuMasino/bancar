package dataAccessImpl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dataAccess.IReportsDao;
import domainModel.Loan;

public class ReportsDao implements IReportsDao
{
	private Database db;
	private LoansDao loansDao;

	public ReportsDao()
	{
		db = new Database();
		loansDao = new LoansDao();
	}

	@Override
	public List<Loan> getLoansByDateRange(Date startDate, Date endDate) throws SQLException
	{
		ResultSet rsLoans;
		List<Loan> loans = new ArrayList<Loan>();

		try
		{
			db.setPreparedStatement("SELECT * FROM Loans WHERE creationDate BETWEEN ? AND ?");
			db.getPreparedStatement().setDate(1, startDate);
			db.getPreparedStatement().setDate(2, endDate);

			rsLoans = db.getPreparedStatement().executeQuery();
			while (rsLoans.next()) {
				loans.add(loansDao.getLoan(rsLoans));
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		System.out.println("\nDesde el Dao, la startDate es: " + startDate);
		System.out.println("\nDesde el Dao, la endDate es: " + endDate);
		return loans;
	}

	@Override
	public BigDecimal getOutstandingInstallmentsAmount() throws SQLException
	{
		String query = 
				"SELECT "
				+ "SUM(i.Amount) AS totalPendingAmount "
				+ "FROM "
				+ "Installments i "
				+ "INNER JOIN "
				+ "Loans l ON i.LoanId = l.LoanId "
				+ "INNER JOIN "
				+ "LoanStatuses ls ON l.LoanStatusId = ls.LoanStatusId "
				+ "WHERE "
				+ "ls.LoanStatusName = 'Vigente' "
				+ "AND i.MovementId IS NULL;";
		
		ResultSet rs;
		BigDecimal amount = new BigDecimal(0);
		
		try
		{
			db.setPreparedStatement(query);
			rs = db.getPreparedStatement().executeQuery();
			
			if (!rs.next()) 
			{
				return amount;
			}
			amount = rs.getBigDecimal("totalPendingAmount");
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		return amount;
	}
}
