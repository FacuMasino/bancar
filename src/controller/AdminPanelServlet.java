package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

import businessLogicImpl.AccountsBusiness;
import businessLogicImpl.ClientsBusiness;
import businessLogicImpl.LoansBusiness;
import businessLogicImpl.ReportsBusiness;
import domainModel.Account;
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
	private AccountsBusiness accountsBusiness;
	private LoansBusiness loansBusiness;
	
	private int clientsQty;
	private int approvedLoansCount;
	private int overdueLoansCount;
	private List<Loan> loansList;

    public AdminPanelServlet() {
    	
        reportsBusiness = new ReportsBusiness();
        clientsBusiness = new ClientsBusiness();
        accountsBusiness = new AccountsBusiness();
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
			//Muestro fondos totales, suponemos en principio, la suma de todas las cuentas..
			BigDecimal totalFunds = accountsBusiness.list().stream().map(Account::getBalance).reduce(BigDecimal.ZERO,BigDecimal::add);
			
			//Muestro Cantidad de clientes
			//clientsQty = clientsBusiness.list().size();
			clientsQty = clientsBusiness.listActiveClients().size();
			
			//Muestro Cantidad de prestamos vigentes y vencidas
			approvedLoansCount = calculateApprovedLoansCount();
			overdueLoansCount = reportsBusiness.getOverdueLoansCount();
			
			//Muestro Deuda total por prestamos y tasa morosidad
			BigDecimal totalPendingAmount = reportsBusiness.getOutstandingInstallmentsAmount();
			BigDecimal defaultRate = calculateDefaultRate();
			
			//Mapeo
			request.setAttribute("clientsQty", clientsQty);
			request.setAttribute("approvedLoansCount", approvedLoansCount);
			request.setAttribute("overdueLoansCount", overdueLoansCount);
			request.setAttribute("totalPendingAmount", totalPendingAmount);
			request.setAttribute("totalFunds", totalFunds);
			request.setAttribute("defaultRate", defaultRate);
		} 
		catch (BusinessException e)
		{
			e.printStackTrace();
		}
	}
	
	private int calculateApprovedLoansCount()
	{
		int approvedLoansCount = 0; 
		
		try
		{
			loansList = loansBusiness.list();
			LoanStatus loanStatus = new LoanStatus();
			loanStatus.setId(LoanStatusEnum.APPROVED.getId());
			approvedLoansCount = loansBusiness.filter(loanStatus, loansList).size();
		} catch (BusinessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return approvedLoansCount;
	}

	private BigDecimal calculateDefaultRate() throws BusinessException
	{
		BigDecimal overdueLoansCount = new BigDecimal(reportsBusiness.getOverdueLoansCount());
		BigDecimal approvedLoansCount = new BigDecimal(calculateApprovedLoansCount());
		
		System.out.println("overdueLoansCount: " + overdueLoansCount);
		System.out.println("approvedLoansCount: " + approvedLoansCount);
		
		if(approvedLoansCount == BigDecimal.valueOf(0))
			return new BigDecimal(0);
		else
			return  overdueLoansCount.divide(approvedLoansCount, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
	}
}