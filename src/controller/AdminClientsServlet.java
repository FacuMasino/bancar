package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import businessLogicImpl.AccountsBusiness;
import businessLogicImpl.ClientsBusiness;
import businessLogicImpl.ProvincesBusiness;
import businessLogicImpl.RolesBusiness;
import dataAccessImpl.MovementsDao;
import businessLogicImpl.CountriesBusiness;
import businessLogicImpl.EmailBusiness;
import businessLogicImpl.LoansBusiness;
import domainModel.Account;
import domainModel.Address;
import domainModel.City;
import domainModel.Client;
import domainModel.Country;
import domainModel.Loan;
import domainModel.Province;
import domainModel.Message.MessageType;
import domainModel.Movement;
import domainModel.MovementType;
import exceptions.BusinessException;
import exceptions.InvalidFieldsException;
import exceptions.NoActiveAccountsException;
import utils.Helper;
import utils.Page;

@WebServlet(urlPatterns = {"/Admin/Clients", "/Admin/Clients/"})
public class AdminClientsServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private ClientsBusiness clientsBusiness;
	private AccountsBusiness accountsBusiness;
	private LoansBusiness loansBusiness;
	private ProvincesBusiness provincesBusiness;
	private CountriesBusiness countriesBusiness;
	private RolesBusiness rolesBusiness;
	private Client client;
	private EmailBusiness emailBusiness;

	public AdminClientsServlet()
	{
		super();
		clientsBusiness = new ClientsBusiness();
		accountsBusiness = new AccountsBusiness();
		loansBusiness = new LoansBusiness();
		provincesBusiness = new ProvincesBusiness();
		countriesBusiness = new CountriesBusiness();
		rolesBusiness = new RolesBusiness();
		emailBusiness = new EmailBusiness();
		client = new Client();
	}

	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException
	{
		String action = request.getParameter("action");

		if (action == null || action.isEmpty())
		{
			listClients(request, response);
			return;
		}

		switch (action)
		{
			case "view":
				viewClient(request, response);
				break;
			case "new":
				newClient(request, response);
				break;
			case "edit":
				editClient(request, response);
				break;
			default:
				listClients(request, response);
		}
	}

	protected void doPost(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException
	{
		String action = request.getParameter("action");

		switch (action)
		{
			case "newClient":
				saveNewClient(request, response);
				break;
			case "saveClient":
				saveEditClient(request, response);
				break;
			case "toggleActiveStatus":
				toggleActiveStatus(request, response);
				break;
			default:
				listClients(request, response);
		}
	}
	
	private void listClients(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException
	{
		int page = Optional.ofNullable(
				request.getParameter("page")).map(Integer::parseInt).orElse(1);

		int pageSize = Optional.ofNullable(
				request.getParameter("pageSize")).map(Integer::parseInt).orElse(10);
		try
		{
			ArrayList<Client> clientsList = clientsBusiness.list();
			loadClientsAccounts(clientsList);
			
			Page<Client> clientsPage =
					new Page<Client>(page, pageSize, clientsList);
			
			request.setAttribute("page", clientsPage);
			Helper.redirect("/WEB-INF/AdminClients.jsp", request, response);
		}
		catch (BusinessException ex)
		{
			Helper.setReqMessage(request, ex.getMessage(), MessageType.ERROR);
			Helper.redirect("/WEB-INF/AdminClients.jsp", request, response);
		}
	}
	
	private void viewClient(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException
	{
		int clientId = Optional.ofNullable(
				request.getParameter("clientId")).map(Integer::parseInt).orElse(0);

		try
		{
			ArrayList<Account> accountsList = new ArrayList<Account>();
			List<Loan> loansList = new ArrayList<Loan>();
			
			client = clientsBusiness.read(clientId);
			accountsList = accountsBusiness.list(clientId);
			loansList = loansBusiness.list(client);
			client.setLoans(loansList);
			client.setAccounts(accountsList);

			request.setAttribute("client", client);
			Helper.redirect("/WEB-INF/AdminViewClient.jsp", request, response);
		}
		catch (BusinessException ex)
		{
			Helper.setReqMessage(request, ex.getMessage(), MessageType.ERROR);
			listClients(request, response);
			ex.printStackTrace();
		}
	}
	
	private void newClient(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException
	{
		try
		{	
			request.setAttribute("provinces", provincesBusiness.list());
			Helper.redirect("/WEB-INF/AdminNewClient.jsp", request, response);
		}
		catch (BusinessException ex)
		{
			Helper.setReqMessage(
					request, ex.getMessage(), MessageType.ERROR);
			
			Helper.redirect("/WEB-INF/AdminNewClient.jsp", request, response);
			ex.printStackTrace();
		}
	}

	private void editClient(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException
	{
		int clientId = Optional.ofNullable(
				request.getParameter("clientId")).map(Integer::parseInt).orElse(0);

		try
		{
			Client client = clientsBusiness.read(clientId);
			request.setAttribute("client", client);
			request.setAttribute("provinces", provincesBusiness.list());
			Helper.redirect("/WEB-INF/AdminEditClient.jsp", request, response);
		}
		catch (BusinessException ex)
		{
			Helper.setReqMessage(request, ex.getMessage(), MessageType.ERROR);
			listClients(request, response);
			ex.printStackTrace();
		}
	}

	private void saveNewClient(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException
	{
		Client client = new Client();

		client.setUsername(request.getParameter("clientUsername"));
		client.setPassword(request.getParameter("clientPassword"));

		try
		{
			client.setRole(rolesBusiness.read(2));
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
		}

		client.setDni(request.getParameter("clientDni"));
		client.setCuil(request.getParameter("clientCuil"));
		client.setFirstName(request.getParameter("clientFirstName"));
		client.setLastName(request.getParameter("clientLastName"));
		client.setSex(request.getParameter("clientSex"));
		client.setEmail(request.getParameter("clientEmail"));
		client.setPhone(request.getParameter("clientPhone"));
		String clientBirth = request.getParameter("clientBirth");
		client.setBirthDate(Date.valueOf(clientBirth));
		
		Country nationality = new Country();
		nationality.setName(request.getParameter("clientNationality"));
		client.setNationality(nationality);

		Address address = new Address();
		address.setStreetName(request.getParameter("clientStreetName"));
		address.setStreetNumber(request.getParameter("clientStreetNumber"));
		address.setFlat(request.getParameter("clientFlat"));
		address.setDetails(request.getParameter("clientDetails"));

		City city = new City();
		city.setName(request.getParameter("clientCity"));
		city.setZipCode(request.getParameter("clientZipCode"));
		address.setCity(city);

		try
		{
			String clientProvinceId = request.getParameter("clientProvinceId");
			int provinceId = Integer.parseInt(clientProvinceId);
			Province province;
			province = provincesBusiness.read(provinceId);
			address.setProvince(province);
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
		}

		try
		{
			address.setCountry(countriesBusiness.read(1));
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
		}

		client.setAddress(address);

		try
		{
			clientsBusiness.create(client);
			emailBusiness.sendWelcome(client);
			Helper.setReqMessage(
					request, "Cliente creado exitosamente.", MessageType.SUCCESS);
			listClients(request, response);
		}
		catch (MessagingException ex)
		{
			Helper.setReqMessage(request,
					"El cliente fue creado con éxito pero no pudo enviarse el email de bienvenida",
					MessageType.SUCCESS);
			listClients(request, response);
		}
		catch (InvalidFieldsException ex)
		{
			request.setAttribute("draftClient", client);
			Helper.setReqErrorList(request, ex.getInvalidFields());
			newClient(request, response);
		}
		catch (BusinessException ex)
		{
			Helper.setReqMessage(
					request, ex.getMessage(), MessageType.ERROR);

			listClients(request, response);
			ex.printStackTrace();
		}
	}
		    
	private void saveEditClient(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException
	{
		int clientId = Optional.ofNullable(
				request.getParameter("clientId")).map(Integer::parseInt).orElse(0);

		try
		{
			client = clientsBusiness.read(clientId);
			
			client.setDni(request.getParameter("clientDni"));
			client.setCuil(request.getParameter("clientCuil"));
			client.setFirstName(request.getParameter("clientFirstName"));
			client.setLastName(request.getParameter("clientLastName"));
			client.setSex(request.getParameter("clientSex"));
			client.setEmail(request.getParameter("clientEmail"));
			client.setPhone(request.getParameter("clientPhone"));
			
			String birthDate = request.getParameter("clientBirthDate");
			client.setBirthDate(Date.valueOf(birthDate));
			
			client.getNationality().setName(request.getParameter("clientNationality"));
			
			client.getAddress().setStreetName(request.getParameter("clientStreetName"));
			client.getAddress().setStreetNumber(request.getParameter("clientStreetNumber"));
			client.getAddress().setFlat(request.getParameter("clientFlat"));
			client.getAddress().setDetails(request.getParameter("clientDetails"));
			
			String clientCity = request.getParameter("clientCity");
			client.getAddress().getCity().setName(clientCity);

			String clientZipCode = request.getParameter("clientZipCode");
			client.getAddress().getCity().setZipCode(clientZipCode);

			String clientProvinceId = request.getParameter("clientProvinceId");
			int provinceId = Integer.parseInt(clientProvinceId);
			client.getAddress().setProvince(provincesBusiness.read(provinceId));
			
			clientsBusiness.update(client);

			Helper.setReqMessage(
					request, "El ciente se modificó con éxito", MessageType.SUCCESS);
			
			listClients(request, response);
		} 
		catch (InvalidFieldsException ex)
		{
			request.setAttribute("draftClient", client);
			Helper.setReqErrorList(request, ex.getInvalidFields());
			editClient(request, response);
		} 
		catch (BusinessException ex)
		{
			Helper.setReqMessage(request, ex.getMessage(), MessageType.ERROR);
			editClient(request, response);
			ex.printStackTrace();
		}
		
	}

	private void toggleActiveStatus(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException
	{
		int clientId = Optional.ofNullable(
				request.getParameter("clientId")).map(Integer::parseInt).orElse(0);

		try
		{
			client = clientsBusiness.read(clientId);

			clientsBusiness.toggleActiveStatus(
					clientId, client.getActiveStatus());

			String msg = client.getActiveStatus() ? "dió de baja" : "reactivó";

			Helper.setReqMessage(
					request, "El cliente se " + msg + " con éxito", MessageType.SUCCESS);
			
			listClients(request, response);
		}
		catch (BusinessException ex)
		{
			Helper.setReqMessage(request, ex.getMessage(), MessageType.ERROR);
			listClients(request, response);
		}
	}
	
	private void loadClientsAccounts(ArrayList<Client> clients)
			throws BusinessException
	{
		for (Client client : clients)
		{
			ArrayList<Account> accounts;

			try
			{
				accounts = accountsBusiness
						.list(client.getClientId());
				client.setAccounts(accounts);
			}
			catch (NoActiveAccountsException ex)
			{
				client.setAccounts(Collections.emptyList());
				continue;
			}
			catch (BusinessException ex)
			{
				throw new BusinessException(
						"Error al obtener las cuentas de clientes.");
			}
		}
	}
}