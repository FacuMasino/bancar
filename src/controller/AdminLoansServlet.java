package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import businessLogicImpl.LoanStatusesBusiness;
import businessLogicImpl.LoansBusiness;
import domainModel.Loan;
import domainModel.LoanStatus;
import domainModel.LoanStatusEnum;
import domainModel.Message.MessageType;
import exceptions.BusinessException;
import utils.Helper;

@WebServlet(urlPatterns = { "/Admin/Loans", "/Admin/Loan/" })
public class AdminLoansServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private LoansBusiness loansBusiness;
	private LoanStatusesBusiness loanStatusesBusiness;

	public AdminLoansServlet()
	{
		super();
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

	private void approveLoan(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		int loanId = Optional.ofNullable(request.getParameter("selectedLoanId"))
				.map(Integer::parseInt).orElse(0);
		try
		{
			
			Boolean success = loansBusiness.approve(loansBusiness.read(loanId));

			if (success)
			{
				Helper.setReqMessage(request, "Préstamo aprobado con éxito!",
						MessageType.SUCCESS);
			}
			viewAdminLoans(request, response);
		} catch (BusinessException ex)
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
			LoanStatus rejectedStatus = new LoanStatus();
			rejectedStatus.setId(LoanStatusEnum.REJECTED.getId());

			Loan loanToReject = loansBusiness.read(loanId);
			loanToReject.setLoanStatus(rejectedStatus);
			Boolean success = loansBusiness.update(loanToReject);

			if (success)
			{
				Helper.setReqMessage(request,
						"Se rechazó el préstamo con éxito!",
						MessageType.SUCCESS);
			}
			
			viewAdminLoans(request, response);
		} catch (BusinessException ex)
		{
			Helper.setReqMessage(request, ex.getMessage(), MessageType.ERROR);
			viewAdminLoans(request, response);
		}
	}

	private void viewAdminLoans(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{

		try
		{
			List<Loan> allLoans = loansBusiness.list();
			allLoans.sort(null); // Orden por defecto, ascendente por fecha
			
			LoanStatus pendingStatus = new LoanStatus();
			pendingStatus.setId(LoanStatusEnum.PENDING.getId());
			
			List<Loan> pendingLoans = loansBusiness.filter(pendingStatus, allLoans);

			request.setAttribute("pendingLoans", pendingLoans);
			request.setAttribute("allLoans", allLoans);

		} catch (BusinessException ex)
		{
			Helper.setReqMessage(request, ex.getMessage(), MessageType.ERROR);
		}

		Helper.redirect("/WEB-INF/AdminLoans.jsp", request, response);
	}

}
