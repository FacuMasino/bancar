package domainModel;

public class City
{
	private int id;
	private String name;
	private String zipCode;

	public City()
	{
		
	}

	public int getId()
	{
		return id;
	}

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
		return "id: " + getId() + " name: " + getName();
	}
}
