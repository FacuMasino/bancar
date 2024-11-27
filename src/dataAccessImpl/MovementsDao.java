package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import dataAccess.IMovementsDao;
import domainModel.Movement;

public class MovementsDao implements IMovementsDao
{
	private Database db;
	private MovementTypesDao movementTypesDao;

	public MovementsDao()
	{
		db = new Database();
		movementTypesDao = new MovementTypesDao();
	}

	@Override
	public int create(Movement movement, int accountId) throws SQLException
	{
		try
		{
			db.setCallableStatement("{CALL insert_movement(?, ?, ?, ?, ?, ?)}");
			setParameters(movement, accountId);
			db.getCallableStatement().executeUpdate();
			return db.getCallableStatement().getInt(1);
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public Movement read(int movementId) throws SQLException
	{
		Movement movement = new Movement();
		ResultSet rs;
		
		try
		{
			db.setPreparedStatement("SELECT * FROM Movements WHERE MovementId = ?;");
			db.getPreparedStatement().setInt(1, movementId);
			rs = db.getPreparedStatement().executeQuery();
			
			if(!rs.next())
			{
				return null;
			}
			
			assignResultSet(movement, rs);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		
		return movement;
	}

	@Override
	public ArrayList<Movement> list(int accountId) throws SQLException
	{
		ResultSet rs;
		ArrayList<Movement> movements = new ArrayList<Movement>();
		
		try
		{
			db.setPreparedStatement("SELECT * FROM Movements where AccountId =?;");
			db.getPreparedStatement().setInt(1, accountId);
			rs = db.getPreparedStatement().executeQuery();
			
			while(rs.next())
			{
				Movement movement = new Movement();
				assignResultSet(movement, rs);
				movements.add(movement);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return movements;
	}
	
	public ArrayList<Movement> listFilter(int accountId, int movTypeId) throws SQLException
	{
		ResultSet rs;
		ArrayList<Movement> movements = new ArrayList<Movement>();
		
		try
		{
			db.setPreparedStatement("SELECT * FROM Movements where AccountId =? AND MovementTypeId =? ;");
			db.getPreparedStatement().setInt(1, accountId);
			db.getPreparedStatement().setInt(2, movTypeId);
			rs = db.getPreparedStatement().executeQuery();
			
			while(rs.next())
			{
				Movement movement = new Movement();
				assignResultSet(movement, rs);
				movements.add(movement);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return movements;
	}
	
	private void setParameters(Movement movement, int accountId) throws SQLException
	{
		db.getCallableStatement().registerOutParameter(1, java.sql.Types.INTEGER);
		db.getCallableStatement().setTimestamp(2, Timestamp.valueOf(movement.getDateTime()));
		db.getCallableStatement().setString(3, movement.getDetails());
		db.getCallableStatement().setBigDecimal(4, movement.getAmount());
		db.getCallableStatement().setInt(5, movement.getMovementType().getId());
		db.getCallableStatement().setInt(6, accountId);
	}
	
	private void assignResultSet(Movement movement, ResultSet rs) throws SQLException
	{
		try
		{
			movement.setId(rs.getInt("MovementId"));
			movement.setDateTime(rs.getTimestamp("MovementDateTime").toLocalDateTime());
			movement.setDetails(rs.getString("Details"));
			movement.setAmount(rs.getBigDecimal("Amount"));

			int movementTypeId = rs.getInt("MovementTypeId");
			movement.setMovementType(movementTypesDao.read(movementTypeId));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
}