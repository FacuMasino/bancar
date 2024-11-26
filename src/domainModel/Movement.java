package domainModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Movement
{
	private int id;
	private LocalDateTime dateTime;
	private String details;
	private BigDecimal amount;
	private MovementType movementType;

	public Movement()
	{
		
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
}
