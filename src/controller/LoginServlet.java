package controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jdt.internal.compiler.ast.NumberLiteral;

import businessLogicImpl.ClientsBusiness;
import businessLogicImpl.UsersBusiness;
import domainModel.Client;
import domainModel.Message;
import domainModel.Message.MessageType;
import domainModel.User;
import exceptions.BusinessException;
import utils.Helper;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UsersBusiness usersBusiness;
	private ClientsBusiness clientsBusiness;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        usersBusiness = new UsersBusiness();
        clientsBusiness = new ClientsBusiness();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            Helper.redirect("Login.jsp", request, response);
            return;
        }
        
        User user = (User) session.getAttribute("user");
        String roleName = user.getRole().getName();
        
        switch(roleName)
        {
        	case "Cliente":
        		response.sendRedirect(request.getContextPath() + "/Client");
        		break;
        	case "Administrador":
        		response.sendRedirect(request.getContextPath() + "/Admin");
        		break;
        	default:
                Helper.redirect("Login.jsp", request, response);
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		if(action == null)
		{
			doGet(request,response);
			return;
		}
		
		switch (action) 
		{
			case "login":
				login(request, response);
				break;
			case "logout":
				logout(request, response);
				break;
			case "viewProfile":
				response.sendRedirect(request.getContextPath() + "/Client?action=viewProfile");
				break;
			default:
				doGet(request,response);
		}
	}
	
	private void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("client");
		Helper.setReqMessage(request,"Cerraste sesión con éxito!", MessageType.SUCCESS);
		Helper.redirect("Login.jsp", request, response);
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
			if(auxUser == null)
			{
				Helper.setReqMessage(request,"El usuario ingresado no existe", MessageType.ERROR);
				Helper.redirect("Login.jsp", request, response);
				return;
			}
			
			// Si es un Cliente
			if(auxUser.getRole().getName().equals("Cliente"))
			{
				Client auxClient = clientsBusiness.findClientByUserId(auxUser.getUserId());
				if(auxClient != null && auxClient.getActiveStatus())
				{
					request.getSession().setAttribute("user", auxUser);
					request.getSession().setAttribute("client", auxClient);
					response.sendRedirect(request.getContextPath() + "/Client?login=true");
					return;
				}
				// Cliente dado de baja
				Helper.setReqMessage(request, "El usuario ingresado está dado de baja", MessageType.ERROR);
			}
			
			// Si es Administrador
			if(auxUser.getRole().getName().equals("Administrador"))
			{
				request.getSession().setAttribute("user", auxUser);
				response.sendRedirect(request.getContextPath() + "/Admin?login=true");
				return;
			}
		} catch (BusinessException e) {
			Helper.setReqMessage(request, e.getMessage(), MessageType.ERROR);
		}
		Helper.redirect("Login.jsp", request, response);
	}
	
}
