package domainModel;

public class Address implements Identifiable
{    
    private int id;
    private String streetName;
    private String streetNumber;
    private String flat;
    private String details;
    private City city;
    private Province province;
    private Country country;
    
    public Address()
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

    public String getStreetName()
    {
        return streetName;
    }

    public void setStreetName(String streetName)
    {
        this.streetName = streetName;
    }

    public String getStreetNumber()
    {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber)
    {
        this.streetNumber = streetNumber;
    }

    public String getFlat()
    {
        return flat;
    }

    public void setFlat(String flat)
    {
        this.flat = flat;
    }

    public String getDetails()
    {
        return details;
    }
    
    public String getDetails(int characters)
    {
    	if (this.details == null)
    	{
    		return "null";
    	}
    	
    	if (this.details.length() <= characters)
    	{
    		return this.details;
    	}

    	return details.substring(0, characters) + "...";
    }

    public void setDetails(String details)
    {
        this.details = details;
    }

    public City getCity()
    {
        return city;
    }

    public void setCity(City city)
    {
        this.city = city;
    }

    public Province getProvince()
    {
        return province;
    }

    public void setProvince(Province province)
    {
        this.province = province;
    }

    public Country getCountry()
    {
        return country;
    }

    public void setCountry(Country country)
    {
        this.country = country;
    }
    
    @Override
	public String toString()
	{
    	return "Address: [" +
				"id=" + getId() +
    			", streetName=" + getStreetName() +
    			", streetNumber=" + getStreetNumber() +
    			", flat=" + getFlat() +
    			", details=" + getDetails(5) +
    			", city=" + city +
    			", province=" + province +
    			", country=" + country +
    			"]"; 
	}
}
