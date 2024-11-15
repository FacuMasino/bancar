<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="error" value="${requestScope.error}" />
<c:set var="stackTrace" value="${requestScope.stackTrace}" />
<c:set var="statusCode" value="${requestScope.statusCode}" />
<!DOCTYPE HTML>
<html>
<head>
  <script src="https://cdn.tailwindcss.com"></script>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>BancAr - Ocurrió un error</title>
</head>
<body class="flex flex-col min-h-screen">
<section class="flex items-center min-h-screen sm:p-16 bg-gray-50 text-gray-800">
  <div class="container flex flex-col items-center justify-center px-5 mx-auto my-8 space-y-8 text-center max-w-2xl">
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" class="w-40 h-40 dark:text-gray-400">
      <path fill="currentColor" d="M256,16C123.452,16,16,123.452,16,256S123.452,496,256,496,496,388.548,496,256,388.548,16,256,16ZM403.078,403.078a207.253,207.253,0,1,1,44.589-66.125A207.332,207.332,0,0,1,403.078,403.078Z"></path>
      <rect width="176" height="32" x="168" y="320" fill="currentColor"></rect>
      <polygon fill="currentColor" points="210.63 228.042 186.588 206.671 207.958 182.63 184.042 161.37 162.671 185.412 138.63 164.042 117.37 187.958 141.412 209.329 120.042 233.37 143.958 254.63 165.329 230.588 189.37 251.958 210.63 228.042"></polygon>
      <polygon fill="currentColor" points="383.958 182.63 360.042 161.37 338.671 185.412 314.63 164.042 293.37 187.958 317.412 209.329 296.042 233.37 319.958 254.63 341.329 230.588 365.37 251.958 386.63 228.042 362.588 206.671 383.958 182.63"></polygon>
    </svg>
    <p class="text-3xl max-w-xs">Ups! Ocurrió un error inesperado - Error ${statusCode}</p>
    <p>${error}</p>
    <h2 class="font-bold text-xl text-start">Información del error:</h2>
    <p class="overflow-y-auto h-60 text-start w-full">
      ${stackTrace}
    </p>
    <a href="${pageContext.request.contextPath}" class="px-8 py-3 bg-red-600 text-white font-semibold rounded">Volver a la home</a>
  </div>
</section>
</body>
</html>