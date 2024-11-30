package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import businessLogicImpl.AccountTypesBusiness;
import businessLogicImpl.AccountsBusiness;
import businessLogicImpl.ClientsBusiness;
import businessLogicImpl.LoansBusiness;
import businessLogicImpl.MovementTypesBusiness;
import businessLogicImpl.MovementsBusiness;
import domainModel.Account;
import domainModel.AccountType;
import domainModel.Client;
import domainModel.Loan;
import domainModel.Movement;
import domainModel.Message.MessageType;
import exceptions.BusinessException;
import utils.Helper;
import utils.Page;

@WebServlet(urlPatterns = { "/Admin/Accounts", "/Admin/Accounts/" })
public class AdminAccountsServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private AccountsBusiness accountsBusiness;
	private ClientsBusiness clientsBusiness;
	private LoansBusiness loansBusiness;
	private AccountTypesBusiness accountTypesBusiness;
	private MovementsBusiness movementsBusiness;
	private MovementTypesBusiness movementTypesBusiness;

	public AdminAccountsServlet()
	{
		super();
		accountsBusiness = new AccountsBusiness();
		clientsBusiness = new ClientsBusiness();
		loansBusiness = new LoansBusiness();
		accountTypesBusiness = new AccountTypesBusiness();
		movementsBusiness = new MovementsBusiness();
		movementTypesBusiness = new MovementTypesBusiness();

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

		/// si entra acá es porque viene por el HREF
		if (request.getParameter("accountId") == null)
		{
			viewClientAccounts(request, response, clientId);

		} else
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
		String accountTypeId = request.getParameter("accountType");
		int typeId = Integer.parseInt(accountTypeId);

		try
		{
			if (typeId == 0) 
			{
				Helper.setReqMessage(request, 
						"Debe seleccionar tipo de cuenta a crear", MessageType.ERROR);
				viewClientAccounts(request, response, clientId);
				return;
			}
			
			AccountType accountType;
			accountType = accountTypesBusiness.read(typeId);
			account.setAccountType(accountType);	
			account.setBalance(new BigDecimal("10000.00"));
			account.setClientId(clientId);
			account.setCbu(account.getCbu());

			Boolean success = accountsBusiness.create(account);
			Client client = getFullClient(clientId);

			if (success)
			{
				Helper.setReqMessage(request, "Cuenta creada exitosamente.",
						MessageType.SUCCESS);
			}
			else
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
			ex.printStackTrace();
			viewClientAccounts(request, response, clientId);
			return;
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
			}
			else
			{
				Helper.setReqMessage(request, "No se pudo modificar la cuenta",
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

	private void deleteAccount(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		int clientId = Optional.ofNullable(request.getParameter("clientId"))
				.map(Integer::parseInt).orElse(0);
		int accountId = Optional.ofNullable(request.getParameter("accountId"))
				.map(Integer::parseInt).orElse(0);
		Client client = new Client();

		try
		{
			client = getFullClient(clientId);
			Boolean success = accountsBusiness.delete(accountId);

			if (success)
			{
				Helper.setReqMessage(request, "Cuenta eliminada con éxito!",
						MessageType.SUCCESS);
			} 
			else
			{
				Helper.setReqMessage(request, "No se pudo eliminar la cuenta",
						MessageType.ERROR);
			}

			viewClientAccounts(request, response, clientId);

		}
		catch (BusinessException ex)
		{
			Helper.setReqMessage(request, ex.getMessage(), MessageType.ERROR);
			request.setAttribute("client", client);
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

			BigDecimal totalLoansDebt = new BigDecimal(0);

			for (Loan loan : client.getLoans())
			{
				totalLoansDebt = totalLoansDebt
						.add(loansBusiness.calcOutstandingBalance(loan));
			}

			request.setAttribute("client", client);
			request.setAttribute("totalLoansDebt", totalLoansDebt);
			request.setAttribute("accountTypes", accountTypesBusiness.list());
			Helper.redirect("/WEB-INF/AdminClientAccounts.jsp", request,
					response);
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
			accountsList = accountsBusiness.list(clientId);

			List<Loan> loansList = new ArrayList<Loan>();

			loansList = loansBusiness.list(client);

			client.setLoans(loansList);
			client.setAccounts(accountsList);

			return client;
		}
		catch (BusinessException ex)
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
			int accountId = Optional
					.ofNullable(request.getParameter("accountId"))
					.map(Integer::parseInt).orElse(0);
			int clientId = Optional.ofNullable(request.getParameter("clientId"))
					.map(Integer::parseInt).orElse(0);

			Integer movementTypeId = Optional
					.ofNullable(request.getParameter("movementTypeId"))
					.map(String::trim).filter(s -> !s.isEmpty())
					.map(Integer::parseInt).orElse(null);

			String transactionDateStr = Optional
					.ofNullable(request.getParameter("transactionDate"))
					.map(String::trim).filter(s -> !s.isEmpty()).orElse(null);
			
			String searchInput = Optional
					.ofNullable(request.getParameter("searchInput"))
					.map(String::trim).filter(s -> !s.isEmpty()).orElse(null);

			ArrayList<Movement> movementsList = new ArrayList<Movement>();
			movementsList = movementsBusiness.list(accountId);

			if (movementTypeId != null)
			{
				movementsList = movementsBusiness.list(accountId,
						movementTypeId);
			}

			if (transactionDateStr != null)
			{

				movementsList = movementsBusiness.filterByDate(movementsList,
						transactionDateStr);
			}
			
			if (searchInput != null) {
				
				movementsList = movementsBusiness.search(accountId, movementsList, searchInput);
			}

			Client client = getFullClient(clientId);
			Account account = accountsBusiness.read(accountId);
			Page<Movement> movementsPage = getMovementsPage(request,
					movementsList);

			request.setAttribute("client", client);
			request.setAttribute("account", account);
			request.setAttribute("page", movementsPage);
			request.setAttribute("movementTypes", movementTypesBusiness.list());
			Helper.redirect("/WEB-INF/AdminAccountDetails.jsp", request,
					response);
		} 
		catch (BusinessException e)
		{
			Helper.setReqMessage(request, e.getMessage(), MessageType.ERROR);
			Helper.redirect("/Admin/Clients", request, response);
			e.printStackTrace();
		}
	}

	private Page<Movement> getMovementsPage(HttpServletRequest req,
			List<Movement> movements)
	{
		int page = Optional.ofNullable(req.getParameter("page"))
				.map(Integer::parseInt).orElse(1);

		int pageSize = Optional.ofNullable(req.getParameter("pageSize"))
				.map(Integer::parseInt).orElse(10);

		Page<Movement> movmentsPage = new Page<Movement>(page, pageSize,
				movements);
		return movmentsPage;
	}
}