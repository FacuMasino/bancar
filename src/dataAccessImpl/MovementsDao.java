package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import dataAccess.IMovementsDao;
import domainModel.Movement;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
			db.setCallableStatement("{CALL insert_movement(?,?, ?, ?, ?, ?, ?)}");
			setParameters(movement, accountId);
			db.getCallableStatement().executeUpdate();
			return db.getCallableStatement().getInt(1);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}

	@Override
	public Movement read(int movementId) throws SQLException
	{
		Movement movement = new Movement();
		ResultSet rs;

		try
		{
			db.setPreparedStatement(
					"SELECT * FROM Movements WHERE MovementId = ?;");
			db.getPreparedStatement().setInt(1, movementId);
			rs = db.getPreparedStatement().executeQuery();

			if (!rs.next())
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

	// Para obtener 2 movimientos con el mismo TransactionId (Transferencias)
	// TODO: No es lo más óptimo, si llegaramos debería haber una tabla de Transferencias 
	@Override
	public ArrayList<Movement> list(String transactionId) throws SQLException
	{
		ResultSet rs;
		ArrayList<Movement> movements = new ArrayList<Movement>();

		try
		{
			db.setPreparedStatement(
					"SELECT * FROM Movements where TransactionId = ? ;");
			db.getPreparedStatement().setString(1, transactionId);
			rs = db.getPreparedStatement().executeQuery();

			while (rs.next())
			{
				Movement movement = new Movement();
				assignResultSet(movement, rs);
				movements.add(movement);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}

		return movements;
	}
	
	@Override
	public ArrayList<Movement> list(int accountId) throws SQLException
	{
		ResultSet rs;
		ArrayList<Movement> movements = new ArrayList<Movement>();

		try
		{
			db.setPreparedStatement(
					"SELECT * FROM Movements where AccountId =?;");
			db.getPreparedStatement().setInt(1, accountId);
			rs = db.getPreparedStatement().executeQuery();

			while (rs.next())
			{
				Movement movement = new Movement();
				assignResultSet(movement, rs);
				movements.add(movement);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}

		return movements;
	}

	@Override
	public ArrayList<Movement> list(int accountId, int movTypeId)
			throws SQLException
	{
		ResultSet rs;
		ArrayList<Movement> movements = new ArrayList<Movement>();

		try
		{
			db.setPreparedStatement(
					"SELECT * FROM Movements WHERE AccountId = ? AND MovementTypeId = ?;");
			db.getPreparedStatement().setInt(1, accountId);
			db.getPreparedStatement().setInt(2, movTypeId);

			rs = db.getPreparedStatement().executeQuery();

			while (rs.next())
			{
				Movement movement = new Movement();
				assignResultSet(movement, rs);
				movements.add(movement);
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}

		return movements;
	}
	
	public ArrayList<Movement> search(int accountId, ArrayList<Movement> movements,
			String searchInput) throws SQLException
	{
		ResultSet rs;
	    ArrayList<Movement> searchedMovements = new ArrayList<Movement>();

	    try
		{
			db.setPreparedStatement(
					"SELECT * FROM Movements WHERE AccountId = ? AND (Details LIKE ? OR Amount LIKE ?);");
			
			String search = "%" + searchInput + "%";
			
			db.getPreparedStatement().setInt(1, accountId);
	        db.getPreparedStatement().setString(2, search); 
	        db.getPreparedStatement().setString(3, search);  

			rs = db.getPreparedStatement().executeQuery();

			while (rs.next())
			{
				Movement movement = new Movement();
				assignResultSet(movement, rs);
				searchedMovements.add(movement);
			}
		} 
	    catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}

		return searchedMovements;
	}
	
	

	private void setParameters(Movement movement, int accountId)
			throws SQLException
	{
		db.getCallableStatement().registerOutParameter(1,
				java.sql.Types.INTEGER);
		db.getCallableStatement().setString(2, movement.getTransactionId());
		db.getCallableStatement().setTimestamp(3,
				Timestamp.valueOf(movement.getDateTime()));
		db.getCallableStatement().setString(4, movement.getDetails());
		db.getCallableStatement().setBigDecimal(5, movement.getAmount());
		db.getCallableStatement().setInt(6, movement.getMovementType().getId());
		db.getCallableStatement().setInt(7, accountId);
	}

	private void assignResultSet(Movement movement, ResultSet rs)
			throws SQLException
	{
		try
		{
			movement.setId(rs.getInt("MovementId"));
			movement.setTransactionId(rs.getString("TransactionId"));
			movement.setDateTime(
					rs.getTimestamp("MovementDateTime").toLocalDateTime());
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