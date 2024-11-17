package domainModel;

import java.sql.Date;
import java.util.ArrayList;

public class Client extends User implements Identifiable
{
	private int clientId;
	private boolean activeStatus;
	private String dni;
	private String cuil;
	private String firstName;
	private String lastName;
	private String sex;
	private String email;
	private String phone;
	private Date birthDate;
	private Country nationality;
	private Address address;
	private ArrayList<Account> accounts;
	private ArrayList<Loan> loans;
	
	public Client()
	{
		
	}
	
	@Override
	public int getId()
	{
		return getClientId();
	}

	@Override
	public void setId(int id)
	{
		setClientId(id);
	}

	public int getClientId()
	{
		return clientId;
	}

	public void setClientId(int clientId)
	{
		this.clientId = clientId;
	}

	public boolean getActiveStatus()
	{
		return activeStatus;
	}

	public void setActiveStatus(boolean activeStatus)
	{
		this.activeStatus = activeStatus;
	}

	public String getDni()
	{
		return dni;
	}

	public void setDni(String dni)
	{
		this.dni = dni;
	}

	public String getCuil()
	{
		return cuil;
	}

	public void setCuil(String cuil)
	{
		this.cuil = cuil;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getSex()
	{
		return sex;
	}

	public void setSex(String sex)
	{
		this.sex = sex;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public Date getBirthDate()
	{
		return birthDate;
	}

	public void setBirthDate(Date birthDate)
	{
		this.birthDate = birthDate;
	}

	public Country getNationality()
	{
		return nationality;
	}

	public void setNationality(Country nationality)
	{
		this.nationality = nationality;
	}

	public Address getAddress()
	{
		return address;
	}

	public void setAddress(Address address)
	{
		this.address = address;
	}

	public ArrayList<Account> getAccounts()
	{
		return accounts;
	}

	public void setAccounts(ArrayList<Account> accounts)
	{
		this.accounts = accounts;
	}
	
	public void setUser(User user)
	{
		if (user != null)
		{			
			this.setUserId(user.getUserId());
			this.setUsername(user.getUsername());
			this.setPassword(user.getPassword());
			this.setRole(user.getRole());
		}
	}

	@Override
	public String toString()
	{
		return super.toString() +
				" clientId: " + getClientId() +
				" activeStatus: " + getActiveStatus() +
				" dni: " + getDni() +
				" cuil: " + getCuil() +
				" firstName: " + getFirstName() +
				" lastName: " + getLastName() +
				" sex: " + getSex() +
				" email: " + getEmail() +
				" phone: " + getPhone() +
				" birthDate: " + getBirthDate() +
				" nationality: [" + getNationality().toString() +
				"] address: [" + getAddress().toString() +
				"]";
	}

	public ArrayList<Loan> getLoans() {
		return loans;
	}

	public void setLoans(ArrayList<Loan> loans) {
		this.loans = loans;
	}
}
