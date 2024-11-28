package businessLogic;

import domainModel.Account;
import domainModel.Installment;
import exceptions.BusinessException;

public interface IInstallmentsBusiness
{
	public Installment read(int installmentId) throws BusinessException;
	public boolean update(Installment installment) throws BusinessException;
	public boolean generate(int loanId) throws BusinessException;
	public boolean payInstallment(Installment installment, Account debitAccount) throws BusinessException;
}
