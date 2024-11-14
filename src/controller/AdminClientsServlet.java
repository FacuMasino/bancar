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
import businessLogicImpl.ClientsBusiness;
import domainModel.Address;
import domainModel.City;
import domainModel.Client;
import domainModel.Country;
import domainModel.Province;
import exceptions.BusinessException;
import utils.Helper;
import utils.Page;

@WebServlet(urlPatterns = {"/Admin/Clients","/Admin/Clients/"})
public class AdminClientsServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private ClientsBusiness clientsBusiness;
	
	public AdminClientsServlet()
	{
		super();
		clientsBusiness = new ClientsBusiness();
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
				Helper.redirect("/WEB-INF/AdminNewClient.jsp", request, response);
				break;
			case "view":
				viewClient(request, response);
				break;
			case "edit":
				editClient(request,response);
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String action = request.getParameter("action");

		switch (action) 
		{
			case "newClient":
				//TODO algunas validaciones de negocio, por ejemplo: verificar nombre de usuario disponible
				//TODO implementar BusinessException
				newClient(request, response);
				break;
			case "saveClient":
				saveClient(request, response);
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

	private void newClient(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		Client client = new Client();

		client.setUsername(request.getParameter("clientUsername"));
		client.setPassword(request.getParameter("clientPassword"));
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
		province.setName(request.getParameter("clientProvince"));
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
			request.setAttribute("client", client);
			Helper.redirect("/WEB-INF/AdminViewClient.jsp", request, response);
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
		}
	}
}
