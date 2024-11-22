package domainModel;

public class Country implements Identifiable
{
	private int id;
	private String name;
	
	public Country()
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
    	return "Country: [" +
				"id=" + getId() +
				", name=" + getName() +
				"]";
	}
}
