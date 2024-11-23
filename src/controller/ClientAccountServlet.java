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
		if (loginSuccess.equals("true"))
		{
			Helper.setReqMessage(request, "Iniciaste sesión con éxito!",
					MessageType.SUCCESS);
		}
		//TODO: esta funcion deberia ser una de las opciones de un switch? para separar las tareas de este servlet...
		showMovements(request,response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
	
	private void showMovements(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException
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
			}
			else
			{
				if (request.getParameter("idSelectedAccount") == null)	//fuerzo la seleccion a la primera cuenta que tenga disponible
				{														//sino, con 1 cuenta disponible, no la puedo seleccionar...
					idSelectedAccount = accounts.get(0).getId();
				} 
				else
				{
					idSelectedAccount = Integer.parseInt(request.getParameter("idSelectedAccount"));
				}
				movementList = movementBusiness.listByIdAccount(idSelectedAccount);
				Account auxAccount = accountBusiness.read(idSelectedAccount);
				
				request.setAttribute("idSelectedAccount", idSelectedAccount);
				request.setAttribute("movements", movementList);
				request.setAttribute("selectedAccountBalance",auxAccount.getBalance());
			}
			request.setAttribute("client", client);
		}
		catch (BusinessException e)
		{
			// TODO: ver que exceptions tendriamos que mandar
			e.printStackTrace();
		}

		Helper.redirect("WEB-INF/Account.jsp", request, response);
	}

}
