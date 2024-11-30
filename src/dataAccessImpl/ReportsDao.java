package dataAccessImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

	@Override
	public int getOverdueLoansCount() throws SQLException
	{
		ResultSet rs;
		String query = 
				"SELECT COUNT(DISTINCT LoanId) AS overdueLoansCount "
				+ "FROM Installments "
				+ "WHERE PaymentDueDate < CURDATE() "
				+ "AND MovementId IS NULL";
		
		int count = 0;
		
		try 
		{
			db.setPreparedStatement(query);
			rs = db.getPreparedStatement().executeQuery();
			
			if (!rs.next()) 
			{
				return count;
			}
			count  = rs.getInt("overdueLoansCount");
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}

		return count;
	}

	/*
	 Segun calculo de interes en SP generate_installments,tenemos que:
	 
	 interesPorInstallment = (InterestRate/(12*100))*requestedAmount
	 en base a este interes, calculamos la ganancia obtenida hasta el momento, y lo que queda por ganar...
	 */
	
	//TODO: Ambas funciones se podrian optimizar en una sola...solo cambia la query y coso
	
	@Override
	public BigDecimal profitsEarned() throws SQLException
	{
		ResultSet rs;
		String query = 
				"SELECT i.LoanId, "
				+ "SUM(CASE WHEN i.MovementId IS NOT NULL THEN 1 ELSE 0 END) AS installmentsCount "
				+ "FROM Installments i "
				+ "GROUP BY i.LoanId;";
		
		BigDecimal profits = BigDecimal.valueOf(0);

		try
		{
			db.setPreparedStatement(query);

			rs = db.getPreparedStatement().executeQuery();
			while (rs.next()) {
				profits = profits.add(calculateProfit(rs));
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}

		return profits;
	}

	@Override
	public BigDecimal profitsToEarn() throws SQLException
	{
		ResultSet rs;
		String query = 
				"SELECT i.LoanId, "
				+ "SUM(CASE WHEN i.MovementId IS NULL THEN 1 ELSE 0 END) AS installmentsCount "
				+ "FROM Installments i "
				+ "GROUP BY i.LoanId;";
		
		BigDecimal profitsToEarn = BigDecimal.valueOf(0);

		try
		{
			db.setPreparedStatement(query);

			rs = db.getPreparedStatement().executeQuery();
			while (rs.next()) {
				profitsToEarn = profitsToEarn.add(calculateProfit(rs));
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}

		return profitsToEarn;
	}
	
	private BigDecimal calculateProfit(ResultSet rs)
	{
		Loan loan = new Loan();
		BigDecimal auxProfit = BigDecimal.valueOf(0);
		BigDecimal interest = BigDecimal.valueOf(0);
		int count = 0;
		try
		{
			loan = loansDao.read(rs.getInt("LoanId"));
			count = rs.getInt("installmentsCount");
			interest = loan.getInterestRate().multiply(loan.getRequestedAmount()).divide(BigDecimal.valueOf(1200), 2, RoundingMode.HALF_UP);
			auxProfit = interest.multiply(BigDecimal.valueOf(count));
			
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return auxProfit;
	}

}
