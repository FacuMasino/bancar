package domainModel;

import java.util.ArrayList;

public class User
{
	int id;
	boolean isActive;
	String username;
	String password;
	ArrayList<Role> roles;

	public User()
	{
		roles = new ArrayList<Role>();
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public boolean isActive()
	{
		return isActive;
	}

	public void setActive(boolean isActive)
	{
		this.isActive = isActive;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
}
