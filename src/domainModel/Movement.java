package domainModel;

import java.math.BigDecimal;
import java.sql.Date;

//Movement tiene los detalles propio del movimiento(unilateral) y la cuenta,
//ya sea de debito o acreditacion
/*
 Apertura de cuenta: genera 
 */

public class Movement {

	private int movementId;
	private Date movementDate;
	private String details;
	private BigDecimal amount;
	private MovementType movementType;
	private int accountId;

	public Movement()
	{
		
	}
	
	public Movement(int movementId, Date movementDate, int accountId, String details, BigDecimal amount) {

		this.movementId = movementId;
		this.movementDate = movementDate;
		this.accountId = accountId;
		this.details = details;
		this.amount = amount;
	}

	public int getMovementId() {
		return movementId;
	}

	public void setMovementId(int movementId) {
		this.movementId = movementId;
	}

	public Date getMovementDate() {
		return movementDate;
	}

	public void setMovementDate(Date movementDate) {
		this.movementDate = movementDate;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public MovementType getMovementType() {
		return movementType;
	}

	public void setMovementType(MovementType movementType) {
		this.movementType = movementType;
	}
}
