package businessLogic;

import java.util.List;

import domainModel.LoanType;
import exceptions.BusinessException;

public interface ILoanTypesBusiness
{
	public List<LoanType> list() throws BusinessException;
}
