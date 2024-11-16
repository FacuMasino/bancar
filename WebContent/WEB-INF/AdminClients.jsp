<%@page import="domainModel.Client"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Variables JSTL --%>
<c:set var="clientsList" value="${requestScope.page.content != null ? requestScope.page.content : emptyList}" />
<c:set var="listPage" value="${requestScope.page}" />

<t:masterpage title="Admin - Clientes" customNavbar="true">
  <t:adminwrapper activeMenuItem="adminClientsMenu">
    
    <form method="get" action="Clients" class="container flex flex-col gap-4 mx-auto p-4 max-w-7xl mb-8">
      <div class="flex justify-between">
        <h1 class="font-bold text-xl">Gestión de Clientes</h1>
          <button name="action" value="new" class="btn btn-primary">
            Nuevo Cliente
          </button>
      </div>
      
      <!-- Tabla de clientes -->
      <div class="flex flex-col bg-white p-2.5 rounded-xl drop-shadow-sm">
        <div class="flex justify-between p-2.5">
          <div class="flex items-center gap-4">
            <span>Clientes por página: </span>
            <select class="select select-bordered w-fit bg-white"
              name="pageSize" onchange="this.form.submit()">
              <option value="10" ${listPage.pageSize == 10 ? 'selected':''}>
                10
              </option>
              <option value="20" ${listPage.pageSize == 20 ? 'selected':''}>
                20
              </option>
              <option value="30" ${listPage.pageSize == 30 ? 'selected':''}>
                30
              </option>
            </select>
          </div>
          <label class="input input-bordered flex items-center gap-2">
            <input type="text" class="grow" placeholder="Buscar cliente" name="searchInput" />
            <i data-lucide="search"></i>
          </label>
         </div>
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
                    <th>${client.clientId}</th>
                    <td>${client.dni}</td>
                    <td>${client.firstName} ${client.lastName}</td>
                    <td>
                      <c:set var="statusClass" value="${client.activeStatus ? 'border-green-600 text-green-600' : 'border-red-600 text-red-600'}" />
                      <c:set var="statusText" value="${client.activeStatus ? 'Activo' : 'Baja'}" />
                      <span class="flex flex-col items-center w-fit px-2.5 rounded-full border ${statusClass} font-semibold">
                        ${statusText}
                      </span>
                    </td>
                    <c:set var="accounts" value="${client.accounts.size() != 0 ? client.accounts.size() : 'No posee' }" />
                    <td>${accounts}</td>
                    <td class="flex items-center">
                      <div class="dropdown dropdown-end">
                        <div tabindex="0" role="button" class="p-0.5">
                          <i class="text-sm" data-lucide="ellipsis-vertical"></i>
                        </div>
                        <ul tabindex="0" class="dropdown-content menu bg-white rounded-box z-[1] w-52 p-2 border-slate-200 drop-shadow">
                          <li><a href="Clients?clientId=${client.clientId}&action=view">Ver cliente</a></li>
                          <li><a href="Clients?clientId=${client.clientId}&action=edit">Editar cliente</a></li>
                          <li><a href="Accounts?clientId=${client.clientId}">Gestionar cuentas</a></li>
                        </ul>
                      </div>
                    </td>
                  </tr>
                </c:forEach>
              </c:otherwise>
            </c:choose>
          </tbody>
        </table>
        <div class="flex w-full items-center p-2.5">
          <span class="w-full">
            Mostrando 
            ${listPage.startElementPos + 1} a ${listPage.endElementPos}
            de ${listPage.totalElements}
          </span>
          <div class="join flex justify-end w-full">
             <c:forEach var="i" begin="1" end="${listPage.totalPages}">
                <button value="${i}" name="page" class="join-item btn">${i}</button>
            </c:forEach>
          </div>
        </div>
      </div>
    </form>
  </t:adminwrapper>
</t:masterpage>