package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

import dataAccess.ILoanStatusesDao;
import domainModel.LoanStatus;

public class LoanStatusesDao implements ILoanStatusesDao
{
	Database db;
	
	public LoanStatusesDao()
	{
		db = new Database();
	}

	@Override
	public LoanStatus read(int loanStatusId) throws SQLException
	{
		ResultSet rs;
		try
		{
			db.setPreparedStatement("SELECT * FROM LoanStatuses WHERE LoanStatusId = ?");
			db.getPreparedStatement().setInt(1, loanStatusId);
			rs = db.getPreparedStatement().executeQuery();
			
			if(!rs.next())
			{
				return null;
			}
			return getLoanStatus(rs);
			
		} catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
	
	private LoanStatus getLoanStatus(ResultSet rs) throws SQLException
	{
		try
		{
			LoanStatus auxLoanStatus = new LoanStatus();
			auxLoanStatus.setLoanStatusId(rs.getInt("LoanTypeId"));
			auxLoanStatus.setLoanStatusName(rs.getString("LoanTypeName"));
			
			return auxLoanStatus;
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}

}
