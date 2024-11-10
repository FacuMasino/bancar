package controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
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
import domainModel.User;
import exceptions.BusinessException;

@WebServlet("/AdminClientsServlet")
public class AdminClientsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminClientsServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		switch (action) {
		case "newClient":
			//TODO algunas validaciones de negocio, por ejemplo: verificar nombre de usuario disponible
			//TODO implementar BusinessException
			newClient(request, response);
			break;
		case "editClient":
			// TODO
			editClient(request, response);
			break;
		case "createAccount":
			// TODO
			createAccount(request, response);
			break;
		case "manageLoans":
			// TODO
			manageLoans(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
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

	private void createAccount(HttpServletRequest request, HttpServletResponse response) {
		// TODO: Aca creo una cuenta del cliente en cuestion, con validaciones de
		// negocio. Venimos de AdminClientAccounts.jsp

	}

	private void manageLoans(HttpServletRequest request, HttpServletResponse response) {
		// TODO Aca aceptamos o rechazamos prestamos. Venimos de AdminLoans.jsp

	}

}
