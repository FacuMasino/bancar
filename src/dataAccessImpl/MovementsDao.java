package dataAccessImpl;

import java.sql.SQLException;
import java.util.ArrayList;

import dataAccess.IMovementsDao;
import domainModel.Movement;

public class MovementsDao implements IMovementsDao
{

	@Override
	public boolean create(Movement movement) throws SQLException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Movement read(int movementId) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Movement movemen) throws SQLException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int movementId) throws SQLException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Movement> list() throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getId(Movement movement) throws SQLException
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Movement> listByIdAccount(int clientId) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}
}
