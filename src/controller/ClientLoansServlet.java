package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import businessLogic.IAccountsBusiness;
import businessLogic.ILoanStatusesBusiness;
import businessLogic.ILoanTypesBusiness;
import businessLogic.ILoansBusiness;
import businessLogicImpl.AccountTypesBusiness;
import businessLogicImpl.AccountsBusiness;
import businessLogicImpl.ClientsBusiness;
import businessLogicImpl.LoanStatusesBusiness;
import businessLogicImpl.LoanTypesBusiness;
import businessLogicImpl.LoansBusiness;
import domainModel.Account;
import domainModel.Client;
import domainModel.Installment;
import domainModel.Loan;
import domainModel.LoanStatus;
import domainModel.LoanType;
import domainModel.Message;
import domainModel.Message.MessageType;
import exceptions.BusinessException;
import utils.Helper;
import utils.Page;

@WebServlet(urlPatterns = { "/Client/Loans", "/Client/Loans/" })
public class ClientLoansServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IAccountsBusiness accountsBusiness;
	private ILoansBusiness loansBusiness;
	private ILoanTypesBusiness loanTypesBusiness;
	private ILoanStatusesBusiness loanStatusesBusiness;
	private Client client;

    public ClientLoansServlet() {
        super();
		accountsBusiness = new AccountsBusiness();
		loansBusiness = new LoansBusiness();
		loanTypesBusiness = new LoanTypesBusiness();
		loanStatusesBusiness = new LoanStatusesBusiness();
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
				break;
			case "apply":
				viewApplyForLoan(req, res);
				break;
			default:
				viewClientLoans(req, res);
				break;
		}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
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
			case "request":
				requestLoan(req, res);
				break;
			case "payInstallment":
				payInstallment(req,res);
			default:
				viewClientLoans(req, res);
				break;
		}
	}

	private void payInstallment(HttpServletRequest req, HttpServletResponse res)
	{
		try
		{
			int loanId = Optional.ofNullable(req.getParameter("id"))
					.map(Integer::parseInt)
					.orElse(0);
			
			int installmentId = Optional.ofNullable(req.getParameter("installmentId"))
					.map(Integer::parseInt)
					.orElse(0);
			
			// Busca entre la lista de préstamos el que coincida con el id
			Loan auxLoan = client.getLoans().stream()
					.filter(loan -> loan.getLoanId() == loanId)
					.findFirst()
					.orElse(null);
			
			if(auxLoan == null)
			{
				Helper.setReqMessage(req,
						"Ocurrió un error al cargar los datos del préstamo.", MessageType.ERROR);
				Helper.redirect("/WEB-INF/PayLoan.jsp", req, res);
				return; // Cortamos el flujo para evitar una excepción por volver a redireccionar
			}
			
			boolean success = loansBusiness.payLoan(auxLoan, installmentId);
			if(success)
			{
				//... [en proceso]
			}
			// manejar bssexceptions posibles [en proceso]
		} catch (Exception ex)
		{
			Helper.setReqMessage(req,
					"Error desconocido: " + ex.getMessage(), MessageType.ERROR);
			ex.printStackTrace();
		}
	}

	private void viewClientLoans(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
	{
		List<Loan> approvedLoans = new ArrayList<Loan>();
		List<Loan> pendingLoans = new ArrayList<Loan>();
		List<Loan> clientLoans = new ArrayList<Loan>();
		List<LoanStatus> loanStatuses = new ArrayList<LoanStatus>();
		List<LoanType> loanTypes = new ArrayList<LoanType>();
		
		try
		{
			// Prestamos del cliente en session
			clientLoans = client.getLoans();
			
			// Obtener estados y tipos 
			loanStatuses = loanStatusesBusiness.list();
			loanTypes = loanTypesBusiness.list();
			
			// System.out.println(clientLoans); // Descomentar para debuggear
			
			// Clasificar prestamos
			LoanStatus pendingStatus = new LoanStatus();
			pendingStatus.setId(1); // Id harcodeado, estado PENDIENTE		
			LoanStatus approvedStatus = new LoanStatus();
			approvedStatus.setId(2); // Id harcodeado, estado VIGENTE/APROBADO
			
			pendingLoans = loansBusiness.filter(pendingStatus, clientLoans);
			approvedLoans = loansBusiness.filter(approvedStatus, clientLoans);
			
			// Se obtiene la página actual del historial de préstamos para el paginado
			Page<Loan> historyPage = getLoansHistoryPage(req);
			
			req.setAttribute("loanStatuses", loanStatuses);
			req.setAttribute("loanTypes", loanTypes);
			req.setAttribute("historyPage", historyPage);
			req.setAttribute("approvedLoans", approvedLoans);
			req.setAttribute("pendingLoans", pendingLoans);
		} catch (BusinessException ex)
		{
			Helper.setReqMessage(req, ex.getMessage(), MessageType.ERROR);
		}
		
		Helper.redirect("/WEB-INF/Loans.jsp", req, res);
	}
	
	private Page<Loan> getLoansHistoryPage(HttpServletRequest req)
	{
		int page = Optional.ofNullable(
				req.getParameter("page")).map(Integer::parseInt).orElse(1);

		int pageSize = Optional.ofNullable(
				req.getParameter("pageSize")).map(Integer::parseInt).orElse(10);
		
		Page<Loan> loansPage = 
				new Page<Loan>(page, pageSize, client.getLoans());
		
		return loansPage;
	}
	
	private void viewPayLoan(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
	{
		try
		{
			// Con esta función Nullable evitamos la excepción Null Pointer
			// En el caso de que no exista el parámetro id.
			// loanId va a tener 0, y no se va a encontrar ningún
			// Préstamo con ese id ya que en la DB empiezan desde 1
			int loanId = Optional.ofNullable(req.getParameter("id"))
					.map(Integer::parseInt)
					.orElse(0);
			
			// Busca entre la lista de préstamos el que coincida con el id
			Loan auxLoan = client.getLoans().stream()
					.filter(loan -> loan.getLoanId() == loanId)
					.findFirst()
					.orElse(null);
			
			if(auxLoan == null)
			{
				Helper.setReqMessage(req,
						"Ocurrió un error al cargar los datos del préstamo.", MessageType.ERROR);
				Helper.redirect("/WEB-INF/PayLoan.jsp", req, res);
				return; // Cortamos el flujo para evitar una excepción por volver a redireccionar
			}
			
			BigDecimal outstandingBalance = loansBusiness.calcOutstandingBalance(auxLoan);
			
			req.setAttribute("outstandingBalance", outstandingBalance);
			req.setAttribute("loan", auxLoan);
		} catch (NumberFormatException ex)
		{
			Helper.setReqMessage(req, "El ID del préstamo tiene un formato inválido.", MessageType.ERROR);
			viewClientLoans(req,res);
			return;
		}
		
		Helper.redirect("/WEB-INF/PayLoan.jsp", req, res);
	}	
	
	private void requestLoan(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException
	{
		try
		{
			int accountId = Integer.parseInt(req.getParameter("destinationAccountId"));
			int loanTypeId = Integer.parseInt(req.getParameter("loanType"));
			int installmentsQty = Integer.parseInt(req.getParameter("installmentsQty"));
			BigDecimal requestedAmount = new BigDecimal(req.getParameter("requestedAmount"));
			BigDecimal interestRate = new BigDecimal(req.getParameter("interestRate"));
			
			if(accountId == 0 || loanTypeId == 0)
			{
				Helper.setReqMessage(req, 
						"Debe seleccionar el Motivo y Cuenta Destino", MessageType.ERROR);
				viewApplyForLoan(req,res);
				return;
			}
			
			LoanType loanType = new LoanType();
			loanType.setId(loanTypeId);
			
			LoanStatus loanStatus = new LoanStatus();
			loanStatus.setId(1); // ESTADO: EN REVISIÓN - ID 1 
			
			Account account = new Account();
			account = accountsBusiness.read(accountId);
			
			Loan loan = new Loan();
			loan.setClient(client);
			loan.setRequestedAmount(requestedAmount);
			loan.setInterestRate(interestRate);
			loan.setInstallmentsQuantity(installmentsQty);
			loan.setLoanType(loanType);
			loan.setLoanStatus(loanStatus);
			loan.setAccount(account);
			
			boolean success = loansBusiness.create(loan);
			if(success)
			{
				Helper.setReqMessage(req, 
						"Solicitud de aprobación enviada con éxito!", MessageType.SUCCESS);
			} else
			{
				Helper.setReqMessage(req, 
						"Ocurrió un error al solicitar el préstamo.", MessageType.ERROR);
			}
			
		} catch (BusinessException ex)
		{
			Helper.setReqMessage(req, ex.getMessage(), MessageType.ERROR);
			viewClientLoans(req,res);
			return;
		} catch (Exception ex)
		{
			Helper.setReqMessage(req, "Ocurrió un error desconocido.", MessageType.ERROR);
		}
		
		getSessionClient(req); // Actualiza los datos completos del cliente
		viewClientLoans(req,res);
	}

	private void viewApplyForLoan(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
	{
		List<LoanType> loanTypes = new ArrayList<LoanType>();
		
		try
		{
			loanTypes = loanTypesBusiness.list();
		} catch (BusinessException ex)
		{
			Helper.setReqMessage(req, ex.getMessage(), MessageType.ERROR);
			Helper.redirect("/WEB-INF/ApplyForLoan.jsp", req, res);
			return;
		}
		
		req.setAttribute("loanTypes", loanTypes);
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
			accountsList = accountsBusiness.list(clientId);
			
			List<Loan> loansList = new ArrayList<Loan>();
			  
			loansList = loansBusiness.list(client);
	
			client.setLoans(loansList); // TODO: Corregir
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
