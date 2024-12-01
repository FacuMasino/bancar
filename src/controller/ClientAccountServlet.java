package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import businessLogicImpl.AccountsBusiness;
import businessLogicImpl.MovementTypesBusiness;
import businessLogicImpl.MovementsBusiness;
import domainModel.Account;
import domainModel.Client;
import domainModel.Message.MessageType;
import domainModel.Movement;
import domainModel.MovementTypeEnum;
import exceptions.BusinessException;
import utils.Helper;
import utils.Page;

@WebServlet(urlPatterns = { "/Client/", "/Client" })
public class ClientAccountServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private AccountsBusiness accountBusiness;
	private MovementsBusiness movementBusiness;
	private MovementTypesBusiness movementTypesBusiness;

	public ClientAccountServlet()
	{
		super();
		accountBusiness = new AccountsBusiness();
		movementBusiness = new MovementsBusiness();
		movementTypesBusiness = new MovementTypesBusiness();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		String loginSuccess = Optional.ofNullable(request.getParameter("login"))
				.orElse("");

		String action = Optional.ofNullable(request.getParameter("action"))
				.orElse("");

		if (loginSuccess.equals("true"))
		{
			Helper.setReqMessage(request, "Iniciaste sesión con éxito!",
					MessageType.SUCCESS);
		}

		switch (action)
		{
		case "viewProfile":
			viewProfile(request, response);
			break;
		case "details":
			showTransactionDetails(request, response);
			break;
		default:
			showMovements(request, response);
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

	private void showMovements(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException
	{
		Client client = new Client();
		client = (Client)req.getSession().getAttribute("client");
		
		int movementTypeId = Optional.ofNullable(req.getParameter("movementTypeId"))
                .map(Integer::parseInt)
                .orElse(0);
		
		String transactionDateStr = Optional
				.ofNullable(req.getParameter("transactionDate"))
				.map(String::trim).filter(s -> !s.isEmpty()).orElse(null);
		
		String searchInput = Optional
				.ofNullable(req.getParameter("searchInput"))
				.map(String::trim).filter(s -> !s.isEmpty()).orElse(null);

		try
		{
			ArrayList<Account> accounts = new ArrayList<Account>();
			accounts = accountBusiness.list(client.getClientId());
			
			client.setAccounts(accounts);

			int selectedAccountId;

			if (req.getParameter("selectedAccountId") == null)
			{
				//fuerzo la seleccion a la primer cuenta disponible, porque si es una sola,no se puede seleccionar
				selectedAccountId = accounts.get(0).getId();
			}
			else
			{
				selectedAccountId = Integer.parseInt(
						req.getParameter("selectedAccountId"));
			}
			
			ArrayList<Movement> movementsList = new ArrayList<Movement>();
			movementsList = movementBusiness.listByIdAccount(selectedAccountId);
			Collections.sort(movementsList); // Ordena la lista según el método compareTo de la clase Movement
			
			if (movementTypeId != 0)
			{
			    movementsList = movementBusiness.list(selectedAccountId, movementTypeId);
			}
			
			if (transactionDateStr != null)
			{
				movementsList = movementBusiness.filterByDate(movementsList,
						transactionDateStr);
			}
			
			if (searchInput != null) {
				
				movementsList = movementBusiness.search(selectedAccountId, movementsList, searchInput);
			}
			
			Page<Movement> movementsPage = getMovementsPage(req,movementsList);
			
			Account auxAccount = accountBusiness.read(selectedAccountId);

			req.setAttribute("selectedAccount", auxAccount);
			req.setAttribute("movementsPage", movementsPage);
			req.setAttribute("movementTypes", movementTypesBusiness.list());
			req.setAttribute("client", client);			
		} 
		catch (BusinessException ex)
		{
			ex.printStackTrace();
			Helper.setReqMessage(req, ex.getMessage(), MessageType.ERROR);
		}

		Helper.redirect("/WEB-INF/Account.jsp", req, res);
	}

	private void viewProfile(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException
	{
		Client auxClient = (Client)req.getSession().getAttribute("client");
		req.setAttribute("client", auxClient);
		
		Helper.redirect("/WEB-INF/Profile.jsp", req, res);
	}
	
	private Page<Movement> getMovementsPage(HttpServletRequest req, 
			List<Movement> movements)
	{
		int page = Optional.ofNullable(
				req.getParameter("page")).map(Integer::parseInt).orElse(1);
		int pageSize = Optional.ofNullable(
				req.getParameter("pageSize")).map(Integer::parseInt).orElse(10);
		
		Page<Movement> movmentsPage = new Page<Movement>(page, pageSize, movements);
		return movmentsPage;
	}

	private void showTransactionDetails(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
	{
		Client client = new Client();
		client = (Client)req.getSession().getAttribute("client");
		try
		{
			int movementId = Optional.ofNullable(req.getParameter("movementId"))
					.map(Integer::parseInt)
					.orElse(0);
			
			int movementTypeId = Optional.ofNullable(req.getParameter("typeId"))
					.map(Integer::parseInt)
					.orElse(0);
			
			if(movementId == 0 || movementTypeId == 0)
			{
				throw new Exception("Los datos de la transacción son inválidos.");
			}
			
			if(movementTypeId == MovementTypeEnum.TRANSFER.getId())
			{
				// TODO: En desarrollo [Facu] hasta resolver lo de las transferencias
			}
			
			if(movementTypeId == MovementTypeEnum.LOAN_PAYMENT.getId())
			{
				// TODO: IDEM
			}
			
			req.setAttribute("isCurrent", false);
			req.setAttribute("success", true);
			req.setAttribute("originClient", client);
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			Helper.setReqMessage(req, ex.getMessage(), MessageType.ERROR);
			showMovements(req,res);
			return;
		}
		
		Helper.redirect("/WEB-INF/TransactionDetails.jsp", req, res);
	}
	
}