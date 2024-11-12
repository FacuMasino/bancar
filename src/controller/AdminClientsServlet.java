package controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

@WebServlet("/AdminClients")
public class AdminClientsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminClientsServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Si solo se accedió a esta ruta, se redirige al listado de clientes
		listClients(request, response);
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
			case "editClient":
				// TODO
				editClient(request, response);
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

	private void editClient(HttpServletRequest request, HttpServletResponse response) {
		// TODO Aca edito un cliente...Venimos de AdminEditClient.jsp

	}
	
	private void listClients(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		// TODO: Listar clientes
		
		// Lista de prueba
		ArrayList<Client> clientsList = new ArrayList<Client>();
		ArrayList<Account> auxAccounts;
		
		for(int i = 0; i <= 25; i++) {
			Client auxClient = new Client();
			auxAccounts = new ArrayList<Account>();
			if(i<12) auxAccounts.add(new Account()); // solo para tener distintos clientes
			auxClient.setId(i);
			auxClient.setFirstName("Cliente");
			auxClient.setLastName("de Prueba " + i);
			auxClient.setDni("3800000" + i);
			auxClient.setActive(i < 10);
			auxClient.setAccounts(auxAccounts);
			clientsList.add(auxClient);
		}
		
		request.setAttribute("clients", clientsList);
		RequestDispatcher rd = request.getRequestDispatcher("AdminClients.jsp");		
		rd.forward(request, response);
	}

}
