package dataAccess;

import java.sql.SQLException;
import java.util.ArrayList;
import domainModel.Movement;

public interface IMovementsDao
{
	public boolean create(Movement movement) throws SQLException;
	public Movement read(int movementId) throws SQLException;
	public boolean update(Movement movemen) throws SQLException;
	public boolean delete(int movementId) throws SQLException;
	public ArrayList<Movement> list() throws SQLException;
	public int getId(Movement movement) throws SQLException;
	public ArrayList<Movement> listByIdAccount(int accountId) throws SQLException;
}
