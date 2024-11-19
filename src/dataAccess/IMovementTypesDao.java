package dataAccess;

import java.sql.SQLException;
import java.util.ArrayList;

import domainModel.MovementType;

public interface IMovementTypesDao
{
	//public boolean create(MovementType movementTypeDao) throws SQLException;
	public MovementType read(int movementTypeId) throws SQLException;
	public MovementType readByName(String movementTypeName) throws SQLException;
	public ArrayList<MovementType> list() throws SQLException;
	public int getId(MovementType movementType) throws SQLException;
}
