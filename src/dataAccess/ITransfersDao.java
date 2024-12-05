package dataAccess;

import java.sql.SQLException;
import domainModel.Movement;

public interface ITransfersDao
{
	public int create(Movement movement, int originAccountId, int destinationAccountId) throws SQLException;
}
