package dataAccessImpl;

import java.sql.SQLException;
import java.sql.Timestamp;
import dataAccess.ITransfersDao;
import domainModel.Movement;

public class TransfersDao implements ITransfersDao
{
	private Database db;
	
	public TransfersDao()
	{
		db = new Database();
	}

	@Override
	public int create(
			Movement movement,
			int originAccountId,
			int destinationAccountId)
					throws SQLException
	{
		try
		{
			db.setCallableStatement("{CALL generate_transfer(?, ?, ?, ?, ?, ?, ?)}");
			setParameters(movement, originAccountId, destinationAccountId);
			db.getCallableStatement().executeUpdate();
			movement.setId(db.getCallableStatement().getInt(1));
			return movement.getId();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}

	private void setParameters(
			Movement movement,
			int originAccountId,
			int destinationAccountId)
					throws SQLException
	{
		db.getCallableStatement().registerOutParameter(1, java.sql.Types.INTEGER);
		db.getCallableStatement().setTimestamp(2, Timestamp.valueOf(movement.getDateTime()));
		db.getCallableStatement().setString(3, movement.getDetails());
		db.getCallableStatement().setBigDecimal(4, movement.getAmount());
		db.getCallableStatement().setInt(5, movement.getMovementType().getId());
		db.getCallableStatement().setInt(6, originAccountId);
		db.getCallableStatement().setInt(7, destinationAccountId);
	}
}
