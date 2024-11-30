package dataAccess;

import java.sql.SQLException;
import java.util.ArrayList;
import domainModel.Movement;

public interface IMovementsDao
{
	public int create(Movement movement, int accountId) throws SQLException;
	public Movement read(int movementId) throws SQLException;
	public ArrayList<Movement> list(int accountId) throws SQLException;
	public ArrayList<Movement> list(int accountId, int movTypeId) throws SQLException;
	public ArrayList<Movement> search(int accountId, ArrayList<Movement> movements,String searchInput)  throws SQLException;
	ArrayList<Movement> list(String transactionId) throws SQLException;
 }