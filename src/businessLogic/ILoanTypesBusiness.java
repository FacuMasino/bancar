package businessLogic;

import java.util.ArrayList;
import domainModel.LoanType;
import exceptions.BusinessException;

public interface ILoanTypesBusiness
{
	public ArrayList<LoanType> list() throws BusinessException;
}
