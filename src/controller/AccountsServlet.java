package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import businessLogicImpl.AccountsBusiness;

@WebServlet("/AccountsServlet")
public class AccountsServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private AccountsBusiness accountsBusiness;

    public AccountsServlet()
    {
        super();
        accountsBusiness = new AccountsBusiness();
    }

	protected void doGet(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException
	{
		
	}

	protected void doPost(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException
	{
		doGet(request, response);
	}
	
	private void redirect(
	        String url,
	        HttpServletRequest request,
	        HttpServletResponse response)
	        throws ServletException, IOException
	{
	    RequestDispatcher rd = request.getRequestDispatcher(url);
	    rd.forward(request, response);
	}
}
