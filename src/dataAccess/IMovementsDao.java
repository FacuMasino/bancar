package dataAccess;

import java.sql.SQLException;
import java.util.ArrayList;
import domainModel.Movement;

public interface IMovementsDao
{
	public int create(Movement movement, int accountId) throws SQLException;
	public Movement read(int movementId) throws SQLException;
	public ArrayList<Movement> list(int accountId) throws SQLException;
}
