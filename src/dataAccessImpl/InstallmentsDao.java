package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dataAccess.IInstallmentsDao;
import dataAccess.IMovementsDao;
import domainModel.Installment;
import domainModel.Movement;

public class InstallmentsDao implements IInstallmentsDao
{
	private Database db;
	private IMovementsDao movementsDao;
	
	public InstallmentsDao()
	{
		db = new Database();
		movementsDao = new MovementsDao();
	}

	@Override
	public boolean generate(int loanId) throws SQLException
	{
		int rows = 0;

		try
		{
			db.setCallableStatement("{CALL generate_installments(?,?)}");
			db.getCallableStatement().registerOutParameter(1, java.sql.Types.INTEGER);
			db.getCallableStatement().setInt(2,loanId);
			db.getCallableStatement().executeUpdate();
			rows = db.getCallableStatement().getInt(1);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}

		return (rows > 0);
	}
	
	@Override
	public Installment read(int installmentId) throws SQLException
	{
		ResultSet rsInstallment;
		
		try
		{
			db.setPreparedStatement("SELECT * FROM Installments WHERE InstallmentId = ?");
			db.getPreparedStatement().setInt(1, installmentId);
			rsInstallment = db.getPreparedStatement().executeQuery();

			if (!rsInstallment.next()) 
			{
				return null;
			}

			return getInstallment(rsInstallment);
  
		}
		catch (SQLException ex) 
		{
			ex.printStackTrace();
			throw ex;
		}
	}
	
	@Override
	public Installment read(Movement movement) throws SQLException
	{
		ResultSet rsInstallment;
		
		try
		{
			db.setPreparedStatement("SELECT * FROM Installments WHERE movementId = ?");
			db.getPreparedStatement().setInt(1, movement.getId());
			rsInstallment = db.getPreparedStatement().executeQuery();

			if (!rsInstallment.next()) 
			{
				return null;
			}

			return getInstallment(rsInstallment);
  
		}
		catch (SQLException ex) 
		{
			ex.printStackTrace();
			throw ex;
		}
	}
	
	@Override
	public boolean update(Installment installment) throws SQLException
	{
		int rows = 0;
		
		try
		{
			db.setPreparedStatement("UPDATE Installments set MovementId = ? WHERE InstallmentId = ?");
			db.getPreparedStatement().setInt(1, installment.getMovement().getId());
			db.getPreparedStatement().setInt(2, installment.getInstallmentId());
			rows = db.getPreparedStatement().executeUpdate();
			
			return (rows > 0);
		}
		catch (SQLException ex) 
		{
			ex.printStackTrace();
			throw ex;
		}
	}

	@Override
	public ArrayList<Installment> listByLoanId(int loanId) 
			throws SQLException
	{
		ArrayList<Installment> installments = new ArrayList<Installment>();
		ResultSet rsInstallment;
		
		try
		{
			db.setPreparedStatement("SELECT * FROM Installments WHERE loanId = ?");
			db.getPreparedStatement().setInt(1, loanId);
			rsInstallment = db.getPreparedStatement().executeQuery();

			while(rsInstallment.next())
			{
				installments.add(getInstallment(rsInstallment));
			}

			return installments;
  
		}
		catch (SQLException ex) 
		{
			ex.printStackTrace();
			throw ex;
		}
	}

	@Override
	public ArrayList<Installment> listPendingsByLoanId(int loanId)
			throws SQLException
	{
		ArrayList<Installment> installments = new ArrayList<Installment>();
		ResultSet rsInstallment;
		
		try
		{
			db.setPreparedStatement("SELECT * FROM Installments WHERE loanId = ? AND MovementId IS NULL");
			db.getPreparedStatement().setInt(1, loanId);
			rsInstallment = db.getPreparedStatement().executeQuery();

			while(rsInstallment.next())
			{
				installments.add(getInstallment(rsInstallment));
			}

			return installments;
  
		}
		catch (SQLException ex) 
		{
			ex.printStackTrace();
			throw ex;
		}
	}

	private Installment getInstallment(ResultSet rs)
			throws SQLException
	{
		Installment auxInstallment = new Installment();
		auxInstallment.setInstallmentId(rs.getInt("InstallmentId"));
		auxInstallment.setNumber(rs.getInt("InstallmentNumber"));
		auxInstallment.setAmount(rs.getBigDecimal("Amount"));
		auxInstallment.setPaymentDueDate(rs.getDate("PaymentDueDate"));
		auxInstallment.setLoanId(rs.getInt("LoanId"));
		
		if(rs.getInt("MovementId") != 0)
		{
			Movement auxMovement = new Movement();
			auxMovement = movementsDao.read(rs.getInt("MovementId"));
			auxInstallment.setMovement(auxMovement);
		}
		
		return auxInstallment;
	}
}
