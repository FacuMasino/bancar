package businessLogic;

import java.math.BigDecimal;
import java.util.List;

import domainModel.Client;
import domainModel.Loan;
import domainModel.LoanStatus;
import domainModel.LoanType;
import exceptions.BusinessException;

public interface ILoansBusiness
{
	public boolean create(Loan loan) throws BusinessException;
	public Loan read(int loanId) throws BusinessException;
	public boolean update(Loan loan) throws BusinessException;
	public boolean approve(Loan loan) throws BusinessException;
	public List<Loan> list() throws BusinessException;
	public List<Loan> list(Client client) throws BusinessException;
	public BigDecimal calcOutstandingBalance(Loan auxLoan);
	public boolean payLoan(Loan loan, int installmentId) throws BusinessException;
	public List<Loan> filter(LoanStatus loanStatus, List<Loan> list) throws BusinessException;
	public List<Loan> filter(LoanType loanType, List<Loan> list) throws BusinessException;
}
