package dataAccess;

import java.sql.SQLException;
import java.util.ArrayList;
import domainModel.MovementType;

public interface IMovementTypesDao
{
	public MovementType read(int movementTypeId) throws SQLException;
	public ArrayList<MovementType> list() throws SQLException;
	public int findId(MovementType movementType) throws SQLException;
}
