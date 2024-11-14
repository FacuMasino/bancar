package controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domainModel.Message.MessageType;
import utils.Helper;

@WebServlet(urlPatterns = {"/Admin","/Admin/"})
public class AdminPanelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AdminPanelServlet() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginSuccess = Optional.ofNullable(request.getParameter("login"))
				.orElse("");
		if(loginSuccess.equals("true"))
		{
			Helper.setReqMessage(request, "Iniciaste sesión con éxito!", MessageType.SUCCESS);
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/AdminPanel.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}