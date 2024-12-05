package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import businessLogic.IAccountsBusiness;
import businessLogic.IClientsBusiness;
import businessLogic.ITransfersBusiness;
import businessLogicImpl.AccountsBusiness;
import businessLogicImpl.ClientsBusiness;
import businessLogicImpl.TransfersBusiness;
import domainModel.Account;
import domainModel.Client;
import domainModel.Movement;
import domainModel.Message.MessageType;
import exceptions.BusinessException;
import exceptions.InvalidFieldsException;
import utils.Helper;

@WebServlet(urlPatterns = { "/Client/Transfer", "/Client/Transfer/" })
public class ClientTransferServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private ITransfersBusiness transfersBusiness;
	private IClientsBusiness clientsBusiness;
	private IAccountsBusiness accountsBusiness;
	private boolean success;
	private Movement movement;
	private Client originClient;
	private Client destinationClient;
	private Account originAccount;
	private Account destinationAccount;
	private String action;
    
    public ClientTransferServlet()
    {
        super();
        transfersBusiness = new TransfersBusiness();
        clientsBusiness = new ClientsBusiness();
        accountsBusiness = new AccountsBusiness();
    	movement = new Movement();
    	originClient = new Client();
    	destinationClient = new Client();
    	originAccount = new Account();
    	destinationAccount = new Account();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		loadTransferPage(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		action = request.getParameter("action");

		switch (action)
		{
			case "goToConfirmation":
				goToConfirmation(request, response);
				break;

			case "goToDetails":
				goToDetails(request, response);
				break;
		}
	}
	
	private void goToDetails(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException
	{
		try
		{
			success = transfersBusiness.create(movement, originAccount, destinationAccount);
		}
		catch (BusinessException ex)
		{
			Helper.setReqMessage(req, ex.getMessage(), MessageType.ERROR);
		}

		setTransferAttributes(req, res);
		Helper.redirect("/WEB-INF/TransactionDetails.jsp", req, res);
	}
	
	private void goToConfirmation(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException
	{
		try
		{
			fetchControls(req, res);
			setTransferAttributes(req, res);
			
			if (!validate(req, res))
			{
				loadTransferPage(req, res);
				return;
			}
			
		}
		catch (BusinessException ex)
		{
			Helper.setReqMessage(req, ex.getMessage(), MessageType.ERROR);
			loadTransferPage(req, res);
			return;
		}
		
		Helper.redirect("/WEB-INF/TransferConfirmation.jsp", req, res);
	}
	
	private void loadTransferPage(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException
	{
		HttpSession session = req.getSession(false);
		originClient = (Client)session.getAttribute("client");
		
		try
		{
			bindAccountsDDL(req, res);
		}
		catch (BusinessException ex)
		{
			Helper.setReqMessage(req, ex.getMessage(), MessageType.ERROR);
		}

		Helper.redirect("/WEB-INF/Transfer.jsp", req, res);
	}
	
	private void bindAccountsDDL(HttpServletRequest request, HttpServletResponse response) 
			throws BusinessException
	{
		try
		{
			// TODO: Reemplazar accounts por sessionClient.getAccounts()
			ArrayList<Account> accounts;
			accounts = accountsBusiness.list(originClient.getId());
			request.setAttribute("accounts", accounts);
		}
		catch (BusinessException ex)
		{
			throw ex;
		}
	}
	
	private void fetchControls(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException, BusinessException
	{
		int originAccountId = Optional.ofNullable(
				req.getParameter("originAccountId")).map(Integer::parseInt).orElse(0);

		String destinationAccountCbu = Optional.ofNullable(
				req.getParameter("destinationAccountCbu")).orElse("");

		BigDecimal transferAmount = Optional.ofNullable(
				req.getParameter("transferAmount")).map(BigDecimal::new).orElse(BigDecimal.ZERO);
		
		String transferDescription = Optional.ofNullable(
				req.getParameter("transferDescription")).orElse("");
		
		String transferType = req.getParameter("transferType");
		
		try
		{
			originAccount = accountsBusiness.read(originAccountId);
			
			int destAccountId = accountsBusiness.findId(destinationAccountCbu);
			destinationAccount = accountsBusiness.read(destAccountId);
			
			int destClientId = clientsBusiness.findClientId(destinationAccount);
			destinationClient = clientsBusiness.read(destClientId);
			
			movement = new Movement();
			movement.setAmount(transferAmount);
			movement.setDetails(transferDescription);			
			movement.setDetails(movement.getDetails() + " (" + transferType + ")");
		}
		catch (BusinessException ex)
		{
			throw ex;
		}
	}
	
	private void setTransferAttributes(HttpServletRequest request, HttpServletResponse response)
	{
		request.setAttribute("movement", movement);
		request.setAttribute("success", success);
		request.setAttribute("originClient", originClient);
		request.setAttribute("destinationClient", destinationClient);
		request.setAttribute("originAccount", originAccount);
		request.setAttribute("destinationAccount", destinationAccount);
		request.setAttribute("isCurrent", true);
	}
	
	private boolean validate(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			transfersBusiness.validate(movement, originAccount, destinationAccount);
		}
		catch (InvalidFieldsException ex)
		{
			Helper.setReqErrorList(request, ex.getInvalidFields());
			return false;
		}
		catch (BusinessException ex)
		{
			Helper.setReqMessage(request, ex.getMessage(), MessageType.ERROR);
			ex.printStackTrace();
			return false;
		}

		return true;
	}
}
