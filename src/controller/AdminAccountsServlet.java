package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import businessLogicImpl.AccountsBusiness;
import businessLogicImpl.ClientsBusiness;
import businessLogicImpl.LoansBusiness;
import domainModel.Account;
import domainModel.AccountType;
import domainModel.Client;
import domainModel.Loan;
import domainModel.Message.MessageType;
import exceptions.BusinessException;
import utils.Helper;

@WebServlet(urlPatterns = { "/Admin/Accounts", "/Admin/Accounts/" })
public class AdminAccountsServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private AccountsBusiness accountsBusiness;
	private ClientsBusiness clientsBusiness;
	private LoansBusiness loansBusiness;

	public AdminAccountsServlet()
	{
		super();
		accountsBusiness = new AccountsBusiness();
		clientsBusiness = new ClientsBusiness();
		loansBusiness = new LoansBusiness();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		int clientId = Optional.ofNullable(request.getParameter("clientId"))
				.map(Integer::parseInt)
				.orElse(0);
		if(clientId == 0)
		{
			response.sendRedirect("Clients");
			return;
		}

		viewClientAccounts(request, response, clientId);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		String action = request.getParameter("action");

		switch (action) 
		{
			case "newAccount":
				newAccount(request, response);
				break;
			case "editAccount":
				editAccount(request, response);
				break;
			case "deleteAccount":
				deleteAccount(request, response);
				break;
		}
	}
	
	private void newAccount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

	}

	private void editAccount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		int clientId = Optional.ofNullable(request.getParameter("clientId"))
				.map(Integer::parseInt)
				.orElse(0);
		int accountId = Optional.ofNullable(request.getParameter("accountId"))
				.map(Integer::parseInt)
				.orElse(0);
		
		BigDecimal accountBalance = Optional.ofNullable(request.getParameter("accountBalance"))
				.map(BigDecimal::new)
				.orElse(BigDecimal.ZERO);
		
		try 
		{
			Account auxAccount = accountsBusiness.read(accountId);
			System.out.println(accountBalance);
			auxAccount.setBalance(accountBalance);
			Boolean success = accountsBusiness.update(auxAccount);
			Client client = getFullClient(clientId);
			if(success)
			{
				Helper.setReqMessage(request, "Cuenta actualizada con éxito!", MessageType.SUCCESS);
			} else
			{
				Helper.setReqMessage(request, "No se pudo modificar la cuenta", MessageType.ERROR);
			}
			request.setAttribute("client", client);
			Helper.redirect("/WEB-INF/AdminClientAccounts.jsp", request, response);
		}
		catch (BusinessException ex)
		{
			Helper.setReqMessage(request, ex.getMessage(), MessageType.ERROR);
			Helper.redirect("/WEB-INF/AdminClientAccounts.jsp", request, response);
		}
	}
	
	private void deleteAccount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		int clientId = Optional.ofNullable(request.getParameter("clientId"))
				.map(Integer::parseInt)
				.orElse(0);
		int accountId = Optional.ofNullable(request.getParameter("accountId"))
				.map(Integer::parseInt)
				.orElse(0);
		
		try 
		{
			Boolean success = accountsBusiness.delete(accountId);
			Client client = getFullClient(clientId);
			
			if(success)
			{
				Helper.setReqMessage(request, "Cuenta eliminada con éxito!", MessageType.SUCCESS);
			} else
			{
				Helper.setReqMessage(request, "No se pudo eliminar la cuenta", MessageType.ERROR);
			}
			
			request.setAttribute("client", client);
			Helper.setReqMessage(request, "Cuenta eliminada con éxito!", MessageType.SUCCESS);
			Helper.redirect("/WEB-INF/AdminClientAccounts.jsp", request, response);
		}
		catch (BusinessException ex)
		{
			Helper.setReqMessage(request, ex.getMessage(), MessageType.ERROR);
			Helper.redirect("/WEB-INF/AdminClientAccounts.jsp", request, response);
		}
	}
	
	private void viewClientAccounts(HttpServletRequest request, HttpServletResponse response,
			int clientId)
			throws ServletException, IOException 
	{
		try 
		{
			Client client = getFullClient(clientId);
			request.setAttribute("client", client);
			
			Helper.redirect("/WEB-INF/AdminClientAccounts.jsp", request, response);
		}
		catch (BusinessException ex)
		{
			Helper.setReqMessage(request, ex.getMessage(), MessageType.ERROR);
			Helper.redirect("/Admin/Clients", request, response);
		}
	}
	
	private Client getFullClient(int clientId) throws BusinessException
	{
		try
		{
			Client client = clientsBusiness.read(clientId);
			
			ArrayList<Account> accountsList = new ArrayList<Account>();
			accountsList = accountsBusiness.listByIdClient(clientId);
			
			ArrayList <Loan> loansList = new ArrayList<Loan>();
			  
			for (Account account : accountsList)
			{
				int accountId = account.getId();
				ArrayList<Loan> accountLoans = loansBusiness.listByIdAccount(accountId);
				loansList.addAll(accountLoans);
			}
	
			client.setLoans(loansList); 
			client.setAccounts(accountsList);
			
			return client;
		}
		catch (BusinessException ex) 
		{
			throw ex;
		}
	}
	
}
