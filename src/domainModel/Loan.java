package domainModel;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;

public class Loan 
{
	private int LoanId;
	private int clientId;
	private Date creationDate;
	private BigDecimal requestedAmount;
	private BigDecimal interestRate;
	private int monthsLimit;
	private ArrayList<Installment> installments;
	private LoanType loanType;
	private LoanStatus loanStatus;

	public Loan() 
	{

	}

	public int getLoanId() {
		return LoanId;
	}

	public void setLoanId(int loanId) {
		LoanId = loanId;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public BigDecimal getRequestedAmount() {
		return requestedAmount;
	}

	public void setRequestedAmount(BigDecimal requestedAmount) {
		this.requestedAmount = requestedAmount;
	}

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}

	public int getMonthsLimit() {
		return monthsLimit;
	}

	public void setMonthsLimit(int monthsLimit) {
		this.monthsLimit = monthsLimit;
	}

	public ArrayList<Installment> getInstallments() {
		return installments;
	}

	public void setInstallments(ArrayList<Installment> installments) {
		this.installments = installments;
	}

	public LoanType getLoanType() {
		return loanType;
	}

	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}

	public LoanStatus getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(LoanStatus loanStatus) {
		this.loanStatus = loanStatus;
	}

}
