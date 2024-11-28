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
import javax.servlet.http.HttpSession;

import businessLogicImpl.AccountsBusiness;
import businessLogicImpl.ClientsBusiness;
import businessLogicImpl.MovementTypesBusiness;
import businessLogicImpl.MovementsBusiness;
import domainModel.Account;
import domainModel.Client;
import domainModel.LoanStatus;
import domainModel.LoanType;
import domainModel.Message.MessageType;
import domainModel.Movement;
import domainModel.User;
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
		
		Integer movementTypeId = Optional.ofNullable(req.getParameter("movementTypeId"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .orElse(null);

		try
		{

			ArrayList<Account> accounts = new ArrayList<Account>();
			accounts = accountBusiness.listByIdClient(client.getClientId());
			
			client.setAccounts(accounts);

			int selectedAccountId;

			if (accounts.isEmpty())
			{
				System.out.println(
						"\nEl cliente no tiene cuentas disponibles!!!");
			} 
			else
			{
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
				
				if (movementTypeId != null)
				{
				    movementsList = movementBusiness.listFilter(selectedAccountId, movementTypeId);
				} 
				
				Page<Movement> movementsPage = getMovementsPage(req,movementsList);
				
				Account auxAccount = accountBusiness.read(selectedAccountId);

				req.setAttribute("selectedAccount", auxAccount);
				req.setAttribute("movementsPage", movementsPage);
				req.setAttribute("movementTypes", movementTypesBusiness.list());
			}
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
		/*
		 * Nota para Gonza:
		 * No hace falta volver a obtener el cliente, porque si el usuario
		 * está acá, es porque inició sesión. Y si lo hizo entonces
		 * el cliente ya está almacenado en la session con el nombre de 
		 * atributo "client". Siempre va a estar, si no está entonces no
		 * tiene forma de llegar a esta página porque cerró sesión o nunca la inició
		 */

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

}