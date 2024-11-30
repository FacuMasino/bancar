package businessLogic;

import java.util.ArrayList;
import domainModel.Movement;
import exceptions.BusinessException;

public interface IMovementsBusiness
{
	public boolean create(Movement movement, int accountId) throws BusinessException;
	public Movement read(int movementId) throws BusinessException;
	public ArrayList<Movement> list(int accountId) throws BusinessException;
	public ArrayList<Movement> list(int accountId, int movTypeId) throws BusinessException;
	public ArrayList<Movement> list(String transactionId) throws BusinessException;
	public ArrayList<Movement> filterByDate (ArrayList<Movement> movements, String filterDate) throws BusinessException;
	public ArrayList<Movement> search(int accountId, ArrayList<Movement> movements, String searchInput) throws BusinessException;
	public String getShortTrxId(String fullTransactionId);
	public String generateTrxId();
}