package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import businessLogic.IAccountsBusiness;
import businessLogic.IClientsBusiness;
import businessLogic.ILoansBusiness;
import businessLogic.IReportsBusiness;
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
import utils.MapTools;

@WebServlet(urlPatterns = { "/Admin", "/Admin/" })
public class AdminPanelServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private IReportsBusiness reportsBusiness;
	private IClientsBusiness clientsBusiness;
	private IAccountsBusiness accountsBusiness;
	private ILoansBusiness loansBusiness;
	private int clientsQty;
	private int approvedLoansCount;
	private int overdueLoansCount;
	private List<Loan> loansList;
	private HashMap<String, Integer> clientsByProvince;
	private BigDecimal totalFunds;
	private BigDecimal totalPendingAmount;
	private BigDecimal defaultRate;
	private BigDecimal profitsEarned;
	private BigDecimal profitsToEarn;
	private String provinces;
	private String provinceClients;
	private LinkedHashMap<String, BigDecimal> loansAmountByPeriod;
	private LinkedHashMap<String, BigDecimal> transfersAmountByPeriod;
	private String periods;
	private String loansGivenAmount;
	private String transfersDoneAmount;

	public AdminPanelServlet()
	{
		reportsBusiness = new ReportsBusiness();
		clientsBusiness = new ClientsBusiness();
		accountsBusiness = new AccountsBusiness();
		loansBusiness = new LoansBusiness();
		loansList = new ArrayList<Loan>();
		clientsByProvince = new HashMap<>();
		totalFunds = new BigDecimal(0);
		totalPendingAmount = new BigDecimal(0);
		defaultRate = new BigDecimal(0);
		profitsEarned = new BigDecimal(0);
		profitsToEarn = new BigDecimal(0);
		provinces = new String();
		provinceClients = new String();
		loansAmountByPeriod = new LinkedHashMap<>();
		transfersAmountByPeriod = new LinkedHashMap<>();
		periods = new String();
		loansGivenAmount = new String();
		transfersDoneAmount = new String();
	}

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		String loginSuccess = Optional.ofNullable(
				request.getParameter("login")).orElse("");

		if (loginSuccess.equals("true"))
		{
			Helper.setReqMessage(request, "Iniciaste sesión con éxito!",
					MessageType.SUCCESS);
		}
		
		manageBarChart(request);
		
		showStaticData(request);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/AdminPanel.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

	private void manageBarChart(HttpServletRequest request)
	{
		LocalDate currentDate = LocalDate.now();
		LocalDate firstDay = currentDate.with(TemporalAdjusters.firstDayOfYear());
		LocalDate firstDayNextYear = currentDate.with(TemporalAdjusters.lastDayOfYear()).plusDays(1);
		
		String startDate = Optional.ofNullable(
				request.getParameter("startDate")).filter(date -> !date.isEmpty()).orElse(firstDay.toString());

		String endDate = Optional.ofNullable(
				request.getParameter("endDate")).filter(date -> !date.isEmpty()).orElse(firstDayNextYear.toString());
		
		if (!LocalDate.parse(endDate).isAfter(LocalDate.parse(startDate)))
		{
			startDate = firstDay.toString();
			endDate = firstDayNextYear.toString();
			Helper.setReqMessage(request, "La fecha de inicio debe ser anterior...", MessageType.ERROR);
		}
		
		try
		{
			if(LocalDate.parse(startDate).getYear() == LocalDate.parse(endDate).getYear() && LocalDate.parse(startDate).getMonth() == LocalDate.parse(endDate).getMonth())
			{
				loansAmountByPeriod = (LinkedHashMap<String, BigDecimal>) reportsBusiness.getLoansAmountByDayPeriod(LocalDate.parse(startDate), LocalDate.parse(endDate));
				transfersAmountByPeriod = (LinkedHashMap<String, BigDecimal>) reportsBusiness.getTransfersAmountByDayPeriod(LocalDate.parse(startDate), LocalDate.parse(endDate));
			}
			else
			{
				loansAmountByPeriod = (LinkedHashMap<String, BigDecimal>) reportsBusiness.getLoansAmountByMonthPeriod(LocalDate.parse(startDate), LocalDate.parse(endDate));
				transfersAmountByPeriod = (LinkedHashMap<String, BigDecimal>) reportsBusiness.getTransfersAmountByMonthPeriod(LocalDate.parse(startDate), LocalDate.parse(endDate));
			}
		} 
		catch (BusinessException e)
		{
			e.printStackTrace();
		}

		periods = MapTools.mapKeysToLiteralString(loansAmountByPeriod);
		loansGivenAmount = MapTools.mapValuesToLiteralString(loansAmountByPeriod);
		transfersDoneAmount = MapTools.mapValuesToLiteralString(transfersAmountByPeriod);

		request.setAttribute("defaultStartDate", startDate);
		request.setAttribute("defaultEndDate", endDate);
		request.setAttribute("periods", periods);
		request.setAttribute("loansGivenAmount", loansGivenAmount);
		request.setAttribute("transfersDoneAmount", transfersDoneAmount);
	}

	void showStaticData(HttpServletRequest request)throws ServletException, IOException
	{
		try
		{
			// Muestro fondos totales, suponemos en principio, la suma de todas las cuentas
			totalFunds = accountsBusiness.list().stream().map(Account::getBalance).reduce(BigDecimal.ZERO, BigDecimal::add);

			// Muestro Cantidad de clientes
			clientsQty = clientsBusiness.listActiveClients().size();

			// Muestro Cantidad de prestamos vigentes y vencidas
			approvedLoansCount = calculateApprovedLoansCount();
			overdueLoansCount = reportsBusiness.getOverdueLoansCount();

			// Muestro Deuda total por prestamos y tasa morosidad
			totalPendingAmount = reportsBusiness.getOutstandingInstallmentsAmount();
			defaultRate = calculateDefaultRate();

			// Muestro Ganancias obtenidas y ganancias futuras
			profitsEarned = reportsBusiness.profitsEarned();
			profitsToEarn = reportsBusiness.profitsToEarn();

			// Muestro Clientes por Provincia charDonut
			clientsByProvince = (HashMap<String, Integer>) reportsBusiness.getClientsByProvince();
			provinces = MapTools.mapKeysToLiteralString(clientsByProvince);
			provinceClients = MapTools.mapValuesToLiteralString(clientsByProvince);
			System.out.println("Clientes por Provincia: " + clientsByProvince.toString());

			mapAllDataToFront(request);
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
		}
	}

	private void mapAllDataToFront(HttpServletRequest request)
	{
		request.setAttribute("clientsQty", clientsQty);
		request.setAttribute("approvedLoansCount", approvedLoansCount);
		request.setAttribute("overdueLoansCount", overdueLoansCount);
		request.setAttribute("totalPendingAmount", totalPendingAmount);
		request.setAttribute("totalFunds", totalFunds);
		request.setAttribute("defaultRate", defaultRate);
		request.setAttribute("profitsEarned", profitsEarned);
		request.setAttribute("profitsToEarn", profitsToEarn);
		request.setAttribute("provinces", provinces);
		request.setAttribute("provinceClients", provinceClients);
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
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
		}
		return approvedLoansCount;
	}
	
	/**
	 * 
	 * Metodo que calcula la tasa de morosidad segun la formula: (Cantidad
	 * prestamos con cuotas impagas) / (Cantidad de prestamos aprobados) * 100
	 */
	private BigDecimal calculateDefaultRate()
	{
		BigDecimal overdueLoansCount = new BigDecimal(0);
		BigDecimal approvedLoansCount = new BigDecimal(calculateApprovedLoansCount());

		try
		{
			overdueLoansCount = new BigDecimal(reportsBusiness.getOverdueLoansCount());
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
		}

		if (approvedLoansCount == BigDecimal.valueOf(0))
		{			
			return new BigDecimal(0);
		}
		else
		{			
			return overdueLoansCount.divide(approvedLoansCount, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
		}
	}
}