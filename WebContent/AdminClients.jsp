<%@page import="domainModel.Client"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="bodyContent">
  <%
  ArrayList<Client> clientsList = new ArrayList<Client>();
  if (request.getAttribute("clients") != null) {
    clientsList = (ArrayList<Client>) request.getAttribute("clients");
  }
  %>
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
          <%
            for (Client c : clientsList) {
          %>
          <!-- row 1 -->
          <tr class="hover">
            <th><%=c.getId()%></th>
            <td><%=c.getDni()%></td>
            <td><%=c.getFirstName() + " " + c.getLastName()%></td>
            <td>
              <span class="flex flex-col items-center w-fit px-2.5 rounded-full border 
                <%=c.isActive() ? "border-green-600 text-green-600" : "border-red-600 text-red-600"%> font-semibold">
                <%=c.isActive() ? "Activo":"Baja" %>
              </span>
            </td>
            <td><%=c.getAccounts().size()%></td>
            <td class="flex items-center">
              <div class="dropdown dropdown-end">
                <div tabindex="0" role="button" class="p-0.5">
                  <i class="text-sm" data-lucide="ellipsis-vertical"></i>
                </div>
                <ul tabindex="0" class="dropdown-content menu bg-white rounded-box z-[1] w-52 p-2 border-slate-200 drop-shadow">
                  <li><a href="AdminClient.jsp?id=<%=c.getId()%>">Ver cliente</a></li>
                  <li><a href="AdminEditClient.jsp?id=<%=c.getId()%>">Editar cliente</a></li>
                  <li><a href="AdminClientAccounts.jsp?id=<%=c.getId()%>">Gestionar cuentas</a></li>
                </ul>
              </div>
            </td>
          </tr>
          <%
          }
          %>
          <!-- row 2 -->
          <tr class="hover">
            <th>2</th>
            <td>36000000</td>
            <td>Bertello Ana Clara</td>
            <td>
              <span class="flex flex-col items-center w-fit px-2.5 rounded-full border border-green-600 text-green-600 font-semibold">
                Activo
              </span>
            </td>
            <td>3</td>
            <td class="flex items-center">
              <div class="dropdown dropdown-end">
                <div tabindex="0" role="button" class="p-0.5">
                  <i class="text-sm" data-lucide="ellipsis-vertical"></i>
                </div>
                <ul tabindex="0" class="dropdown-content menu bg-white rounded-box z-[1] w-52 p-2 border-slate-200 drop-shadow">
                  <li><a href="AdminClient.jsp?id=2">Ver cliente</a></li>
                  <li><a href="AdminEditClient.jsp?id=2">Editar cliente</a></li>
                  <li><a href="AdminClientAccounts.jsp?id=2">Gestionar cuentas</a></li>
                </ul>
              </div>
            </td>
          </tr>
          <!-- row 3 -->
          <tr class="hover">
            <th>3</th>
            <td>39000000</td>
            <td>Malvicino Maximiliano</td>
            <td>
              <span class="flex flex-col items-center w-fit px-2.5 rounded-full border border-red-600 text-red-600 font-semibold">
                Baja
              </span>
            </td>
            <td>1</td>
            <td class="flex items-center">
              <div class="dropdown dropdown-end">
                <div tabindex="0" role="button" class="p-0.5">
                  <i class="text-sm" data-lucide="ellipsis-vertical"></i>
                </div>
                <ul tabindex="0" class="dropdown-content menu bg-white rounded-box z-[1] w-52 p-2 border-slate-200 drop-shadow">
                  <li><a href="AdminClient.jsp?id=3">Ver cliente</a></li>
                  <li><a href="AdminEditClient.jsp?id=3">Editar cliente</a></li>
                  <li><a href="AdminClientAccounts.jsp?id=3">Gestionar cuentas</a></li>
                </ul>
              </div>
            </td>
          </tr>
          <!-- row 4 -->
          <tr class="hover">
            <th>4</th>
            <td>39000000</td>
            <td>Masino Facundo</td>
            <td>
              <span class="flex flex-col items-center w-fit px-2.5 rounded-full border border-green-600 text-green-600 font-semibold">
                Activo
              </span>
            </td>
            <td>2</td>
            <td class="flex items-center">
              <div class="dropdown dropdown-end">
                <div tabindex="0" role="button" class="p-0.5">
                  <i class="text-sm" data-lucide="ellipsis-vertical"></i>
                </div>
                <ul tabindex="0" class="dropdown-content menu bg-white rounded-box z-[1] w-52 p-2 border-slate-200 drop-shadow">
                  <li><a href="AdminClient.jsp?id=4">Ver cliente</a></li>
                  <li><a href="AdminEditClient.jsp?id=4">Editar cliente</a></li>
                  <li><a href="AdminClientAccounts.jsp?id=4">Gestionar cuentas</a></li>
                </ul>
              </div>
            </td>
          </tr>
          <!-- row 4 -->
          <tr class="hover">
            <th>5</th>
            <td>40000000</td>
            <td>Romero Juan</td>
            <td>
              <span class="flex flex-col items-center w-fit px-2.5 rounded-full border border-green-600 text-green-600 font-semibold">
                Activo
              </span>
            </td>
            <td>0</td>
            <td class="flex items-center">
              <div class="dropdown dropdown-end">
                <div tabindex="0" role="button" class="p-0.5">
                  <i class="text-sm" data-lucide="ellipsis-vertical"></i>
                </div>
                <ul tabindex="0" class="dropdown-content menu bg-white rounded-box z-[1] w-52 p-2 border-slate-200 drop-shadow">
                  <li><a href="AdminClient.jsp?id=5">Ver cliente</a></li>
                  <li><a href="AdminEditClient.jsp?id=5">Editar cliente</a></li>
                  <li><a href="AdminClientAccounts.jsp?id=5">Gestionar cuentas</a></li>
                </ul>
              </div>
            </td>
          </tr>
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
</c:set>

<t:masterpage title="Admin - Clientes" customNavbar="true">
  <t:adminwrapper activeMenuItem="adminClientsMenu">
  
    <jsp:body>
        ${bodyContent}
    </jsp:body>
  </t:adminwrapper>
</t:masterpage>