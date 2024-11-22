package domainModel;

public class Province implements Identifiable
{
	private int id;
	private String name;

	public Province()
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
		return "Province: [" +
				"id=" + getId() +
				", name=" + getName() +
				"]";
	}
}
