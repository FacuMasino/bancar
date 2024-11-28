package domainModel;

public enum LoanStatusEnum
{
	PENDING(1),
	APPROVED(2),
	FINISHED(3),
	REJECTED(4);
	
	private final int id;
	
	LoanStatusEnum(int id)
	{
		this.id = id;
	}
	
	public int getId()
	{
		return id;
	}
}
