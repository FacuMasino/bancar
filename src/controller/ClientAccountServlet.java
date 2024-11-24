package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import businessLogicImpl.AccountsBusiness;
import businessLogicImpl.ClientsBusiness;
import businessLogicImpl.MovementsBusiness;
import domainModel.Account;
import domainModel.Client;
import domainModel.Message.MessageType;
import domainModel.Movement;
import domainModel.User;
import exceptions.BusinessException;
import utils.Helper;

@WebServlet(urlPatterns = { "/Client/", "/Client" })
public class ClientAccountServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private ClientsBusiness clientBusiness;
	private AccountsBusiness accountBusiness;
	private MovementsBusiness movementBusiness;

	public ClientAccountServlet()
	{
		super();
		clientBusiness = new ClientsBusiness();
		accountBusiness = new AccountsBusiness();
		movementBusiness = new MovementsBusiness();
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

	private void showMovements(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(false);

		User user = (User) session.getAttribute("user");

		Client client = new Client();

		try
		{
			ArrayList<Account> accounts = new ArrayList<Account>();

			client = clientBusiness.findClientByUserId(user.getUserId());
			accounts = accountBusiness.listByIdClient(client.getClientId());
			ArrayList<Movement> movementList = new ArrayList<Movement>();

			client.setAccounts(accounts);

			int selectedAccountId;

			if (accounts.isEmpty())
			{
				System.out.println(
						"\nEl cliente no tiene cuentas disponibles!!!");
			} else
			{
				if (request.getParameter("selectedAccountId") == null)
				{
					//fuerzo la seleccion a la primer cuenta disponible, porque si es una sola,no se puede seleccionar
					selectedAccountId = accounts.get(0).getId();
				} else
				{
					selectedAccountId = Integer.parseInt(
							request.getParameter("selectedAccountId"));
				}
				movementList = movementBusiness
						.listByIdAccount(selectedAccountId);
				Account auxAccount = accountBusiness.read(selectedAccountId);

				request.setAttribute("idSelectedAccount", selectedAccountId);
				request.setAttribute("movements", movementList);
				request.setAttribute("selectedAccountBalance",
						auxAccount.getBalance());
			}
			request.setAttribute("client", client);
		} catch (BusinessException ex)
		{
			ex.printStackTrace();
			Helper.setReqMessage(req, ex.getMessage(), MessageType.ERROR);
		}

		Helper.redirect("WEB-INF/Account.jsp", request, response);
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
		
		Helper.redirect("Profile.jsp", req, res);
	}

}
