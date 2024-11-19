package businessLogicImpl;

import java.util.ArrayList;
import businessLogic.IMovementsBusiness;
import domainModel.Movement;
import exceptions.BusinessException;

public class MovementsBusiness implements IMovementsBusiness
{

	@Override
	public boolean create(Movement movement) throws BusinessException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Movement read(int movementId) throws BusinessException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Movement movement) throws BusinessException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int movementId)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Movement> list() throws BusinessException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getId(Movement movement) throws BusinessException
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Movement> listByIdAccount(int accountId)
			throws BusinessException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
