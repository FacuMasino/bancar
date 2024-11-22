package domainModel;

import java.math.BigDecimal;
import java.sql.Date;

public class Installment
{
	private int installmentId;
	private int number;
	private BigDecimal amount;
	private Date paymentDueDate;
	private int loanId;
	private Movement movement;
	
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

	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
	
	public Date getPaymentDueDate()
	{
		return paymentDueDate;
	}

	public void setPaymentDueDate(Date paymentDueDate)
	{
		this.paymentDueDate = paymentDueDate;
	}

	public Movement getMovement()
	{
		return movement;
	}

	public void setMovement(Movement movement)
	{
		this.movement = movement;
	}

	@Override
	public String toString()
	{
		return "Installment [installmentId=" + installmentId + ", number="
				+ number + ", amount=" + amount + ", paymentDueDate="
				+ paymentDueDate + ", loanId=" + loanId + ", movement="
				+ movement + "]";
	}
	
}
