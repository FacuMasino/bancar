package dataAccessImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class Database
{
	private static Connection connection;
	private PreparedStatement preparedStatement;
	private String host;
	private String user;
	private String pass;
	private String dbName;
	
	public Database()
	{
		configProperties();
	}

	public void close()
	{
		try 
		{
			connection.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void setPreparedStatement(String query) throws SQLException
	{
		setConnection();
		preparedStatement = connection.prepareStatement(query);
	}
	
	public PreparedStatement getPreparedStatement()
	{
		return preparedStatement;
	}
	
	private void setConnection()
	{
		try
		{
			callDriver();

			if(connection == null || connection.isClosed())
			{
				connection = DriverManager.getConnection(host + dbName, user, pass);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	
	private void configProperties()
	{
		Properties props = new Properties();

		try
		{
			InputStream is = Database.class.getClassLoader().getResourceAsStream("config.properties");
			
			if (is == null)
			{
		        throw new FileNotFoundException("El archivo 'config.properties' no se encontró en el directorio.");
		    }

			props.load(is);
			is.close();
			
			host = props.getProperty("db.host");
			user = props.getProperty("db.user");
			pass = props.getProperty("db.pass");
			dbName = props.getProperty("db.name");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void callDriver()
	{
		try
		{
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}