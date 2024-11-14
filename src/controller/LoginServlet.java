package controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import businessLogicImpl.UsersBusiness;
import domainModel.Message;
import domainModel.Message.MessageType;
import domainModel.User;
import exceptions.BusinessException;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UsersBusiness usersBusiness;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        usersBusiness = new UsersBusiness();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");

		switch (action) 
		{
			case "login":
				login(request, response);
				break;
			case "logout":
				logout(request, response);
				break;
			default:
				login(request, response);
		}
	}
	
	private void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		request.getSession().removeAttribute("user");
		setMessage(request,"Cerraste sesión con éxito!", MessageType.SUCCESS);
		RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
		rd.forward(request, response);
	}
	
	private void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String username = Optional.ofNullable(request.getParameter("username"))
				.orElse("");
		String password = Optional.ofNullable(request.getParameter("password"))
				.orElse("");
		
		try {
			User auxUser = usersBusiness.validateCredentials(username, password);
			if(auxUser != null)
			{
				request.getSession().setAttribute("user", auxUser);
				if(auxUser.getRole().getName().equals("Cliente")) {					
					response.sendRedirect(request.getContextPath() + "/Client?login=true");
				} else {
					response.sendRedirect(request.getContextPath() + "/Admin?login=true");
				}
				return;
			}
			setMessage(request,"El usuario ingresado no existe", MessageType.ERROR);
		} catch (BusinessException e) {
			setMessage(request, e.getMessage(), MessageType.ERROR);
		}
		RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
		rd.forward(request, response);
	}

	private void setMessage(HttpServletRequest request, String message, MessageType type)
	{
		request.setAttribute("message", 
				new Message(message,type));
	}
	
}
