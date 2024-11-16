package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import businessLogicImpl.AccountsBusiness;
import businessLogicImpl.ClientsBusiness;
import businessLogicImpl.LoansBusiness;
import domainModel.Account;
import domainModel.AccountType;
import domainModel.Client;
import domainModel.Loan;
import domainModel.Message.MessageType;
import exceptions.BusinessException;
import utils.Helper;

@WebServlet(urlPatterns = { "/Admin/Loans", "/Admin/Loan/" })
public class AdminLoansServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private AccountsBusiness accountsBusiness;
	private ClientsBusiness clientsBusiness;
	private LoansBusiness loansBusiness;

	public AdminLoansServlet()
	{
		super();
		accountsBusiness = new AccountsBusiness();
		clientsBusiness = new ClientsBusiness();
		loansBusiness = new LoansBusiness();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		viewAdminLoans(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		String action = request.getParameter("action");

		switch (action) 
		{
			case "approve":
				approveLoan(request, response);
				break;
			case "reject":
				rejectLoan(request, response);
				break;
		}
	}
	
	private void approveLoan(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

	}

	private void rejectLoan(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		
	}
	
	private void viewAdminLoans(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{

		Helper.redirect("/WEB-INF/AdminLoans.jsp", request, response);
	}
	
}
