package domainModel;

import java.util.ArrayList;

public class User
{
	private int userId;
	private String username;
	private String password;
	private ArrayList<Role> roles;

	public User()
	{
		setRoles(new ArrayList<Role>());
	}
	
	public User(String username, String password)
	{
		this.username = username;
		this.password = password;
	}

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
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

	public ArrayList<Role> getRoles()
	{
		return roles;
	}

	public void setRoles(ArrayList<Role> roles)
	{
		this.roles = roles;
	}
}
