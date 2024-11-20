package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dataAccess.IInstallmentsDao;
import domainModel.Installment;
import domainModel.Loan;

public class InstallmentsDao implements IInstallmentsDao
{
	Database db;
	
	public InstallmentsDao()
	{
		db = new Database();
	}

	@Override
	public boolean create(Installment installment) throws SQLException
	{
		int rows = 0;

		try
		{
			db.setPreparedStatement("{CALL insert_installment(?, ?, ?, ?)}");
			setParameters(installment);
			rows = db.getPreparedStatement().executeUpdate();
		} 
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}

		return (rows > 0);
	}

	private void setParameters(Installment installment) 
			throws SQLException
	{
		db.getPreparedStatement().setInt(1, installment.getNumber());
		db.getPreparedStatement().setBigDecimal(2, installment.getAmount());
		db.getPreparedStatement().setDate(3, installment.getPaymentDate());
		db.getPreparedStatement().setInt(4, installment.getLoanId());
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

	private Installment getInstallment(ResultSet rs)
			throws SQLException
	{
		Installment auxInstallment = new Installment();
		auxInstallment.setInstallmentId(rs.getInt("InstallmentId"));
		auxInstallment.setNumber(rs.getInt("InstallmentNumber"));
		auxInstallment.setAmount(rs.getBigDecimal("Amount"));
		auxInstallment.setPaymentDate(rs.getDate("PaymentDate"));
		auxInstallment.setLoanId(rs.getInt("LoanId"));
		return auxInstallment;
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

}
