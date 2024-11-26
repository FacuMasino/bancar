package businessLogic;

import java.util.List;

import domainModel.LoanStatus;
import exceptions.BusinessException;

public interface ILoanStatusesBusiness
{
	public List<LoanStatus> list() throws BusinessException;
	public LoanStatus read(int loanStatusId) throws BusinessException;
}
