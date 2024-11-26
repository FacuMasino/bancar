package dataAccess;

import java.sql.SQLException;
import domainModel.Movement;

public interface ITransfersDao
{
	public boolean create(
			Movement movement,
			int originAccountId,
			int destinationAccountId)
					throws SQLException;
}
