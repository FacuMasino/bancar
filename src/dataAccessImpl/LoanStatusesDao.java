package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

	@Override
	public List<LoanStatus> list() throws SQLException
	{
		ResultSet rs;
		List<LoanStatus> loanStatusList = new ArrayList<LoanStatus>();
		
		try
		{
			db.setPreparedStatement("SELECT * FROM LoanStatuses");
			rs = db.getPreparedStatement().executeQuery();
			
			while(rs.next())
			{
				loanStatusList.add(getLoanStatus(rs));
			}
			
		} catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		
		return loanStatusList;
	}

	private LoanStatus getLoanStatus(ResultSet rs) throws SQLException
	{
		try
		{
			LoanStatus auxLoanStatus = new LoanStatus();
			auxLoanStatus.setId(rs.getInt("LoanStatusId"));
			auxLoanStatus.setName(rs.getString("LoanStatusName"));
			
			return auxLoanStatus;
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
}
