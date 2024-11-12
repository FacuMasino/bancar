<%@page import="domainModel.Client"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- Variables JSTL --%>
<c:set var="clientsList" value="${requestScope.clients != null ? requestScope.clients : emptyList}" />

<t:masterpage title="Admin - Clientes" customNavbar="true">
  <t:adminwrapper activeMenuItem="adminClientsMenu">
    <!-- Enlace a CSS y scripts DataTable.js -->
    <link rel="stylesheet" href="https://cdn.datatables.net/2.1.8/css/dataTables.dataTables.css" />
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"
      integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
      crossorigin="anonymous">
    </script>
    <script type="text/javascript" charset="utf8" 
      src="https:////cdn.datatables.net/2.1.8/js/dataTables.min.js">
    </script>
    
    <form method="get" action="#" class="container flex flex-col gap-4 mx-auto p-4 max-w-7xl mb-8">
      <div class="flex justify-between">
        <h1 class="font-bold text-xl">Gestión de Clientes</h1>
        <div class="flex gap-2.5">
          <label class="input input-bordered flex items-center gap-2">
            <input type="text" class="grow" placeholder="Buscar cliente" name="searchInput" />
            <i data-lucide="search"></i>
          </label>
          <a href="AdminNewClient.jsp" class="btn btn-primary">
            Nuevo Cliente
          </a>
        </div>
      </div>
      
      <!-- Tabla de clientes -->
      <div class="flex flex-col bg-white p-2.5 rounded-xl drop-shadow-sm">
        <table id="clientsList" class="table">
          <!-- head -->
          <thead>
            <tr>
              <th>ID</th>
              <th>DNI</th>
              <th>Nombre y Apellido</th>
              <th>Estado</th>
              <th>Cuentas</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            <%-- If en JSTL --%>
            <c:choose>
              <c:when test="${empty clientsList}">
                <!-- Mostrar mensaje si no hay clientes -->
                <tr>
                  <td colspan="6" class="text-center">No hay clientes para mostrar</td>
                </tr>
              </c:when>
              <c:otherwise>
                <c:forEach var="client" items="${clientsList}">
                  <tr class="hover">
                    <th>${client.id}</th>
                    <td>${client.dni}</td>
                    <td>${client.firstName} ${client.lastName}</td>
                    <td>
                      <c:set var="statusClass" value="${client.active ? 'border-green-600 text-green-600' : 'border-red-600 text-red-600'}" />
                      <c:set var="statusText" value="${client.active ? 'Activo' : 'Baja'}" />
                      <span class="flex flex-col items-center w-fit px-2.5 rounded-full border ${statusClass} font-semibold">
                        ${statusText}
                      </span>
                    </td>
                    <td>${client.accounts.size()}</td>
                    <td class="flex items-center">
                      <div class="dropdown dropdown-end">
                        <div tabindex="0" role="button" class="p-0.5">
                          <i class="text-sm" data-lucide="ellipsis-vertical"></i>
                        </div>
                        <ul tabindex="0" class="dropdown-content menu bg-white rounded-box z-[1] w-52 p-2 border-slate-200 drop-shadow">
                          <li><a href="AdminClient.jsp?id=${client.id}">Ver cliente</a></li>
                          <li><a href="AdminEditClient.jsp?id=${client.id}">Editar cliente</a></li>
                          <li><a href="AdminClientAccounts.jsp?id=${client.id}">Gestionar cuentas</a></li>
                        </ul>
                      </div>
                    </td>
                  </tr>
                </c:forEach>
              </c:otherwise>
            </c:choose>
          </tbody>
        </table>
      </div>
    </form>
    <script>
        new DataTable('#clientsList', {
            responsive: true,
            searching: false,
            language: {
              decimal: "",
              emptyTable: "No hay información",
              info: "Mostrando _START_ a _END_ de _TOTAL_ Entradas",
              infoEmpty: "Mostrando 0 to 0 of 0 Entradas",
              infoFiltered: "(Filtrado de _MAX_ total entradas)",
              infoPostFix: "",
              thousands: ",",
              lengthMenu: "Mostrar _MENU_ Entradas",
              loadingRecords: "Cargando...",
              processing: "Procesando...",
              search: "Buscar:",
              zeroRecords: "Sin resultados encontrados",
                paginate: {
                    previous: '&lt;',
                    next: '&gt;'
                }
            }
        });
    </script>
  </t:adminwrapper>
</t:masterpage>