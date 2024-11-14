<%-- Esta sería la página base del proyecto
     De esta forma podemos importar en todas las demás
     los mismos css y scripts en el <head>
--%>
<%@tag description="Master page" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="title" required="true"%>
<%@attribute name="customNavbar" required="false" type="java.lang.Boolean" %>

<c:set var="msg" value="${requestScope.message != null ? requestScope.message : null}" />

<!DOCTYPE html>
<html data-theme="light">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	    <link href="https://cdn.jsdelivr.net/npm/daisyui@4.12.13/dist/full.min.css" 
	          rel="stylesheet" type="text/css" />
	    <script src="https://cdn.tailwindcss.com"></script>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css" type="text/css"/>
	    <style type="text/tailwindcss"><%@include file="/css/twstyles.css"%></style>
        <script src="https://cdn.jsdelivr.net/npm/lucide@latest/dist/umd/lucide.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/main.js"></script>
	    <title>BancAr - ${title}</title>
  	</head>
	<body class="flex flex-col min-h-screen bg-slate-100">
	  <%-- Se utiliza el Navbar por defecto excepto que se indique
	  	El atributo customNavbar=true, entonces la JSP page tendrá
	  	que usar un NavBar propio --%>
		<%
			Boolean showCustomNavbar = (customNavbar != null) ? customNavbar : Boolean.FALSE;
			if (!showCustomNavbar) { 
		%>
			<jsp:include page="/Components/Navbar.jsp" />
		<% 
			}
		%>
		<jsp:doBody />
        <!-- Notificación Toast -->
        <c:choose>
          <%-- Si hay atributo "message" se muestra la notificación --%>
          <c:when test="${not empty msg}">
            <c:set var="alertType" value="${msg.type == 'ERROR' ? 'error' : 'success'}" />
            <script type="module">
            	import { startWindToast } from "${pageContext.request.contextPath}/js/wind-notify/index.js"

				startWindToast("Mensaje", "${msg.message}", "${alertType}", 5, "bottom-end");
			</script>
          </c:when>
        </c:choose>
		<%--@include page="/Components/Footer.jsp" --%>
    <script>
      // Inicializa Lucide y reemplaza los <i data-lucide="...">
      lucide.createIcons();
    </script>
	</body>
</html>