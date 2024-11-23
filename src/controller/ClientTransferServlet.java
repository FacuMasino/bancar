package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import businessLogicImpl.AccountsBusiness;
import domainModel.Account;
import domainModel.Client;
import exceptions.BusinessException;
import utils.Helper;

@WebServlet(urlPatterns = { "/Client/Transfer", "/Client/Transfer/" })
public class ClientTransferServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private AccountsBusiness accountsBusiness;
	private Client sessionClient;
	private Account originAccount;
	private Account destinationAccount;
	private String selectedAccountId;
	private String destinationAccountCbu;
    
    public ClientTransferServlet()
    {
        super();
        accountsBusiness = new AccountsBusiness();
    }

	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		fetchSessionClient(request, response);
		bindAccountsDDL(request, response);
		Helper.redirect("/WEB-INF/Transfer.jsp", request, response);
	}

	protected void doPost(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{		
		fetchControls(request, response);
		doGet(request, response);
	}
	
	private void fetchSessionClient(
			HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession(false);

        if (session != null)
        {
        	sessionClient = (Client) session.getAttribute("client");
        }
	}
	
	private void bindAccountsDDL(
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			// TODO: Reemplazar accounts por sessionClient.getAccounts()
			ArrayList<Account> accounts;
			accounts = accountsBusiness.list(sessionClient.getId());
			request.setAttribute("accounts", accounts);
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
		}
	}
	
	private void fetchControls(
			HttpServletRequest request, HttpServletResponse response)
	{
		selectedAccountId = request.getParameter("selectedAccountId");
		destinationAccountCbu = request.getParameter("destinationAccountCbu");
		
		if (selectedAccountId != null && !selectedAccountId.isEmpty())
		{
			try
			{
				originAccount = accountsBusiness.read(Integer.parseInt(selectedAccountId));
				System.out.println(originAccount.toString()); // test
			}
			catch (NumberFormatException e)
			{
				e.printStackTrace();
			}
			catch (BusinessException e)
			{
				e.printStackTrace();
			}
		}
		
		if (destinationAccountCbu != null && !destinationAccountCbu.isEmpty())
		{
			try
			{
				int destinationAccountId = accountsBusiness.findId(destinationAccountCbu);
				destinationAccount = accountsBusiness.read(destinationAccountId);
				System.out.println(destinationAccount.toString()); // test
			}
			catch (NumberFormatException e)
			{
				e.printStackTrace();
			}
			catch (BusinessException e)
			{
				e.printStackTrace();
			}
		}
	}
}
