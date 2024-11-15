package domainModel;

public class LoanStatus 
{
	private int loanStatusId;
	private String loanStatusName;
	
	public LoanStatus() 
	{
		
	}
	
	public int getLoanStatusId() 
	{
		return loanStatusId;
	}
	
	public void setLoanStatusId(int loanStatusId)
	{
		this.loanStatusId = loanStatusId;
	}
	
	public String getLoanStatusName()
	{
		return loanStatusName;
	}
	
	public void setLoanStatusName(String loanStatusName) 
	{
		this.loanStatusName = loanStatusName;
	}
}
