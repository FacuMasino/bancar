package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import businessLogicImpl.AccountsBusiness;
import businessLogicImpl.ClientsBusiness;
import businessLogicImpl.LoanStatusesBusiness;
import businessLogicImpl.LoansBusiness;
import domainModel.Account;
import domainModel.AccountType;
import domainModel.Client;
import domainModel.Loan;
import domainModel.LoanStatus;
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
	private LoanStatusesBusiness loanStatusesBusiness;

	public AdminLoansServlet()
	{
		super();
		accountsBusiness = new AccountsBusiness();
		clientsBusiness = new ClientsBusiness();
		loansBusiness = new LoansBusiness();
		loanStatusesBusiness = new LoanStatusesBusiness();
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
	//TODO: AL APROBAR EL LOAN, GENERAR LOS INSTALLMENTS!! o.O
	//TODO: MAPEAR EL MINI REPORTE DEL DINERO INVERTIDO,PRESTADO Y COSO
	
	private void approveLoan(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		int loanId = Optional.ofNullable(request.getParameter("selectedLoanId"))
				.map(Integer::parseInt).orElse(0);
		try
		{
			LoanStatus loanStatusApproved = loanStatusesBusiness.read(2); // 2 is for approved, Vigente en BD!
																			
			Loan loanToApprove = loansBusiness.read(loanId);
			loanToApprove.setLoanStatus(loanStatusApproved);
			Boolean success = loansBusiness.update(loanToApprove);

			if (success)
			{
				Helper.setReqMessage(request, "Préstamo aprobado con éxito!",
						MessageType.SUCCESS);
			} else
			{
				Helper.setReqMessage(request, "Error al aprobar préstamo.",
						MessageType.ERROR);
			}
			viewAdminLoans(request, response);
		} catch (Exception ex) // Reemplazar por BusinessException
		{
			Helper.setReqMessage(request, ex.getMessage(), MessageType.ERROR);
			viewAdminLoans(request, response);
		}
	}

	private void rejectLoan(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		int loanId = Optional.ofNullable(request.getParameter("selectedLoanId"))
				.map(Integer::parseInt).orElse(0);
		try
		{
			// Pruebita
			System.out.println("El id Loan que clickeeeee es: " + loanId);

			LoanStatus loanStatusApproved = loanStatusesBusiness.read(4); // 2 is for rejeted, Rechazado en BD!

			Loan loanToApprove = loansBusiness.read(loanId);
			loanToApprove.setLoanStatus(loanStatusApproved);
			Boolean success = loansBusiness.update(loanToApprove);

			System.out.println("\n\nEl prestamo que rechace tiene esto: "
					+ loanToApprove.toString());

			if (success)
			{
				Helper.setReqMessage(request,
						"Se rechazó el préstamo con éxito!",
						MessageType.SUCCESS);
			} else
			{
				Helper.setReqMessage(request, "Error al rechazar préstamo.",
						MessageType.ERROR);
			}
			viewAdminLoans(request, response);
		} catch (Exception ex) // Reemplazar por BusinessException
		{
			Helper.setReqMessage(request, ex.getMessage(), MessageType.ERROR);
			viewAdminLoans(request, response);
		}
	}

	private void viewAdminLoans(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		// TODO: Cargar lista cuando funcione eḷ negocio
		// Enviar atributo con la lista
		/*
		 * 1 En revisión 2 Vigente 3 Finalizado 4 Rechazado
		 */
		try
		{
			List<Loan> loans = loansBusiness.list();

			ArrayList<Loan> pendingLoans = (ArrayList<Loan>) loansBusiness
					.filter(loanStatusesBusiness.read(1), loans);
			ArrayList<Loan> approvedLoans = (ArrayList<Loan>) loansBusiness
					.filter(loanStatusesBusiness.read(2), loans);
			ArrayList<Loan> endedLoans = (ArrayList<Loan>) loansBusiness
					.filter(loanStatusesBusiness.read(3), loans);
			ArrayList<Loan> rejectedLoans = (ArrayList<Loan>) loansBusiness
					.filter(loanStatusesBusiness.read(4), loans);

			request.setAttribute("pendingLoans", pendingLoans);
			request.setAttribute("approvedLoans", approvedLoans);
			request.setAttribute("endedLoans", endedLoans);
			request.setAttribute("rejectedLoans", rejectedLoans);

		} catch (BusinessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Helper.redirect("/WEB-INF/AdminLoans.jsp", request, response);
	}

}
