package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import businessLogicImpl.AccountTypesBusiness;
import businessLogicImpl.AccountsBusiness;
import businessLogicImpl.ClientsBusiness;
import businessLogicImpl.LoansBusiness;
import domainModel.Account;
import domainModel.Client;
import domainModel.Loan;
import domainModel.Message.MessageType;
import exceptions.BusinessException;
import utils.Helper;

@WebServlet(urlPatterns = { "/Client/Loans", "/Client/Loans/" })
public class ClientLoansServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AccountsBusiness accountsBusiness;
	private LoansBusiness loansBusiness;
	private Client client;

    public ClientLoansServlet() {
        super();
		accountsBusiness = new AccountsBusiness();
		loansBusiness = new LoansBusiness();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException
	{
		
		String action = req.getParameter("action");
		getSessionClient(req); // Obtener cliente y sus datos
		
		if(action == null || action.isEmpty())
		{
			viewClientLoans(req, res);
			return;
		}
		
		switch (action)
		{
			case "payLoan":
				viewPayLoan(req, res);
			case "apply":
				viewApplyForLoan(req, res);
		}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException
	{
		getSessionClient(req); // Obtener cliente y sus datos
		doGet(req, res);
	}

	private void viewClientLoans(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
	{
		List<Loan> approvedLoans = new ArrayList<Loan>();
		List<Loan> pendingLoans = new ArrayList<Loan>();
		List<Loan> clientLoans = new ArrayList<Loan>();
		
		clientLoans = client.getLoans();
		
		System.out.println(clientLoans);
		
		// Clasificar prestamos
		for(Loan loan: clientLoans)
		{
			int statusId = loan.getLoanStatus().getLoanStatusId();
			
			switch(statusId)
			{
				// En revisión
				case 1:
					pendingLoans.add(loan);
					break;
				// Vigente
				case 2:
					approvedLoans.add(loan);
					break;
				default:
					break;
			}
		}
		
		req.setAttribute("approvedLoans", approvedLoans);
		req.setAttribute("pendingLoans", pendingLoans);
		Helper.redirect("/WEB-INF/Loans.jsp", req, res);
	}
	
	private void viewPayLoan(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
	{
		Helper.redirect("/WEB-INF/PayLoan.jsp", req, res);
	}	

	private void viewApplyForLoan(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
	{
		
		
		Helper.redirect("/WEB-INF/ApplyForLoan.jsp", req, res);
	}
	
	/*
	 * Esta función lee el cliente almacenado en la session,
	 * Cada vez que se haga un doGet o doPost se obtienen los
	 * datos actualizados de cuentas y préstamos del cliente.
	 * 
	 * El cliente se asigna al atributo client en este servlet
	 * para poder ser accedido desde cualquiera de sus métodos.
	 */
	private void getSessionClient(HttpServletRequest req) 
	{
		try
		{
			client = (Client)req.getSession().getAttribute("client");
			int clientId = client.getClientId();
			
			ArrayList<Account> accountsList = new ArrayList<Account>();
			accountsList = accountsBusiness.listByIdClient(clientId);
			
			ArrayList <Loan> loansList = new ArrayList<Loan>();
			  
			for (Account account : accountsList)
			{
				int accountId = account.getId();
				ArrayList<Loan> accountLoans = loansBusiness.listByIdAccount(accountId);
				loansList.addAll(accountLoans);
			}
	
			client.setLoans(loansList);
			client.setAccounts(accountsList);
			
			// Se setea el atributo para que lo tenga el JSP al que se redireccione
			req.setAttribute("client", client);
		}
		catch (BusinessException ex) 
		{
			Helper.setReqMessage(req, "Ocurrió un error al obtener los datos del cliente.", MessageType.ERROR);
		}
	}
}
