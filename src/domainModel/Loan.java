package domainModel;

import java.sql.Date;
import java.util.ArrayList;

public class Loan
{
	private int id;
    private Date creationDate;
    private int monthsLimit;
    private ArrayList<Installment> installments;

    public Loan()
	{
    	installments = new ArrayList<Installment>();
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public Date getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Date creationDate)
	{
		this.creationDate = creationDate;
	}

	public int getMonthsLimit()
	{
		return monthsLimit;
	}

	public void setMonthsLimit(int monthsLimit)
	{
		this.monthsLimit = monthsLimit;
	}
}
