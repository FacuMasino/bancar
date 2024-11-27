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
import businessLogicImpl.ClientsBusiness;
import businessLogicImpl.TransfersBusiness;
import domainModel.Account;
import domainModel.Client;
import domainModel.Movement;
import domainModel.MovementTypeEnum;
import exceptions.BusinessException;
import utils.Helper;

@WebServlet(urlPatterns = { "/Client/Transfer", "/Client/Transfer/" })
public class ClientTransferServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private TransfersBusiness transfersBusiness;
	private ClientsBusiness clientsBusiness;
	private AccountsBusiness accountsBusiness;
	private boolean success;
	private Movement movement;
	private Client originClient;
	private Client destinationClient;
	private Account originAccount;
	private Account destinationAccount;
	private String action;
	private String originAccountId;
	private String destinationAccountCbu;
	private String transferAmount;
	private String transferType;
	private String transferDescription;
    
    public ClientTransferServlet()
    {
        super();
        transfersBusiness = new TransfersBusiness();
        clientsBusiness = new ClientsBusiness();
        accountsBusiness = new AccountsBusiness();
    }

	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		action = request.getParameter("action");

		if (action == null || action.isEmpty())
		{
			fetchSessionClient(request, response);
			bindAccountsDDL(request, response);
			Helper.redirect("/WEB-INF/Transfer.jsp", request, response);
		}
	}

	protected void doPost(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		action = request.getParameter("action");

		switch (action)
		{
			case "goToConfirmation":
				fetchControls(request, response);
				mapControls(request, response);
				Helper.redirect("/WEB-INF/TransferConfirmation.jsp", request, response);
				break;
			case "goToDetails":
				confirmTransfer();
				mapControls(request, response);
				Helper.redirect("/WEB-INF/TransactionDetails.jsp", request, response);
				break;
		}
	}
	
	private void fetchSessionClient(
			HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession(false);

        if (session != null)
        {
        	originClient = (Client) session.getAttribute("client");
        }
	}
	
	private void bindAccountsDDL(
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			// TODO: Reemplazar accounts por sessionClient.getAccounts()
			ArrayList<Account> accounts;
			accounts = accountsBusiness.list(originClient.getId());
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
		originAccountId = request.getParameter("originAccountId");

		if (originAccountId != null && !originAccountId.isEmpty())
		{
			int origAccountId = Integer.parseInt(originAccountId);

			try
			{
				originAccount = accountsBusiness.read(origAccountId);
			}
			catch (BusinessException e)
			{
				e.printStackTrace();
			}
		}
		
		destinationAccountCbu = request.getParameter("destinationAccountCbu");
		
		if (destinationAccountCbu != null && !destinationAccountCbu.isEmpty())
		{
			try
			{
				int destAccountId = accountsBusiness.findId(destinationAccountCbu);
				destinationAccount = accountsBusiness.read(destAccountId);
			}
			catch (BusinessException e)
			{
				e.printStackTrace();
			}
			
			try
			{
				int destClientId = clientsBusiness.findClientId(destinationAccount);
				destinationClient = clientsBusiness.read(destClientId);
			}
			catch (BusinessException e)
			{
				e.printStackTrace();
			}
		}

		movement = new Movement();
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
	
	private void mapControls(
			HttpServletRequest request, HttpServletResponse response)
	{
		request.setAttribute("movement", movement);
		request.setAttribute("success", success);
		request.setAttribute("originClient", originClient);
		request.setAttribute("destinationClient", destinationClient);
		request.setAttribute("originAccount", originAccount);
		request.setAttribute("destinationAccount", destinationAccount);
		request.setAttribute("movementTypeId", MovementTypeEnum.TRANSFER.getId());
	}
	
	private void confirmTransfer()
	{
		try
		{
			success = transfersBusiness.create(
					movement, originAccount.getId(), destinationAccount.getId());
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
		}
	}
}
