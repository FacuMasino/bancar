package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dataAccess.ILoanTypesDao;
import domainModel.LoanType;

public class LoanTypesDao implements ILoanTypesDao
{
	Database db;
	
	public LoanTypesDao()
	{
		db = new Database();
	}

	@Override
	public LoanType read(int loanTypeId) throws SQLException
	{
		ResultSet rs;
		try
		{
			db.setPreparedStatement("SELECT * FROM LoanTypes WHERE LoanTypeId = ?");
			db.getPreparedStatement().setInt(1, loanTypeId);
			rs = db.getPreparedStatement().executeQuery();
			
			if(!rs.next())
			{
				return null;
			}
			return getLoanType(rs);
			
		} catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}

	@Override
	public ArrayList<LoanType> list() throws SQLException
	{
		ArrayList<LoanType> loanTypesList = new ArrayList<LoanType>();
		ResultSet rs;
		
		try
		{
			db.setPreparedStatement("Select * from LoanTypes");
			rs = db.getPreparedStatement().executeQuery();
			
			while(rs.next())
			{
				loanTypesList.add(getLoanType(rs));
			}
			
			return loanTypesList;
			
		} catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}

	private LoanType getLoanType(ResultSet rs) throws SQLException
	{
		try
		{
			LoanType auxLoanType = new LoanType();
			auxLoanType.setId(rs.getInt("LoanTypeId"));
			auxLoanType.setName(rs.getString("LoanTypeName"));
			auxLoanType.setDescription(rs.getString("LoanTypeDescription"));
			
			return auxLoanType;
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
	
}
