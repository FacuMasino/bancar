package controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import businessLogicImpl.ClientsBusiness;
import domainModel.Client;
import domainModel.Message.MessageType;
import domainModel.User;
import exceptions.BusinessException;
import utils.Helper;

@WebServlet(urlPatterns = { "/Client/", "/Client" })
public class ClientAccountServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private ClientsBusiness clientBusiness;

	public ClientAccountServlet()
	{
		super();
		clientBusiness = new ClientsBusiness();
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

		// TODO: Falta implementar que levante el user/cliente de session
		//TODO: levantó bien el user/client?

		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(false);

		User user = (User) session.getAttribute("user");

		Client client = new Client();

		try
		{
			client = clientBusiness.findClientByUserId(user.getUserId());
			System.out.println(client.toString());
		} 
		catch (BusinessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("client", client);
		//TODO SEGUID MAPEANDO DATOS EN EL FRONT, ACCOUNTS,LOANS,ETC
		Helper.redirect("WEB-INF/Account.jsp", request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

}
