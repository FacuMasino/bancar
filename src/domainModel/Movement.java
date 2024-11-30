package domainModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Movement implements Comparable<Movement>
{
	private int id;
	private String transactionId;
	private LocalDateTime dateTime;
	private String details;
	private BigDecimal amount;
	private MovementType movementType;

	public Movement()
	{
		setDateTime(LocalDateTime.now());
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public LocalDateTime getDateTime()
	{
		return dateTime;
	}
	
	public String getFormattedDateTime()
	{
		return dateTime.format(
				DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
	}

	public void setDateTime(LocalDateTime dateTime)
	{
		this.dateTime = dateTime;
	}

	public String getDetails()
	{
		return details;
	}

	public void setDetails(String details)
	{
		this.details = details;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	public MovementType getMovementType()
	{
		return movementType;
	}

	public void setMovementType(MovementType movementType)
	{
		this.movementType = movementType;
	}
	
	public String getTransactionId()
	{
		return transactionId;
	}

	public void setTransactionId(String transactionId)
	{
		this.transactionId = transactionId;
	}

	@Override
	public String toString()
	{
		return "Movement: [" +
				"id=" + id +
				", dateTime=" + dateTime +
				", details=" + details +
				", amount=" + amount +
				", movementType=" + movementType +
				"]";
	}

	@Override
	public int compareTo(Movement movement)
	{
		// Orden por fecha de mayor a menor
		return movement.getDateTime().compareTo(this.getDateTime());
	}

}
