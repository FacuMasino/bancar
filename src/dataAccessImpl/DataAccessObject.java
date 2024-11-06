package dataAccessImpl;

public class DataAccessObject
{
	protected Database db;

	protected void callDriver()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
