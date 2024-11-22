package domainModel;

import java.math.BigDecimal;
import java.sql.Date;

public class Account
{
	private int id;
	private boolean activeStatus;
	private String cbu;
	private Date creationDate;
	private BigDecimal balance;
	private AccountType accountType;
	private Client client; // TODO: ¿Se usa en algún momento account.getClient()?
	private int clientId; // TODO: ¿Se usa en algún momento account.getClientId()?

	public Account()
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

	public boolean getActiveStatus()
	{
		return activeStatus;
	}

	public void setActiveStatus(boolean activeStatus)
	{
		this.activeStatus = activeStatus;
	}

	public String getCbu()
	{
		return cbu;
	}

	public void setCbu(String cbu)
	{
		this.cbu = cbu;
	}

	public Date getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Date creationDate)
	{
		this.creationDate = creationDate;
	}

	public BigDecimal getBalance()
	{
		return balance;
	}

	public void setBalance(BigDecimal balance)
	{
		this.balance = balance;
	}

	public AccountType getAccountType()
	{
		return accountType;
	}

	public void setAccountType(AccountType accountType)
	{
		this.accountType = accountType;
	}

	public Client getClient()
	{
		return client;
	}

	public void setClient(Client client)
	{
		this.client = client;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
}
