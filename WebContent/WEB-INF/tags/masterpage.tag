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
<c:set var="msgErrorList" value="${requestScope.errorList != null ? requestScope.errorList : emptyList}" />

<!DOCTYPE html>
<html data-theme="light" lang="es-AR">
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
        <!-- Notificación Toast (Lista de errores)-->
        <c:choose>
          <%-- Si hay atributo "errorList" se muestra la lista de errores --%>
          <c:when test="${not empty msgErrorList}">
              <div id="hiddenErrorList" class="hidden">
                <ul>
                  <c:forEach var="errorItem" items="${msgErrorList}">
                      <li>
                        <i data-lucide="arrow-right" class="inline"></i>
                        ${errorItem}
                      </li>
                  </c:forEach>
                </ul>
              </div>
              <script type="module">
              	import { startWindToast } from "${pageContext.request.contextPath}/js/wind-notify/index.js"
				const errorList = document.getElementById("hiddenErrorList").innerHTML;
        		startWindToast("Mensaje", errorList, "error", 15, "bottom-end");
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