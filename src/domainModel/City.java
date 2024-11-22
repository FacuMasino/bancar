package domainModel;

public class City implements Identifiable
{
	private int id;
	private String name;
	private String zipCode;

	public City()
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

	public String getZipCode()
	{
		return zipCode;
	}

	public void setZipCode(String zipCode)
	{
		this.zipCode = zipCode;
	}
	
	@Override
	public String toString()
	{
		return "City: [" +
				"id=" + getId() +
				", name=" + getName() +
				", zipCode=" + getZipCode() +
				"]";
	}
}
