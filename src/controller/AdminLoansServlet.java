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

import businessLogic.ILoanStatusesBusiness;
import businessLogic.ILoanTypesBusiness;
import businessLogic.ILoansBusiness;
import businessLogic.IReportsBusiness;
import businessLogicImpl.LoanStatusesBusiness;
import businessLogicImpl.LoanTypesBusiness;
import businessLogicImpl.LoansBusiness;
import businessLogicImpl.ReportsBusiness;
import domainModel.Loan;
import domainModel.LoanStatus;
import domainModel.LoanStatusEnum;
import domainModel.LoanType;
import domainModel.Message.MessageType;
import exceptions.BusinessException;
import utils.Helper;
import utils.Page;

@WebServlet(urlPatterns = { "/Admin/Loans", "/Admin/Loan/" })
public class AdminLoansServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private ILoansBusiness loansBusiness;
	private ILoanStatusesBusiness loanStatusesBusiness;
	private ILoanTypesBusiness loanTypesBusiness;
	private IReportsBusiness reportsBusiness;
	private List<Loan> allLoans;
	
	public AdminLoansServlet()
	{
		super();
		loansBusiness = new LoansBusiness();
		loanStatusesBusiness = new LoanStatusesBusiness();
		loanTypesBusiness = new LoanTypesBusiness();
		reportsBusiness = new ReportsBusiness();
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

	private void approveLoan(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException
	{
		int loanId = Optional.ofNullable(req.getParameter("selectedLoanId"))
				.map(Integer::parseInt).orElse(0);
		try
		{
			
			Boolean success = loansBusiness.approve(loansBusiness.read(loanId));

			if (success)
			{
				Helper.setReqMessage(req, "Préstamo aprobado con éxito!",
						MessageType.SUCCESS);
			}
			viewAdminLoans(req, res);
		} catch (BusinessException ex)
		{
			Helper.setReqMessage(req, ex.getMessage(), MessageType.ERROR);
			viewAdminLoans(req, res);
		}
	}

	private void rejectLoan(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException
	{
		int loanId = Optional.ofNullable(req.getParameter("selectedLoanId"))
				.map(Integer::parseInt).orElse(0);
		try
		{
			Loan loanToReject = loansBusiness.read(loanId);
			Boolean success = loansBusiness.reject(loanToReject);

			if (success)
			{
				Helper.setReqMessage(req,
						"Se rechazó el préstamo con éxito!",
						MessageType.SUCCESS);
			}
			
			viewAdminLoans(req, res);
		} catch (BusinessException ex)
		{
			Helper.setReqMessage(req, ex.getMessage(), MessageType.ERROR);
			viewAdminLoans(req, res);
		}
	}

	private void viewAdminLoans(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException
	{

		try
		{
			List<LoanStatus> loanStatuses = new ArrayList<LoanStatus>();
			List<LoanType> loanTypes = new ArrayList<LoanType>();
			
			// Obtener estados y tipos 
			loanStatuses = loanStatusesBusiness.list();
			loanTypes = loanTypesBusiness.list();
			
			// Lista completa de préstamos
			allLoans = loansBusiness.list();
			allLoans.sort(null); // Orden por defecto, ascendente por fecha

			// Obtener listados paginados
			Page<Loan> pendingsPage = getPendingsPage(req);
			Page<Loan> historyPage = getHistoryPage(req);
			
			// Obtener monto total requerido
			LoanStatus pendingStatus = new LoanStatus();
			pendingStatus.setId(LoanStatusEnum.PENDING.getId());
			List<Loan> pendingLoans = loansBusiness.filter(pendingStatus, allLoans);
			BigDecimal totalRequestedAmount = loansBusiness.getRequestedAmount(pendingLoans);
			
			// Obtener monto total adeudado
			BigDecimal overdueLoansAmount = reportsBusiness.getOutstandingInstallmentsAmount();
			
			req.setAttribute("overdueLoansAmount", overdueLoansAmount);
			req.setAttribute("totalRequestedAmount", totalRequestedAmount);
			req.setAttribute("loanStatuses", loanStatuses);
			req.setAttribute("loanTypes", loanTypes);
			req.setAttribute("pendingsPage", pendingsPage);
			req.setAttribute("historyPage", historyPage);

		} catch (BusinessException ex)
		{
			Helper.setReqMessage(req, ex.getMessage(), MessageType.ERROR);
		}

		Helper.redirect("/WEB-INF/AdminLoans.jsp", req, res);
	}
	
	private Page<Loan> getHistoryPage(HttpServletRequest req)
			throws ServletException, IOException, BusinessException
	{
		try
		{
			int page = Optional.ofNullable(
					req.getParameter("historyPage")).map(Integer::parseInt).orElse(1);
			int pageSize = Optional.ofNullable(
					req.getParameter("historyPageSize")).map(Integer::parseInt).orElse(10);
			int loanStatusId = Optional.ofNullable(
					req.getParameter("historyStatusId")).map(Integer::parseInt).orElse(0);
			int loanTypeId= Optional.ofNullable(
					req.getParameter("historyTypeId")).map(Integer::parseInt).orElse(0);
			
			List<Loan> loansHistory = allLoans;
			
			LoanStatus loanStatus = new LoanStatus();
			LoanType loanType = new LoanType();
			
			if(loanStatusId != 0) // Filtrar solo si se utilizó el select de estado
			{
				loanStatus.setId(loanStatusId);
				loansHistory = loansBusiness.filter(loanStatus, loansHistory);
			}
			
			if(loanTypeId != 0) // Filtrar solo si se utilizó el select de tipo
			{
				loanType.setId(loanTypeId);
				loansHistory = loansBusiness.filter(loanType, loansHistory);
			}
			
			Page<Loan> loansHistoryPage = new Page<Loan>(page, pageSize,
					loansHistory);
			
			return loansHistoryPage;
		}
		catch (BusinessException ex)
		{
			throw ex;
		}
	}
	
	private Page<Loan> getPendingsPage(HttpServletRequest req)
			throws ServletException, IOException, BusinessException
	{
		try
		{
			int page = Optional.ofNullable(
					req.getParameter("pendingsPage")).map(Integer::parseInt).orElse(1);
			int pageSize = Optional.ofNullable(
					req.getParameter("pendingsPageSize")).map(Integer::parseInt).orElse(5);
			int loanTypeId= Optional.ofNullable(
					req.getParameter("pendingTypeId")).map(Integer::parseInt).orElse(0);
			
			// Creación del estado PENDIENTE
			LoanStatus loanStatus = new LoanStatus();
			loanStatus.setId(LoanStatusEnum.PENDING.getId());
			
			List<Loan> pendingLoans = loansBusiness.filter(loanStatus, allLoans);
			
			if(loanTypeId != 0) // Filtrar solo si se utilizó el select de tipo
			{
				LoanType loanType = new LoanType();
				loanType.setId(loanTypeId);
				pendingLoans = loansBusiness.filter(loanType, pendingLoans);
			}
			
			Page<Loan> pendingsPage = new Page<Loan>(page, pageSize,
					pendingLoans);
			
			// Seteo otros tamaños de página más chicos para los préstamos pendientes
			pendingsPage.setPageSizes(new int[] {5,10,15,20});
			
			return pendingsPage;
		}
		catch (BusinessException ex)
		{
			throw ex;
		}
	}

}
