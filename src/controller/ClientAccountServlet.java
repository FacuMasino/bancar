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

			int idSelectedAccount;

			if (accounts.isEmpty())
			{
				System.out.println(
						"\nEl cliente no tiene cuentas disponibles!!!");
			} else
			{
				if (request.getParameter("idSelectedAccount") == null)
				{
					//fuerzo la seleccion a la primer cuenta disponible, porque si es una sola,no se puede seleccionar
					idSelectedAccount = accounts.get(0).getId();
				} else
				{
					idSelectedAccount = Integer.parseInt(
							request.getParameter("idSelectedAccount"));
				}
				movementList = movementBusiness
						.listByIdAccount(idSelectedAccount);
				Account auxAccount = accountBusiness.read(idSelectedAccount);

				request.setAttribute("idSelectedAccount", idSelectedAccount);
				request.setAttribute("movements", movementList);
				request.setAttribute("selectedAccountBalance",
						auxAccount.getBalance());
			}
			request.setAttribute("client", client);
		} catch (BusinessException e)
		{
			// TODO: ver que exceptions tendriamos que mandar
			e.printStackTrace();
		}

		Helper.redirect("WEB-INF/Account.jsp", request, response);
	}

	private void viewProfile(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Entramos a VIEWPROFILE!!");

		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(false);

		User user = (User) session.getAttribute("user");


		try
		{
			Client auxClient = clientBusiness.findClientByUserId(user.getUserId());
			request.getSession().setAttribute("client", auxClient);
			request.setAttribute("client", auxClient);
		} catch (BusinessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Helper.redirect("Profile.jsp", request, response);

	}

}
