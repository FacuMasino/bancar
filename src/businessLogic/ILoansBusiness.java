package businessLogic;

import java.util.ArrayList;
import domainModel.Loan;
import exceptions.BusinessException;

public interface ILoansBusiness
{
	public boolean create(Loan loan) throws BusinessException;
	public Loan read(int loanId) throws BusinessException;
	public boolean update(Loan loan, boolean isApproving) throws BusinessException;
	public ArrayList<Loan> list() throws BusinessException;
	public ArrayList<Loan> listByIdAccount(int accountId) throws BusinessException;
}
