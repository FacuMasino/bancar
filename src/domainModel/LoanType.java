package domainModel;

public class LoanType 
{
	private int loanTypeId;
	private String loanTypeName;
	private String loanTypeDescription;
	
	public LoanType() 
	{
		
	}
	
	public String getLoanTypeDescription() 
	{
		return loanTypeDescription;
	}
	
	public void setLoanTypeDescription(String loanTypeDescription)
	{
		this.loanTypeDescription = loanTypeDescription;
	}
	
	public String getLoanTypeName()
	{
		return loanTypeName;
	}
	
	public void setLoanTypeName(String loanTypeName)
	{
		this.loanTypeName = loanTypeName;
	}
	
	public int getLoanTypeId()
	{
		return loanTypeId;
	}
	
	public void setLoanTypeId(int loanTypeId) 
	{
		this.loanTypeId = loanTypeId;
	}
}
