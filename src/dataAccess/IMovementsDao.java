package dataAccess;

import java.sql.SQLException;
import java.util.ArrayList;
import domainModel.Movement;

public interface IMovementsDao
{
	public int create(Movement movement, int accountId) throws SQLException;
	public Movement read(int movementId) throws SQLException;
	public ArrayList<Movement> list(int accountId) throws SQLException;
	public ArrayList<Movement> listFilter(int accountId, int movTypeId) throws SQLException;
	public ArrayList<Movement> filterByDate (ArrayList<Movement> movements, String filterDate) throws SQLException;
}