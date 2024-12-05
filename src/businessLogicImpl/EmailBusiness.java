package businessLogicImpl;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import dataAccessImpl.Database;
import domainModel.Client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmailBusiness {
	private String user;
	private String pass;
	
	public EmailBusiness()
	{
		configCredentials();
	}
	
	private void configCredentials()
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
			
			user = props.getProperty("email.user");
			pass = props.getProperty("email.pass");

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
    public void sendWelcome(Client client) throws MessagingException {

        final String username = user;
        final String password = pass;

        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("bancar.sys.info@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(client.getEmail() + ", " + client.getEmail())
            );
            message.setSubject("Bienvenido a BancAr - Alta de Usuario");
            message.setText("Hola " + client.getFirstName() + ","
                    + "\n\n Te informamos que tu usuario fue dado de alta con éxito.\n\n" +
            		"Estos son los datos para acceder a tu Home Banking:\n"+
                    "- Usuario: " + client.getUsername()  +
                    "\n- Password: " + client.getPassword());

            Transport.send(message);

            System.out.println("Email enviado.");

        } catch (MessagingException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

}