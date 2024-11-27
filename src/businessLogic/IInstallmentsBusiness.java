package businessLogic;

import domainModel.Installment;
import exceptions.BusinessException;

public interface IInstallmentsBusiness
{
	public boolean update(Installment installment) throws BusinessException;
	public boolean generate(int loanId) throws BusinessException;
}
