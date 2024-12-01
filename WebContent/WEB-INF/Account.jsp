<%@page import="domainModel.Client"%>
<%@page import="domainModel.MovementTypeEnum"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Necesario para el formato de números como moneda -->
<fmt:setLocale value="es_AR"/>

<c:set var="client" value="${requestScope.client}" />
<c:set var="movements" value="${requestScope.movementsPage.content != null ? requestScope.movementsPage.content : emptyList}" />
<c:set var="page" value="${requestScope.movementsPage}" />
<c:set var="selectedAccount" value="${requestScope.selectedAccount}" />
<c:set var="movementTypes" value="${requestScope.movementTypes != null ? requestScope.movementTypes : emptyList}" />

<t:masterpage title="Mi Cuenta" customNavbar="true">
  <t:clientwrapper activeMenuItem="accountMenu">
    <form method="get" action="Client" class="container flex flex-col gap-4 mx-auto p-4 max-w-7xl mb-8">
      <h1 class="font-bold text-3xl mb-2">
        Bienvenido/a ${client.firstName} ${client.lastName}
      </h1>
      <div class="flex items-center justify-between mb-4">
        <h2 class="font-bold text-lg">Mi Cuenta</h2>
        <select name="selectedAccountId"
          class="bg-white w-64 font-bold font-sans select text-black select-bordered"
          onchange="this.form.submit()">
          <c:choose>
            <c:when test="${empty client.accounts}">
              <!-- Mostrar mensaje si accounts estï¿½ vacï¿½a -->
              <option disabled selected>
                No hay cuentas disponibles
              </option>
            </c:when>
            <c:otherwise>
              <c:forEach var="account" items="${client.accounts}"
                varStatus="status">
                <option value="${account.id}"
                  ${account.id == selectedAccount.id ? 'selected' : ''}>
                  Cuenta Nro: ${account.id}
                </option>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </select>
      </div>
      <div
        class="flex flex-col p-4 mb-4 border border border-gray-300 rounded-lg  gap-6 w-full bg-white">
        <div class="flex flex-col p-2 gap-3">
          <span class="font-semibold text-gray-600">
            ${selectedAccount.accountType.name} - Nro. ${selectedAccount.id}
          </span>
          <h2 class="font-bold text-2xl">
            Saldo:
            <fmt:formatNumber value="${selectedAccount.balance}" type="currency" />
          </h2>
        </div>
      </div>
      <div class="flex flex-col p-4 border border border-gray-300 rounded-lg  gap-6 w-full bg-white">
      
        <div class="flex justify-between">
          <h2 class="font-bold text-xl">Últimos Movimientos</h2>
          <!-- BUSCADOR -->
          <label class="input input-sm input-bordered flex items-center gap-2">
            <input type="text" placeholder="Monto, descripción" name="searchInput" value="${param.searchInput}">
            <button type="submit">
              <i data-lucide="search" class="w-[20px] h-[20px]"></i>
            </button>
          </label>
        </div>
        
        <div class="flex flex-col mb-4">
          <!-- FILTROS DE PÁGINA -->
          <div class="flex justify-between mb-2">
            <div>
              <span>Tamaño de página</span> 
              <select name="pageSize" onchange="this.form.submit()"
                class="select select-bordered select-sm w-fit bg-white">
                <c:forEach var="size" items="${page.pageSizes}">
                  <option ${param.pageSize == size ? 'selected':''}>
                    ${size}
                  </option>
                </c:forEach>
              </select>
            </div>
            <div class="flex gap-2.5">
              <div class="flex gap-2.5 items-center">
                <span>Fecha</span>
                <input name="transactionDate" type="date"
                  class="border p-1 rounded border-gray-200"
                  value="${param.transactionDate}" onchange="this.form.submit()">
              </div>
              <select name="movementTypeId"  class="select select-bordered select-sm w-fit bg-white" onchange="this.form.submit()">
                <c:choose>
                  <c:when test="${empty movementTypes}">
                    <option disabled selected>
                      Error: No hay tipos de movimientos para mostrar
                    </option>
                  </c:when>
                  <c:otherwise>
                    <option value="0" ${empty param.movementTypeId ? 'selected' : ''}>
                      Seleccione tipo de movimiento
                    </option>
                    <c:forEach var="movementType" items="${movementTypes}">
                        <option value="${movementType.id}" 
                          ${param.movementTypeId == movementType.id ? 'selected' : ''}>
                          ${movementType.name}
                        </option>
                    </c:forEach>
                  </c:otherwise>
                </c:choose>
              </select>
            </div>
          </div>
          <!-- TABLA -->
          <table class="table bg-white w-full">
            <thead>
              <tr>
                <th>Fecha</th>
                <th>Descripción</th>
                <th>Monto</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              <c:choose>
                <c:when test="${empty movements}">
                  <!-- Mostrar mensaje si no hay movimientos-->
                  <tr class="hover">
                    <td class="text-center" colspan="3">No hay movimientos disponibles</td>
                  </tr>
                </c:when>
                <c:otherwise>
                  <c:forEach var="movement" items="${movements}"
                    varStatus="status">
                    <tr class="hover">
                      <td>${movement.formattedDateTime}</td>
                      <td>${movement.details}</td>
                      <td>
                        <span 
                          class="font-semibold ${movement.amount < 0 ? 'text-red-600':'text-green-600'}">
                          <fmt:formatNumber value="${movement.amount}" type="currency" />
                        </span>
                      </td>
                      <td>
                        <c:set var="movType" value="${movement.movementType.id}" />
                        <c:choose>
                          <c:when
                            test="${movType != MovementTypeEnum.NEW_ACCOUNT.id and movType != MovementTypeEnum.NEW_LOAN.id}">
                            <a href="?movementId=${movement.id}&action=details">
                              <i data-lucide="eye"></i>
                            </a>
                          </c:when>
                          <c:otherwise>
                            -
                          </c:otherwise>
                        </c:choose>
                      </td>
                    </tr>
                  </c:forEach>
                </c:otherwise>
              </c:choose>
            </tbody>
          </table>
            
          <!-- BOTONES PAGINADO -->
          <div class="flex w-full items-center p-2.5">
           <span class="w-full">
            Mostrando 
            ${page.startElementPos} a ${page.endElementPos}
            de ${page.totalElements}
           </span>
          <div class="join flex justify-end w-full">
             <c:forEach var="i" begin="1" end="${page.totalPages}">
                <button value="${i}" name="page" class="join-item btn"
                  ${page.totalPages <= 1 ? 'disabled' : ''}>
                  ${i}
                </button>
            </c:forEach>
            </div>  
          </div>
            
        </div>
      </div>
    </form>
  </t:clientwrapper>
</t:masterpage>