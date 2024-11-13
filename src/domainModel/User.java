package domainModel;

public class User
{
	private int userId;
	private String username;
	private String password;
	private Role role;

	public User()
	{
		
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

	public Role getRole()
	{
		return role;
	}

	public void setRole(Role role)
	{
		this.role = role;
	}
	
	@Override
	public String toString()
	{
    	return "userId: " + getUserId() +
    			" username: " + getUsername() +
    			" password: " + getPassword() +
    			" role: [" + getRole().toString() +
    			"]";
	}
}
