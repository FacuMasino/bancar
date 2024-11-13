package controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import businessLogicImpl.ClientsBusiness;
import domainModel.Account;
import domainModel.Address;
import domainModel.City;
import domainModel.Client;
import domainModel.Country;
import domainModel.Province;
import domainModel.User;
import exceptions.BusinessException;
import utils.Page;

@WebServlet("/AdminClients")
public class AdminClientsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClientsBusiness clientsBiz;
	
	public AdminClientsServlet() {
		super();
		clientsBiz= new ClientsBusiness();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String action = request.getParameter("action");
		if(action == null) {
			// Si no se especifica accion, solo mostrar listado
			listClients(request, response);
			return;
		}

		switch (action) 
		{
			case "view":
				viewClient(request, response);
				break;
			case "edit":
				editClient(request,response);
			default:
				listClients(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		switch (action) 
		{
			case "newClient":
				//TODO algunas validaciones de negocio, por ejemplo: verificar nombre de usuario disponible
				//TODO implementar BusinessException
				newClient(request, response);
				break;
			case "saveClient":
				// TODO
				saveClient(request, response);
				break;
			default:
				listClients(request, response);
		}
	}

	private void newClient(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ClientsBusiness cb = new ClientsBusiness();
		Client auxClient = new Client();

		auxClient.setFirstName(request.getParameter("clientName"));
		auxClient.setLastName(request.getParameter("clientSurname"));
		auxClient.setSex(request.getParameter("clientSex"));
		auxClient.setBirthDate(Date.valueOf(request.getParameter("clientBirthdate")));
		auxClient.setEmail(request.getParameter("clientEmail"));

		User u = new User();
		u.setUsername(request.getParameter("clientUser"));
		u.setPassword(request.getParameter("clientPass"));
		auxClient.setUser(u);

		auxClient.setDni(request.getParameter("clientDNI"));
		auxClient.setCuil(request.getParameter("clientCuil"));

		// Cargamos toda la direccion del cliente
		Address address = new Address();
		address.setId(1);
		address.setStreetName(request.getParameter("clientStreetName"));
		address.setStreetNumber(request.getParameter("clientStreetNumber"));

		Province province = new Province();
		province.setName(request.getParameter("clientProvince"));
		address.setProvince(province);

		City city = new City();
		city.setName(request.getParameter("clientCity"));
		address.setCity(city);

		Country country = new Country();
		country.setId(1);
		country.setName("Argentina");
		address.setCountry(country);

		auxClient.setNationality(country);
		auxClient.setAddress(address);

		auxClient.setPhone(request.getParameter("clientPhone"));

		try {
			cb.create(auxClient);
			System.out.println("Cree un cliente");
		} catch (BusinessException e) {
			e.printStackTrace();
		}

		// TODO: redirigir a pagina que corresponda o modal (CLIENTE AGREGADO
		// EXITOSAMENTE?)
		RequestDispatcher rd = request.getRequestDispatcher("Home.jsp");
		rd.forward(request, response);
	}

	private void editClient(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException
	{
		int id = Optional.ofNullable(request.getParameter("clientId"))
				.map(Integer::parseInt)
				.orElse(0);
		
		// TODO: Cambiar por metodo read cuando esté listo el negocio de clientes
		//Client auxClient = clientsBiz.read(id);
		Client auxClient = clientsListMock().get(id);

		request.setAttribute("client", auxClient);
		RequestDispatcher rd = request.getRequestDispatcher("AdminEditClient.jsp");		
		rd.forward(request, response);
	}
	
	private void saveClient(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException
	{
		
		Client auxClient = new Client();
		
		// TODO: Mapear parametros, actualizar el cliente, devolver mensaje de estado
	}
	
	private void listClients(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		int page = Optional.ofNullable(request.getParameter("page"))
				.map(Integer::parseInt)
				.orElse(1);
		int pageSize = Optional.ofNullable(request.getParameter("pageSize"))
				.map(Integer::parseInt)
				.orElse(10);
		
		// TODO: cambiar por el método lista() cuando esté listo el negocio
		//ArrayList<Client> clientsList = clientsBiz.list();
		ArrayList<Client> clientsList = clientsListMock();
		Page<Client> clientsPage = new Page<Client>(page,pageSize, clientsList);

		request.setAttribute("page", clientsPage);
		RequestDispatcher rd = request.getRequestDispatcher("AdminClients.jsp");		
		rd.forward(request, response);
	}
	
	private void viewClient(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		int id = Optional.ofNullable(request.getParameter("clientId"))
				.map(Integer::parseInt)
				.orElse(0);
		
		// TODO: Cambiar por metodo read cuando esté listo el negocio de clientes
		//Client auxClient = clientsBiz.read(id);
		Client auxClient = clientsListMock().get(id);

		request.setAttribute("client", auxClient);
		RequestDispatcher rd = request.getRequestDispatcher("AdminViewClient.jsp");		
		rd.forward(request, response);
	}
	
	// TODO: ELIMINAR esto cuando funciones ClientsBusiness.list();
	private ArrayList<Client> clientsListMock() {
		// Lista de prueba
		ArrayList<Client> clientsList = new ArrayList<Client>();
		ArrayList<Account> auxAccounts;
		
		for(int i = 0; i <= 25; i++) {
			Client auxClient = new Client();
			auxAccounts = new ArrayList<Account>();
			if(i<12) auxAccounts.add(new Account()); // Hasta el 11 tienen 1 cuenta
			auxClient.setClientId(i);
			auxClient.setFirstName("Cliente");
			auxClient.setLastName("de Prueba " + i);
			auxClient.setDni("3800000" + i);
			auxClient.setActiveStatus(i < 10);
			auxClient.setAccounts(auxAccounts);
			clientsList.add(auxClient);
		}
		
		return clientsList;
	}
}
