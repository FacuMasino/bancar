package domainModel;

public class Role implements Identifiable
{
	private int id;
	private String name;

	public Role()
	{
		
	}

	@Override
	public int getId()
	{
		return id;
	}

	@Override
	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	@Override
	public String toString()
	{	
    	return "Role: [" +
				"id=" + getId() +
				", name=" + getName() +
				"]";
	}
}
