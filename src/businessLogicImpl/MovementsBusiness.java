package businessLogicImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import businessLogic.IMovementsBusiness;
import dataAccessImpl.MovementsDao;
import domainModel.Movement;
import exceptions.BusinessException;
import exceptions.SQLOperationException;

public class MovementsBusiness implements IMovementsBusiness
{
	private MovementsDao movementDao;
	
	
	public MovementsBusiness()
	{
		movementDao = new MovementsDao();
	}

	@Override
	public boolean create(Movement movement) throws BusinessException
	{
		// TODO pendiente la creacion del movimiento es vida
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
		try {
			return movementDao.listByIdAccount(accountId);
		} catch (SQLException ex) {
			throw new SQLOperationException();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException("Ocurrió un error desconocido al leer los movimientos...");
		}
	}

}
