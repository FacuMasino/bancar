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
import businessLogicImpl.AccountTypesBusiness;
import businessLogicImpl.AccountsBusiness;
import businessLogicImpl.ClientsBusiness;
import businessLogicImpl.LoansBusiness;
import businessLogicImpl.MovementsBusiness;
import domainModel.Account;
import domainModel.AccountType;
import domainModel.Client;
import domainModel.Loan;
import domainModel.Movement;
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
	private AccountTypesBusiness accountTypesBusiness;
	private MovementsBusiness movementsBusiness;

	public AdminAccountsServlet()
	{
		super();
		accountsBusiness = new AccountsBusiness();
		clientsBusiness = new ClientsBusiness();
		loansBusiness = new LoansBusiness();
		accountTypesBusiness = new AccountTypesBusiness();
		movementsBusiness = new MovementsBusiness();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{

		int clientId = Optional.ofNullable(request.getParameter("clientId"))
				.map(Integer::parseInt).orElse(0);
		if (clientId == 0)
		{
			response.sendRedirect("Clients");
			return;
		}

		viewClientAccounts(request, response, clientId);

		//// SI ENTRA ACÁ VIENE POR EL HREF

		if (request.getParameter("id") != null)
		{
			listMovements(request, response);
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{

		int clientId = Optional.ofNullable(request.getParameter("clientId"))
				.map(Integer::parseInt).orElse(0);

		String action = request.getParameter("action");

		switch (action)
		{
		case "saveNewAccount":
			saveNewAccount(request, response);
			break;
		case "editAccount":
			editAccount(request, response);
			break;
		case "deleteAccount":
			deleteAccount(request, response);
			break;
		}
	}

	private void saveNewAccount(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		int clientId = Optional.ofNullable(request.getParameter("clientId"))
				.map(Integer::parseInt).orElse(0);

		Account account = new Account();

		try
		{
			String accountTypeId = request.getParameter("accountType");
			int typeId = Integer.parseInt(accountTypeId);
			AccountType accountType;
			accountType = accountTypesBusiness.read(typeId);
			account.setAccountType(accountType);
		} catch (BusinessException e)
		{
			e.printStackTrace();
		}

		account.setBalance(new BigDecimal("10000.00"));
		account.setClientId(clientId);
		account.setCbu(account.getCbu());

		try
		{
			Boolean success = accountsBusiness.create(account);
			Client client = getFullClient(clientId);

			if (success)
			{
				Helper.setReqMessage(request, "Cuenta creada exitosamente.",
						MessageType.SUCCESS);
			} else
			{
				Helper.setReqMessage(request, "No se pudo crear la cuenta.",
						MessageType.ERROR);
			}

			System.out.println("Cuenta creada exitosamente.");
			request.setAttribute("client", client);
			request.setAttribute("accountTypes", accountTypesBusiness.list());
			Helper.redirect("/WEB-INF/AdminClientAccounts.jsp", request,
					response);
		}

		catch (BusinessException ex)
		{
			Helper.setReqMessage(request, ex.getMessage(), MessageType.ERROR);
			Helper.redirect("/WEB-INF/AdminClientAccounts.jsp", request,
					response);
			ex.printStackTrace();
		}
	}

	private void editAccount(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		int clientId = Optional.ofNullable(request.getParameter("clientId"))
				.map(Integer::parseInt).orElse(0);
		int accountId = Optional.ofNullable(request.getParameter("accountId"))
				.map(Integer::parseInt).orElse(0);

		BigDecimal accountBalance = Optional
				.ofNullable(request.getParameter("accountBalance"))
				.map(BigDecimal::new).orElse(BigDecimal.ZERO);

		try
		{
			Account auxAccount = accountsBusiness.read(accountId);
			System.out.println(accountBalance);
			auxAccount.setBalance(accountBalance);
			Boolean success = accountsBusiness.update(auxAccount);
			Client client = getFullClient(clientId);
			if (success)
			{
				Helper.setReqMessage(request, "Cuenta actualizada con éxito!",
						MessageType.SUCCESS);
			} else
			{
				Helper.setReqMessage(request, "No se pudo modificar la cuenta",
						MessageType.ERROR);
			}
			request.setAttribute("client", client);
			Helper.redirect("/WEB-INF/AdminClientAccounts.jsp", request,
					response);
		} catch (BusinessException ex)
		{
			Helper.setReqMessage(request, ex.getMessage(), MessageType.ERROR);
			Helper.redirect("/WEB-INF/AdminClientAccounts.jsp", request,
					response);
		}
	}

	private void deleteAccount(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		int clientId = Optional.ofNullable(request.getParameter("clientId"))
				.map(Integer::parseInt).orElse(0);
		int accountId = Optional.ofNullable(request.getParameter("accountId"))
				.map(Integer::parseInt).orElse(0);

		try
		{
			Boolean success = accountsBusiness.delete(accountId);
			Client client = getFullClient(clientId);

			if (success)
			{
				Helper.setReqMessage(request, "Cuenta eliminada con éxito!",
						MessageType.SUCCESS);
			} else
			{
				Helper.setReqMessage(request, "No se pudo eliminar la cuenta",
						MessageType.ERROR);
			}

			request.setAttribute("client", client);
			Helper.redirect("/WEB-INF/AdminClientAccounts.jsp", request,
					response);
		
		}
		
		catch (BusinessException ex)
		{
			Helper.setReqMessage(request, ex.getMessage(), MessageType.ERROR);
			Helper.redirect("/WEB-INF/AdminClientAccounts.jsp", request,
					response);
		}
	}

	private void viewClientAccounts(HttpServletRequest request,
			HttpServletResponse response, int clientId)
			throws ServletException, IOException
	{
		try
		{
			Client client = getFullClient(clientId);
			request.setAttribute("client", client);
			request.setAttribute("accountTypes", accountTypesBusiness.list());

			Helper.redirect("/WEB-INF/AdminClientAccounts.jsp", request,
					response);
		} catch (BusinessException ex)
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

			ArrayList<Loan> loansList = new ArrayList<Loan>();

			loansList = loansBusiness.listByClient(client);

			client.setLoans(loansList);
			client.setAccounts(accountsList);

			return client;
		} catch (BusinessException ex)
		{
			throw ex;
		}
	}

	//// PARA LISTAR MOVIMIENTOS EN NUEVO JSP

	private void listMovements(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			// TODO: Cargar lista cuando funcione eḷ negocio
			// Enviar atributo con la lista
			int accountId = Optional
					.ofNullable(request.getParameter("accountId"))
					.map(Integer::parseInt).orElse(0);
			int clientId = Optional.ofNullable(request.getParameter("clientId"))
					.map(Integer::parseInt).orElse(0);

			Client client = getFullClient(clientId);
			Account account = accountsBusiness.read(accountId);
			ArrayList<Movement> movementsList = new ArrayList<Movement>();
			movementsList = movementsBusiness.listByIdAccount(accountId);

			request.setAttribute("client", client);
			request.setAttribute("account", account);
			request.setAttribute("movementsList", movementsList);
			Helper.redirect("/WEB-INF/AdminAccountDetails.jsp", request,
					response);
		} catch (BusinessException e)
		{
			Helper.setReqMessage(request, e.getMessage(), MessageType.ERROR);
			Helper.redirect("/Admin/Clients", request, response);
			e.printStackTrace();
		}
	}
}