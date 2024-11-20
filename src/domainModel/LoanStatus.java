package domainModel;

public class LoanStatus 
{
	private int id;
	private String name;
	
	public LoanStatus() 
	{
		
	}
	
	public int getId() 
	{
		return id;
	}
	
	public void setId(int loanStatusId)
	{
		this.id = loanStatusId;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String loanStatusName) 
	{
		this.name = loanStatusName;
	}

	@Override
	public String toString()
	{
		return "LoanStatus [loanStatusId=" + id + ", loanStatusName="
				+ name + "]";
	}
	
}

