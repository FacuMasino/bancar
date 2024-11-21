package domainModel;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;

public class Loan 
{
	private int loanId;
	private int clientId;
	private Date creationDate;
	private BigDecimal requestedAmount;
	private BigDecimal interestRate;
	private ArrayList<Installment> installments;
	private ArrayList<Installment> pendingInstallments;
	private int installmentsQuantity;
	private LoanType loanType;
	private LoanStatus loanStatus;
	private int accountId;

	public Loan() 
	{

	}

	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
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

	public int getAccountId()
	{
		return accountId;
	}

	public void setAccountId(int accountId)
	{
		this.accountId = accountId;
	}

	public int getInstallmentsQuantity()
	{
		return installmentsQuantity;
	}

	public void setInstallmentsQuantity(int installmentsQuantity)
	{
		this.installmentsQuantity = installmentsQuantity;
	}

	
	
	public ArrayList<Installment> getPendingInstallments()
	{
		return pendingInstallments;
	}

	public void setPendingInstallments(ArrayList<Installment> pendingInstallments)
	{
		this.pendingInstallments = pendingInstallments;
	}

	@Override
	public String toString()
	{
		return "Loan [loanId=" + loanId + ", clientId=" + clientId
				+ ", creationDate=" + creationDate + ", requestedAmount="
				+ requestedAmount + ", interestRate=" + interestRate
				+ ", installments=" + installments + ", installmentsQuantity="
				+ installmentsQuantity + ", loanType=" + loanType
				+ ", loanStatus=" + loanStatus + ", accountId=" + accountId
				+ "]";
	}


}
