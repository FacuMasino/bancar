package domainModel;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;

public class Loan 
{
	private int loanId;
	private Date creationDate;
	private BigDecimal requestedAmount;
	private BigDecimal interestRate;
	private ArrayList<Installment> installments;
	private ArrayList<Installment> pendingInstallments;
	private int installmentsQuantity;
	private LoanType loanType;
	private LoanStatus loanStatus;
	private Client client;
	private Account account;
	
	public Loan() 
	{

	}

	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
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

	public Account getAccount()
	{
		return account;
	}

	public void setAccount(Account account)
	{
		this.account = account;
	}


}
