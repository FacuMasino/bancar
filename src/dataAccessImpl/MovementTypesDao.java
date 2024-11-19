package dataAccessImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dataAccess.IMovementTypesDao;

import domainModel.MovementType;

public class MovementTypesDao implements IMovementTypesDao
{
	private Database db;
	
	

	public MovementTypesDao()
	{
		db = new Database();
	}
	/*
	@Override
	public boolean create(MovementType movementTypeDao) throws SQLException
	{
		// TODO Auto-generated method stub
		return false;
	}
	*/
	@Override
	public MovementType read(int movementTypeId) throws SQLException
	{
		ResultSet rsMovementType;
		// El negocio debe verificar que lo devuelto != null
		MovementType movementType = null;
		
		try {
			db.setPreparedStatement("Select * from MovementTypes where movementTypeId = ?");
			db.getPreparedStatement().setInt(1, movementTypeId);
			rsMovementType = db.getPreparedStatement().executeQuery();

			if (!rsMovementType.next())
				return movementType; // no se encontró, devuelve null

			movementType = getMovementType(rsMovementType);
		} catch (Exception ex) {

			ex.printStackTrace();
			throw ex;
		}

		return movementType;
	}

	@Override
	public MovementType readByName(String movementTypeName) throws SQLException
	{
		ResultSet rsMovementType;
		MovementType auxMovementType = null;

		try {
			db.setPreparedStatement("Select * from MovementTypes where MovementTypeName = ?");
			db.getPreparedStatement().setString(1, movementTypeName);
			rsMovementType = db.getPreparedStatement().executeQuery();

			if (!rsMovementType.next())
				return auxMovementType; // no se encontró, devuelve null

			auxMovementType = getMovementType(rsMovementType);
		} catch (Exception ex) {

			ex.printStackTrace();
			throw ex;
		}

		return auxMovementType;
	}

	@Override
	public ArrayList<MovementType> list() throws SQLException
	{
		ArrayList<MovementType> auxMovementTypeList = new ArrayList<MovementType>();
		ResultSet rsMovementType;

		try {
			db.setPreparedStatement("SELECT * from MovementTypes");
			rsMovementType = db.getPreparedStatement().executeQuery();

			while (rsMovementType.next()) {
				if (!rsMovementType.next())
					return auxMovementTypeList; // devuelve null si esta vacia

				MovementType auxMovementType = new MovementType();
				auxMovementType = getMovementType(rsMovementType);

				auxMovementTypeList.add(auxMovementType);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}

		return auxMovementTypeList;
	}

	@Override
	public int getId(MovementType movementType) throws SQLException
	{
		ResultSet rsMovementType;
		int auxId = 0;
		try {
			db.setPreparedStatement("SELECT MovementTypeId from MovementTypes where MovementTypeName = ?");
			db.getPreparedStatement().setString(1, movementType.getName());
			rsMovementType = db.getPreparedStatement().executeQuery();
			if (!rsMovementType.next()) {
				return auxId;
			}
			auxId = rsMovementType.getInt(1);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}

		return auxId;
	}
	
	private MovementType getMovementType(ResultSet rsMovementType) throws SQLException {
		MovementType auxMovementType = new MovementType();

		try {
			auxMovementType.setId(rsMovementType.getInt("MovementTypeId"));
			auxMovementType.setName(rsMovementType.getString("MovementTypeName"));
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		}

		return auxMovementType;
	}

}
