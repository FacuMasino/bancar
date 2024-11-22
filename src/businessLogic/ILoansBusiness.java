package businessLogic;

import java.util.ArrayList;

import domainModel.Client;
import domainModel.Loan;
import exceptions.BusinessException;

public interface ILoansBusiness
{
	public boolean create(Loan loan) throws BusinessException;
	public Loan read(int loanId) throws BusinessException;
	public boolean update(Loan loan, boolean isApproving) throws BusinessException;
	public ArrayList<Loan> list() throws BusinessException;
	public ArrayList<Loan> listByClient(Client client) throws BusinessException;
	public boolean payLoan(Loan loan, int installmentId) throws BusinessException;
}
