package dataAccessImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dataAccess.IReportsDao;
import domainModel.Loan;
import utils.DateRangeFormatter;
import utils.MapTools;

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

	/**
	 * Metodo que calcula los interes ganados por todas las cuotas pagadas.
	 **/
	
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
	
	/**
	 * Metodo que calcula los interes por ganar, por las cuotas aun no pagadas.
	 **/
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
	
	/**
	 * 
	 * Metodo para calcular la ganancia obtenida o por obtener de un prestamo.Se calcula el interes de la cuota y se multiplica por la cantidad de cuotas.
	 * -Para el caso de las cuotas pagadas, seran cantidad de cuotas pagadas
	 * -Para el caso de las cuotas aun no pagadas, seran cantidad de cuotas aun no pagadas.
	 * 
	 * interesPorInstallment = (InterestRate/(12*100))*requestedAmount
	 * 
	 */
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

	/**
	 *	Metodo que devuelve un Map del estilo {Provincia : cantidad_clientes} 
	 **/
	@Override
	public Map<String, Integer> getClientsByProvince() throws SQLException
	{
		Map<String,Integer> clientsByProvince = new HashMap<>();
		ResultSet rs;
		String query = 
				"SELECT p.ProvinceName AS ProvinceName, "
				+ "COUNT(cl.ClientId) AS ClientCount "
				+ "FROM Clients cl "
				+ "INNER JOIN "
				+ "Addresses a ON cl.AddressId = a.AddressId "
				+ "INNER JOIN "
				+ "Cities ci ON a.CityId = ci.CityId "
				+ "INNER JOIN "
				+ "Provinces p ON ci.ProvinceId = p.ProvinceId "
				+ "WHERE cl.ActiveStatus = 1 "
				+ "GROUP BY "
				+ "p.ProvinceName "
				+ "ORDER BY "
				+ "ClientCount DESC;";
		
		try
		{
			db.setPreparedStatement(query);
			rs = db.getPreparedStatement().executeQuery();
			
			while (rs.next()) {
				clientsByProvince.put(rs.getString("ProvinceName"),rs.getInt("ClientCount"));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return clientsByProvince;
	}
	/**
	 *	Metodo que devuelve un Map del estilo {Mes : Monto_otorgado_porPrestamos} 
	 *IMPORTANTE: suma un dia a la consulta para obtener la fecha endDate inclusive.Because TimeStamp jode con el horario en las query
	 **/
	@Override
	public Map<String, BigDecimal> getLoansAmountByMonthPeriod(LocalDate startDate,LocalDate endDate) throws SQLException
	{
		ArrayList<String> periods = (ArrayList<String>) DateRangeFormatter.generateFormattedMonths(startDate, endDate);
		
		//Creo un Map que tiene como keys: periodos entre startDate y endDate. Como values 0.0
		LinkedHashMap<String,BigDecimal> loansAmountByPeriod = new LinkedHashMap<>();
		loansAmountByPeriod = (LinkedHashMap<String, BigDecimal>) MapTools.assignKeysFromList(periods, BigDecimal.valueOf(0));
		ResultSet rs;
		
		String query = 
				"SELECT "
				+ 	"UPPER(DATE_FORMAT(MovementDateTime, '%b-%y')) AS MonthYear, "
				+ 	"SUM(Amount) AS TotalAmount "
				+ "FROM "
				+ 	"Movements "
				+ "WHERE "
				+ 	"MovementTypeId = 2 "
				+ 	"AND MovementDateTime BETWEEN ? AND ? "
				+ "GROUP BY "
				+ 	"MonthYear "
				+ "ORDER BY "
				+ 	"MonthYear DESC ";
		try
		{
			db.setPreparedStatement(query);
			db.getPreparedStatement().setString(1, startDate.toString());
			db.getPreparedStatement().setString(2, endDate.toString());
			rs = db.getPreparedStatement().executeQuery();
			
			while (rs.next()) 
			{
				//Si la key ya existe, el metodo PUT actualiza el value.
				loansAmountByPeriod.put(rs.getString("MonthYear"), rs.getBigDecimal("TotalAmount"));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return loansAmountByPeriod;
	}
	//TODO: estos 4 metodos se pueden optimizar en 2 (quizas 1) ver Gonzoo
	@Override
	public Map<String, BigDecimal> getLoansAmountByDayPeriod(LocalDate startDate, LocalDate endDate) throws SQLException
	{
		ArrayList<String> periods = (ArrayList<String>) DateRangeFormatter.generateFormattedDays(startDate, endDate);

		// Creo un Map que tiene como keys: periodos entre startDate y endDate.
		// Como values 0.0
		LinkedHashMap<String, BigDecimal> loansAmountByPeriod = new LinkedHashMap<>();
		loansAmountByPeriod = (LinkedHashMap<String, BigDecimal>) MapTools
				.assignKeysFromList(periods, BigDecimal.valueOf(0));
		ResultSet rs;

		String query = 
				"SELECT "
				+ "DATE_FORMAT(MovementDateTime, '%d/%m') AS DayMonth, "
				+ "SUM(Amount) AS TotalAmount "
				+ "FROM "
				+ "Movements "
				+ "WHERE " 
				+ "MovementTypeId = 2 "
				+ "AND MovementDateTime BETWEEN ? AND ? "
				+ "GROUP BY "
				+ "DayMonth " 
				+ "ORDER BY " 
				+ "STR_TO_DATE(DayMonth, '%d/%m') ASC; ";
		try
		{
			db.setPreparedStatement(query);
			db.getPreparedStatement().setString(1, startDate.toString());
			db.getPreparedStatement().setString(2, endDate.toString());
			rs = db.getPreparedStatement().executeQuery();

			while (rs.next())
			{
				// Si la key ya existe, el metodo PUT actualiza el value.
				loansAmountByPeriod.put(rs.getString("DayMonth"),
						rs.getBigDecimal("TotalAmount"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return loansAmountByPeriod;
	}

	@Override
	public Map<String, BigDecimal> getTransfersAmountByMonthPeriod(LocalDate startDate, LocalDate endDate) throws SQLException
	{
		ArrayList<String> periods = (ArrayList<String>) DateRangeFormatter.generateFormattedMonths(startDate, endDate);
		
		//Creo un Map que tiene como keys: periodos entre startDate y endDate. Como values 0.0
		LinkedHashMap<String,BigDecimal> transfersAmountByPeriod = new LinkedHashMap<>();
		transfersAmountByPeriod = (LinkedHashMap<String, BigDecimal>) MapTools.assignKeysFromList(periods, BigDecimal.valueOf(0));
		ResultSet rs;
		
		String query = 
				"SELECT "
				+ 	"UPPER(DATE_FORMAT(MovementDateTime, '%b-%y')) AS MonthYear, "
				+ 	"SUM(Amount) AS TotalAmount "
				+ "FROM "
				+ 	"Movements "
				+ "WHERE "
				+ 	"MovementTypeId = 4 "
				+ 	" AND Amount > 0 "
				+ 	"AND MovementDateTime BETWEEN ? AND ? "
				+ "GROUP BY "
				+ 	"MonthYear "
				+ "ORDER BY "
				+ 	"MonthYear DESC ";
		try
		{
			db.setPreparedStatement(query);
			db.getPreparedStatement().setString(1, startDate.toString());
			db.getPreparedStatement().setString(2, endDate.toString());
			rs = db.getPreparedStatement().executeQuery();
			
			while (rs.next()) 
			{
				//Si la key ya existe, el metodo PUT actualiza el value.
				transfersAmountByPeriod.put(rs.getString("MonthYear"), rs.getBigDecimal("TotalAmount"));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return transfersAmountByPeriod;
	}

	@Override
	public Map<String, BigDecimal> getTransfersAmountByDayPeriod(LocalDate startDate, LocalDate endDate) throws SQLException
	{
		ArrayList<String> periods = (ArrayList<String>) DateRangeFormatter.generateFormattedDays(startDate, endDate);

		// Creo un Map que tiene como keys: periodos entre startDate y endDate.
		// Como values 0.0
		LinkedHashMap<String, BigDecimal> transfersAmountByPeriod = new LinkedHashMap<>();
		transfersAmountByPeriod = (LinkedHashMap<String, BigDecimal>) MapTools.assignKeysFromList(periods, BigDecimal.valueOf(0));
		ResultSet rs;

		String query = 
				"SELECT "
				+ "DATE_FORMAT(MovementDateTime, '%d/%m') AS DayMonth, "
				+ "SUM(Amount) AS TotalAmount "
				+ "FROM "
				+ "Movements "
				+ "WHERE " 
				+ "MovementTypeId = 4 "
				+ "AND Amount > 0 " 
				+ "AND MovementDateTime BETWEEN ? AND ? "
				+ "GROUP BY "
				+ "DayMonth " 
				+ "ORDER BY " 
				+ "DayMonth; ";
		try
		{
			db.setPreparedStatement(query);
			db.getPreparedStatement().setString(1, startDate.toString());
			db.getPreparedStatement().setString(2, endDate.toString());
			rs = db.getPreparedStatement().executeQuery();

			while (rs.next())
			{
				// Si la key ya existe, el metodo PUT actualiza el value.
				transfersAmountByPeriod.put(rs.getString("DayMonth"),rs.getBigDecimal("TotalAmount"));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return transfersAmountByPeriod;
	}

}
