package businessLogic;

import java.util.ArrayList;
import domainModel.Movement;
import exceptions.BusinessException;

public interface IMovementsBusiness
{
	public boolean create(Movement movement, int accountId) throws BusinessException;
	public Movement read(int movementId) throws BusinessException;
	public ArrayList<Movement> list(int accountId) throws BusinessException;
	public ArrayList<Movement> listFilter(int accountId, int movTypeId) throws BusinessException;
	public ArrayList<Movement> filterByDate (ArrayList<Movement> movements, String filterDate) throws BusinessException;
	public ArrayList<Movement> filterBySearch(int accountId, ArrayList<Movement> movements, String searchInput) throws BusinessException;

}