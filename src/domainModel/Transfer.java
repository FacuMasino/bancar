package domainModel;

import java.math.BigDecimal;
import java.sql.Date;

public class Transfer
{
	private int transferId;
	private String senderCbu;
    private String recipientCbu;
    private Date creationDate;
    private String details;
    private BigDecimal amount;

    public Transfer()
	{
		
	}
    
    public String getSenderCbu()
	{
		return senderCbu;
	}

	public void setSenderCbu(String senderCbu)
	{
		this.senderCbu = senderCbu;
	}

	public String getRecipientCbu()
	{
		return recipientCbu;
	}

	public void setRecipientCbu(String recipientCbu)
	{
		this.recipientCbu = recipientCbu;
	}

	public Date getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Date creationDate)
	{
		this.creationDate = creationDate;
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
	
	public int getTransferId()
	{
		return transferId;
	}

	public void setTransferId(int transferId)
	{
		this.transferId = transferId;
	}
}
