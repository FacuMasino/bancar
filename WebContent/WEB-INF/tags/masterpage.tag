<!-- Esta sería la página base del proyecto
     De esta forma podemos importar en todas las demás
     los mismos css y scripts en el <head>
-->
<%@tag description="Master page" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="title" required="true"%>
<%@attribute name="customNavbar" required="false" type="java.lang.Boolean" %>
<!DOCTYPE html>
<html data-theme="light">
	<head>
	    <link href="https://cdn.jsdelivr.net/npm/daisyui@4.12.13/dist/full.min.css" 
	          rel="stylesheet" type="text/css" />
	    <script src="https://cdn.tailwindcss.com"></script>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css" type="text/css"/>
	    <style type="text/tailwindcss"><%@include file="/css/twstyles.css"%></style>
        <script src="https://cdn.jsdelivr.net/npm/lucide@latest/dist/umd/lucide.min.js"></script>
	    <title>TP Integrador Lab4 - ${title}</title>
  	</head>
	<body class="flex flex-col min-h-screen bg-slate-100">
	  <%-- Se utiliza el Navbar por defecto excepto que se indique
	  	El atributo customNavbar=true, entonces la JSP page tendrá
	  	que usar un NavBar propio --%>
		<%
			Boolean showCustomNavbar = (customNavbar != null) ? customNavbar : Boolean.FALSE;
			if (!showCustomNavbar) { 
		%>
			<jsp:include page="/Componentes/Navbar.jsp" />
		<% 
			}
		%>
		<jsp:doBody />
		<%--@include page="/Componentes/Footer.jsp" --%>
    <script>
      // Inicializa Lucide y reemplaza los <i data-lucide="...">
      lucide.createIcons();
    </script>
	</body>
</html>