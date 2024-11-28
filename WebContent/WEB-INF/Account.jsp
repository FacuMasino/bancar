<%@page import="domainModel.Client"%>
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
    <div class="container flex flex-col gap-4 mx-auto p-4 max-w-7xl mb-8">
      <div class="container mx-auto p-4">
        <div class="mb-2">
          <h1 class="font-bold text-3xl">
            Bienvenido/a ${client.firstName} ${client.lastName}
          </h1>
        </div>
        <form method="get" action="Client" class="flex items-center justify-between mb-4">
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
                    <c:if test="${account.id == selectedAccount.id}">selected</c:if>>
                    Cuenta Nro: ${account.id}</option>
                </c:forEach>
              </c:otherwise>
            </c:choose>
          </select>
        </form>
        <div
          class=" flex flex-col p-4 mb-4 border border border-gray-300 rounded-lg  gap-6 w-full bg-white">
          <div class="flex flex-col p-2 gap-3">
            <span class="font-semibold text-gray-600">${selectedAccount.accountType.name} - Nro. ${selectedAccount.id}</span>
            <h2 class="font-bold text-2xl">
              Saldo:
              <fmt:formatNumber value="${selectedAccount.balance}" type="currency" />
            </h2>
          </div>
        </div>
       <form method="get" action="Client">
        <div class=" flex flex-col p-4 border border border-gray-300 rounded-lg  gap-6 w-full bg-white ">
          <div class="flex justify-between">
            <h2 class="font-bold text-xl">Últimos Movimientos</h2>
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
          </div>
          <div class="flex flex-col mb-4">
              <div class="flex justify-between">
           <input type="hidden" name="selectedAccountId" value="${selectedAccount.id}" />
                <label class="input input-sm input-bordered flex items-center gap-2">
              <input type="text" placeholder="Buscar monto o descripción" name="searchInput" value="${param.searchInput}">
              <i data-lucide="search" class="w-[20px] h-[20px]"></i>
            </label>
                <div class="flex gap-2.5">
                  <input name="transactionDate" type="date" class="border p-1 rounded border-gray-200" value="${param.transactionDate}" onchange="this.form.submit()"> 
                  <select name="movementTypeId"  class="select select-bordered select-sm w-fit bg-white" onchange="this.form.submit()">
              <c:choose>
                     <c:when test="${empty movementTypes}">
                  <option disabled selected>Error: No hay tipos
                    de movimientos para mostrar</option>
                </c:when>
                <c:otherwise>
                 <option value="" ${empty param.movementTypeId ? 'selected' : ''}>
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
               </form>
              </div>
              <table class="table bg-white w-full">
                <thead>
                  <tr>
                    <th>Fecha</th>
                    <th>Descripción</th>
                    <th>Monto</th>
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
                        </tr>
                      </c:forEach>
                    </c:otherwise>
                  </c:choose>
                </tbody>
              </table>
            </div>
            <div class="flex w-full items-center p-2.5">
             <span class="w-full">
              Mostrando 
              ${page.startElementPos + 1} a ${page.endElementPos}
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
    </div>
  </t:clientwrapper>
</t:masterpage>