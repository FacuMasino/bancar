package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import businessLogicImpl.ClientsBusiness;
import businessLogicImpl.LoansBusiness;
import businessLogicImpl.ReportsBusiness;
import dataAccessImpl.ReportsDao;
import domainModel.Loan;
import domainModel.LoanStatus;
import domainModel.LoanStatusEnum;
import domainModel.Message.MessageType;
import exceptions.BusinessException;
import utils.Helper;

@WebServlet(urlPatterns = {"/Admin","/Admin/"})
public class AdminPanelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ReportsBusiness reportsBusiness;
	private ClientsBusiness clientsBusiness;
	private LoansBusiness loansBusiness;
	
	private int clientsQty;
	private int approvedLoansQty;
	private List<Loan> loansList;

    public AdminPanelServlet() {
    	
        reportsBusiness = new ReportsBusiness();
        clientsBusiness = new ClientsBusiness();
        loansBusiness = new LoansBusiness();
        loansList = new ArrayList<Loan>();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginSuccess = Optional.ofNullable(request.getParameter("login"))
				.orElse("");
		if(loginSuccess.equals("true"))
		{
			Helper.setReqMessage(request, "Iniciaste sesión con éxito!", MessageType.SUCCESS);
		}
		
		/////
		//Pruebita fechas
		Date startDate = Date.valueOf("2023-01-10");
		Date endDate = Date.valueOf("2023-01-11");
		//ArrayList<Loan> listita = (ArrayList<Loan>) reportsBusiness.getLoansByDateRange(startDate, endDate);
		/////
		
		showStaticData(request,response);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/AdminPanel.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	void showStaticData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		try
		{
			//Muestro Cantidad de clientes
			clientsQty = clientsBusiness.list().size();
			
			//Muestro Cantidad de prestamos vigentes
			loansList = loansBusiness.list();
			LoanStatus loanStatus = new LoanStatus();
			loanStatus.setId(LoanStatusEnum.APPROVED.getId());
			approvedLoansQty = loansBusiness.filter(loanStatus, loansList).size();
			
			//Muestro Deuda total por prestamos
			BigDecimal totalPendingAmount = reportsBusiness.getOutstandingInstallmentsAmount();
			
			request.setAttribute("clientsQty", clientsQty);
			request.setAttribute("approvedLoansQty", approvedLoansQty);
			request.setAttribute("totalPendingAmount", totalPendingAmount);
		} 
		catch (BusinessException e)
		{
			e.printStackTrace();
		}
	}
}