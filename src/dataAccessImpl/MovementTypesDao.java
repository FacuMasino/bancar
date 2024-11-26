package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dataAccess.IMovementTypesDao;
import domainModel.MovementType;

public class MovementTypesDao implements IMovementTypesDao
{
	private Database db;

	public MovementTypesDao()
	{
		db = new Database();
	}

	@Override
	public MovementType read(int movementTypeId) throws SQLException
	{
		MovementType movementType = new MovementType();
		ResultSet rs;
		
		try
		{
			db.setPreparedStatement("SELECT * FROM MovementTypes WHERE MovementTypeId = ?;");
			db.getPreparedStatement().setInt(1, movementTypeId);
			rs = db.getPreparedStatement().executeQuery();
			
			if(!rs.next())
			{
				return null;
			}
			
			assignResultSet(movementType, rs);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		
		return movementType;
	}

	@Override
	public ArrayList<MovementType> list() throws SQLException
	{
		ResultSet rs;
		ArrayList<MovementType> movementTypes = new ArrayList<MovementType>();
		
		try
		{
			db.setPreparedStatement("SELECT * FROM MovementTypes;");
			rs = db.getPreparedStatement().executeQuery();
			
			while(rs.next())
			{
				MovementType movementType = new MovementType();
				assignResultSet(movementType, rs);
				movementTypes.add(movementType);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return movementTypes;
	}
	
	@Override
	public int findId(MovementType movementType) throws SQLException
	{
		return findId(movementType.getName());
	}

	private int findId(String movementTypeName) throws SQLException
	{
		ResultSet rs;
		
		try
		{
			db.setPreparedStatement("SELECT MovementTypeId FROM MovementTypes WHERE MovementTypeName = ?;");
			db.getPreparedStatement().setString(1, movementTypeName);
			rs = db.getPreparedStatement().executeQuery();
			
			if(!rs.next())
			{
				return 0;
			}
			
			return rs.getInt("MovementTypeId");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
	
	private void assignResultSet(MovementType movementType, ResultSet rs) throws SQLException
	{
		try
		{
			movementType.setId(rs.getInt("MovementTypeId"));
			movementType.setName(rs.getString("MovementTypeName"));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
}
