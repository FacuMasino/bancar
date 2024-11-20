package domainModel;

public class LoanType 
{
	private int id;
	private String name;
	private String description;
	
	public LoanType() 
	{
		
	}
	
	public String getDescription() 
	{
		return description;
	}
	
	public void setDescription(String loanTypeDescription)
	{
		this.description = loanTypeDescription;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String loanTypeName)
	{
		this.name = loanTypeName;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int loanTypeId) 
	{
		this.id = loanTypeId;
	}

	@Override
	public String toString()
	{
		return "LoanType [loanTypeId=" + id + ", loanTypeName="
				+ name + ", loanTypeDescription=" + description
				+ "]";
	}
}
