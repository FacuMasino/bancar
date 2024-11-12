package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import businessLogicImpl.AccountsBusiness;
import domainModel.Account;
import domainModel.AccountType;
import domainModel.Client;
import exceptions.BusinessException;

@WebServlet("/AccountsServlet")
public class AccountsServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private AccountsBusiness accountsBusiness;

    public AccountsServlet()
    {
        super();
        accountsBusiness = new AccountsBusiness();
    }

	protected void doGet(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException
	{
		try 
		{

			if(accountsBusiness.delete(1))
			{
				System.out.println("Cuenta eliminada");
			}
			
			Account auxAcc = accountsBusiness.read(1); 
		
			auxAcc.getAccountType().setId(1);
			System.out.println(auxAcc.getId());
			auxAcc.setBalance(new BigDecimal(120000.20));
			
			if(accountsBusiness.update(auxAcc)) {
				System.out.println("Cuenta modificada");
			} else {
				System.out.println("No se pudo modificar");
			}
			
			Account account = new Account();
			
			Client client = new Client();
			client.setId(1);
			account.setClient(client);
			
			AccountType accountType = new AccountType();
			accountType.setId(2);
			account.setAccountType(accountType);
			
			BigDecimal saldo = new BigDecimal(9999);
			account.setBalance(saldo);
			
			accountsBusiness.create(account);
			
			ArrayList<Account> accounts = accountsBusiness.list();
			for(Account acc : accounts) {
				System.out.println(acc.getCbu());
			}
			
		}
		
		catch (BusinessException ex) {
			ex.printStackTrace();
		}
	}

	protected void doPost(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException
	{
		doGet(request, response);
	}
	
	private void redirect(
	        String url,
	        HttpServletRequest request,
	        HttpServletResponse response)
	        throws ServletException, IOException
	{
	    RequestDispatcher rd = request.getRequestDispatcher(url);
	    rd.forward(request, response);
	}
}
