package utils;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domainModel.Message;
import domainModel.Message.MessageType;

public class Helper
{
	public static void redirect(
	        String url,
	        HttpServletRequest request,
	        HttpServletResponse response)
	        throws ServletException, IOException
	{
	    RequestDispatcher rd = request.getRequestDispatcher(url);
	    rd.forward(request, response);
	}
	
	public static void setReqMessage(HttpServletRequest request, String message, MessageType type)
	{
		request.setAttribute("message", 
				new Message(message,type));
	}
}
