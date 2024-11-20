package domainModel;

import java.math.BigDecimal;
import java.sql.Date;

public class Installment
{
	private int installmentId;
	private int number;
	private BigDecimal amount;
	private Date paymentDate;
	private int loanId;

	public Installment()
	{
		
	}

	public int getInstallmentId() {
		return installmentId;
	}

	public void setInstallmentId(int installmentId) {
		this.installmentId = installmentId;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	@Override
	public String toString()
	{
		return "Installment [installmentId=" + installmentId + ", number="
				+ number + ", amount=" + amount + ", paymentDate=" + paymentDate
				+ ", loanId=" + loanId + "]";
	}
	
}
