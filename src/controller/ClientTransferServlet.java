package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import businessLogicImpl.AccountsBusiness;
import businessLogicImpl.TransfersBusiness;
import domainModel.Account;
import domainModel.Client;
import domainModel.Movement;
import exceptions.BusinessException;
import utils.Helper;

@WebServlet(urlPatterns = { "/Client/Transfer", "/Client/Transfer/" })
public class ClientTransferServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private TransfersBusiness transfersBusiness;
	private AccountsBusiness accountsBusiness;
	private Client sessionClient;
	private Movement movement;
	private String originAccountId;
	private String destinationAccountCbu;
	private String transferAmount;
	private String transferType;
	private String transferDescription;
	private int originAccId;
	private int destinationAccId;
    
    public ClientTransferServlet()
    {
        super();
        transfersBusiness = new TransfersBusiness();
        accountsBusiness = new AccountsBusiness();
        movement = new Movement();
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
		mapControls(request, response);
		confirmTransfer();
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
	
	private void mapControls(
			HttpServletRequest request, HttpServletResponse response)
	{
		originAccountId = request.getParameter("originAccountId");

		if (originAccountId != null && !originAccountId.isEmpty())
		{
			originAccId = Integer.parseInt(originAccountId);
		}
		
		destinationAccountCbu = request.getParameter("destinationAccountCbu");
		
		if (destinationAccountCbu != null && !destinationAccountCbu.isEmpty())
		{
			destinationAccId = accountsBusiness.findId(destinationAccountCbu);
		}

		transferAmount = request.getParameter("transferAmount");
		
		if (transferAmount != null && !transferAmount.isEmpty())
		{
			try
			{
				movement.setAmount(new BigDecimal(transferAmount));
	        }
			catch (NumberFormatException e)
			{
	            System.out.println("El monto no tiene un formato válido.");
	        }
		}

		transferDescription = request.getParameter("transferDescription");
		
		if (transferDescription != null && !transferDescription.isEmpty())
		{
			movement.setDetails(transferDescription + " - ");
		}

		transferType = request.getParameter("transferType");
		
		if (transferType != null && !transferType.isEmpty())
		{
			movement.setDetails(movement.getDetails() + transferType);
		}
	}
	
	private void confirmTransfer()
	{
		try
		{
			transfersBusiness.create(movement, originAccId, destinationAccId);
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
		}
	}
}
