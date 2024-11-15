package businessLogic;

import java.util.ArrayList;
import domainModel.Loan;
import exceptions.BusinessException;

public interface ILoansBusiness
{
	public boolean create(Loan loan) throws BusinessException;
	public Loan read(int loanId) throws BusinessException;
	public boolean update(Loan loan) throws BusinessException;
	public boolean delete(int loanId) throws BusinessException;
	public ArrayList<Loan> list() throws BusinessException;
	public int getId(Loan loan) throws BusinessException;
	public ArrayList<Loan> listByIdAccount(int accountId) throws BusinessException;
}
