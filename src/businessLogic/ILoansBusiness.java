package businessLogic;

import java.math.BigDecimal;
import java.util.List;

import domainModel.Account;
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
	public boolean reject(Loan loan) throws BusinessException;
	public int rejectAll(Client client) throws BusinessException;
	public List<Loan> list() throws BusinessException;
	public List<Loan> list(Client client) throws BusinessException;
	public BigDecimal calcOutstandingBalance(Loan auxLoan);
	public boolean payLoan(Loan loan, int installmentId, Account debitAccount) throws BusinessException;
	public List<Loan> filter(LoanStatus loanStatus, List<Loan> list) throws BusinessException;
	public List<Loan> filter(LoanType loanType, List<Loan> list) throws BusinessException;
	public boolean currentLoans (Client client) throws BusinessException;
	public BigDecimal getRequestedAmount(List<Loan> loans);
	
}