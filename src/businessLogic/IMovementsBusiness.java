package businessLogic;

import java.util.ArrayList;
import domainModel.Movement;
import exceptions.BusinessException;

public interface IMovementsBusiness
{
	public boolean create(Movement movement) throws BusinessException;
	public Movement read(int movementId) throws BusinessException;
	public boolean update(Movement movement) throws BusinessException;
	public boolean delete(int movementId) throws BusinessException;
	public ArrayList<Movement> list() throws BusinessException;
	public int getId(Movement movement) throws BusinessException;
	public ArrayList<Movement> listByIdAccount(int accountId) throws BusinessException;
	
}
