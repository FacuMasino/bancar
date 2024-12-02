package controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domainModel.Message.MessageType;
import utils.Helper;
import domainModel.User;

import java.io.IOException;

/*
 * Filtro global utilizado para comprobar rutas
 * donde se requiera la sesión inicada de un cliente
 */

@WebFilter("/Client/*") // Rutas a las que aplica el filtro
public class ClientFilter implements Filter 
{
    public void init(FilterConfig filterConfig) 
    		throws ServletException 
    {
        // Inicialización del filtro
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
    		throws IOException, ServletException 
    {
        System.out.println("Filtro cliente accionado.");
      
        // Castea la solicitud a un tipo de solicitud HTTP para poder obtener la session
        HttpServletRequest req = (HttpServletRequest)request;
        // getSession(false) devuelve null si no hay una session creada
		HttpSession session = req.getSession(false);
		
		// Si hay obj. session, se asigna el atributo user (si no existe será null)
		User user = session != null ? 
				(User)session.getAttribute("user") : null;
		// Si hay un user guardado en session se obtiene el Rol, si es Rol Cliente entonces se asigna true
		Boolean hasClientUser = user != null ? 
				user.getRole().getName().equals("Cliente") : false;
		
		if(session == null || !hasClientUser) {
			Helper.setReqMessage(req,"Debes iniciar sesión como cliente para acceder a esta sección",MessageType.ERROR);
			Helper.redirect("/Login", req, (HttpServletResponse)response);
			return; // salir de el filtro
		}
		// Si pasa la validación, se deja acceder a la ruta /Client
        chain.doFilter(request, response); // Continúa con el siguiente filtro o servlet
    }

    public void destroy() 
    {
        // Limpieza de recursos
    }
}