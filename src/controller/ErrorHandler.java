package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ErrorHandler
 */
@WebServlet("/ErrorHandler")
public class ErrorHandler extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErrorHandler()
    {
        super();
    }
    
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        processError(request, response);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        processError(request, response);
    }
    
    private void processError(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {
        Throwable throwable = (Throwable) request
                .getAttribute("javax.servlet.error.exception");

        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        
        String servletName = (String) request
                .getAttribute("javax.servlet.error.servlet_name");
        
        if (servletName == null)
        {
            servletName = "Desconocido";
        }
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        
        request.setAttribute("error", "Servlet " + servletName + 
          " arrojó una excepción <br>" + throwable.getClass().getName() +
          " : " + throwable.getMessage());

        request.setAttribute("statusCode", statusCode);
        request.setAttribute("stackTrace", sw.toString());
        request.getRequestDispatcher("/WEB-INF/ErrorPage.jsp").forward(request, response);
    }
}
