package controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import businessLogicImpl.AccountsBusiness;
import businessLogicImpl.ClientsBusiness;
import businessLogicImpl.LoansBusiness;
import businessLogicImpl.ProvincesBusiness;
import domainModel.Account;
import domainModel.Address;
import domainModel.City;
import domainModel.Client;
import domainModel.Country;
import domainModel.Loan;
import domainModel.Province;
import domainModel.Role;
import exceptions.BusinessException;
import utils.Helper;
import utils.Page;

@WebServlet(urlPatterns = {"/Admin/Clients","/Admin/Clients/"})
public class AdminClientsServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private ClientsBusiness clientsBusiness;
	private AccountsBusiness accountsBusiness;
	private LoansBusiness loansBusiness;
	private ProvincesBusiness provincesBusiness;
	private Client client;
	
	public AdminClientsServlet()
	{
		super();
		clientsBusiness = new ClientsBusiness();
		accountsBusiness = new AccountsBusiness();
		loansBusiness = new LoansBusiness();
		provincesBusiness = new ProvincesBusiness();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{	
		String action = request.getParameter("action");
		if(action == null || action.isEmpty())
		{
			try
			{
				listClients(request, response);
			}
			catch (ServletException | IOException | BusinessException e)
			{
				e.printStackTrace();
			}
			return;
		}

		switch (action) 
		{
			case "new":
				newClient(request,response);
				break;
			case "view":
				viewClient(request, response);
				break;
			case "edit":
				editClient(request,response);
				break;
			case "manageAccounts":
				manageAccounts(request, response);
			default:
				try
				{
					listClients(request, response);
				}
				catch (ServletException | IOException | BusinessException e)
				{
					e.printStackTrace();
				}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String action = request.getParameter("action");

		switch (action) 
		{
			case "newClient":
				//TODO algunas validaciones de negocio, por ejemplo: verificar nombre de usuario disponible
				//TODO implementar BusinessException
				saveNewClient(request, response);
				break;
			case "saveClient":
				saveClient(request, response);
				break;
			case "toggleActiveStatus":
				try
				{
					toggleActiveStatus(request, response);
				}
				catch (BusinessException e)
				{
					e.printStackTrace();
				}
				break;
			default:
				try
				{
					listClients(request, response);
				}
				catch (ServletException | IOException | BusinessException e)
				{
					e.printStackTrace();
				}
		}
	}

	private void saveNewClient(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		Client client = new Client();

		client.setUsername(request.getParameter("clientUsername"));
		client.setPassword(request.getParameter("clientPassword"));
		
		Role role = new Role();
		role.setId(2);
		role.setName("Cliente");
		client.setRole(role);
		
		client.setDni(request.getParameter("clientDni"));
		client.setCuil(request.getParameter("clientCuil"));
		client.setFirstName(request.getParameter("clientFirstName"));
		client.setLastName(request.getParameter("clientLastName"));
		client.setSex(request.getParameter("clientSex"));
		client.setEmail(request.getParameter("clientEmail"));
		client.setPhone(request.getParameter("clientPhone"));
		client.setBirthDate(Date.valueOf(request.getParameter("clientBirth")));

		Address address = new Address();
		address.setStreetName(request.getParameter("clientStreetName"));
		address.setStreetNumber(request.getParameter("clientStreetNumber"));

		City city = new City();
		city.setName(request.getParameter("clientCity"));
		address.setCity(city);

		Province province = new Province();
		province.setName(request.getParameter("clientProvinceId"));
		address.setProvince(province);

		Country country = new Country();
		country.setId(1);
		country.setName("Argentina");
		address.setCountry(country);

		client.setAddress(address);
		client.setNationality(country);

		try
		{
			clientsBusiness.create(client);
			// TODO: reemplazar println() por modal "Cliente creado exitosamente."
			System.out.println("Cliente creado exitosamente.");
			Helper.redirect("/WEB-INF/AdminPanel.jsp", request, response);
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
		}
	}

	private void newClient(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		try
		{
			request.setAttribute("provinces", provincesBusiness.list());
			Helper.redirect("/WEB-INF/AdminNewClient.jsp", request, response);
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
		}
	}
	
	private void editClient(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		int id = Optional.ofNullable(request.getParameter("clientId"))
				.map(Integer::parseInt)
				.orElse(0);
		
		try
		{
			Client client = clientsBusiness.read(id);
			request.setAttribute("client", client);
			request.setAttribute("provinces", provincesBusiness.list());
			Helper.redirect("/WEB-INF/AdminEditClient.jsp", request, response);
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
		}
	}
	
	private void saveClient(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		Client client = new Client();
		
		// TODO: Mapear parametros, actualizar el cliente, devolver mensaje de estado
	}
	
	private void listClients(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, BusinessException 
	{
		int page = Optional.ofNullable(request.getParameter("page"))
				.map(Integer::parseInt)
				.orElse(1);

		int pageSize = Optional.ofNullable(request.getParameter("pageSize"))
				.map(Integer::parseInt)
				.orElse(10);
		
		ArrayList<Client> clientsList = clientsBusiness.list();
		Page<Client> clientsPage = new Page<Client>(page,pageSize, clientsList);

		request.setAttribute("page", clientsPage);
		Helper.redirect("/WEB-INF/AdminClients.jsp", request, response);
	}
	
	private void viewClient(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		int clientId = Optional.ofNullable(request.getParameter("clientId"))
				.map(Integer::parseInt)
				.orElse(0);

		try
		{
			Client client = clientsBusiness.read(clientId);
			
			
			ArrayList<Account> accountsList = new ArrayList<Account>();
			accountsList = accountsBusiness.listByIdClient(clientId);
			
			ArrayList <Loan> loansList = new ArrayList <Loan>();
			  
			for (Account account : accountsList)
			  {
			      int accountId = account.getId(); 
			      ArrayList<Loan> accountLoans = loansBusiness.listByIdAccount(accountId);
			      loansList.addAll(accountLoans); 
			  }

			client.setLoans(loansList); 
			client.setAccounts(accountsList);
		
			request.setAttribute("client", client);
			
			Helper.redirect("/WEB-INF/AdminViewClient.jsp", request, response);
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
		}
	}
	
	private void manageAccounts(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		//int clientId = Optional.ofNullable(request.getParameter("clientId")).map(Integer::parseInt).orElse(0);
		
		int clientId = Integer.parseInt(request.getParameter("clientId"));
		System.out.println("Lo que llega al servler..." + clientId);
		try {
			Client client = clientsBusiness.read(clientId);
			System.out.println("\nAl leer e cliente de BD..." + client.getClientId());
			ArrayList<Account> accountsList = new ArrayList<Account>();
			accountsList = accountsBusiness.listByIdClient(clientId);
			client.setAccounts(accountsList);

			//TODO: HACER FUNCION CARGAR CLIENTE!!!
			request.setAttribute("client", client);
			
			System.out.println("\nantes de redirect..." + client.getClientId());
			
			Helper.redirect("/AdminClientAccounts.jsp", request, response);
		}
		catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	private void toggleActiveStatus(HttpServletRequest request, HttpServletResponse response) throws BusinessException, ServletException, IOException
	{
		int clientId = Optional.ofNullable(request.getParameter("clientId"))
				.map(Integer::parseInt)
				.orElse(0);

		try
		{
			client = clientsBusiness.read(clientId);
			clientsBusiness.toggleActiveStatus(clientId, client.getActiveStatus());
			response.sendRedirect("Clients");
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
		}
	}
}
