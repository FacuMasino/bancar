<%@page import="domainModel.Client"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="client" value="${requestScope.client}" />
<c:set var="listPage" value="${requestScope.page}" />

<t:masterpage title="Admin - Movimientos de cuenta" customNavbar="true">
  <t:adminwrapper activeMenuItem="">
    <form method="get" action=""
      class="container flex flex-col gap-4 mx-auto p-4 max-w-7xl mb-8">
      <input type="hidden" name="clientId" value="${client.clientId}" />
      <div class="container mx-auto p-4">
        <div class="mb-2">
          <h1 class="font-bold text-3xl">Movimientos de la cuenta N°
            ${account.id}</h1>
        </div>
        <div class="flex items-center justify-between mb-4">
          <h1 class="font-bold text-lg">Apellido y nombre: ${client.firstName}
            ${client.lastName} </h1>
        </div>
        <div
          class=" flex flex-col p-4 mb-4 border border border-gray-300 rounded-lg  gap-6 w-full bg-white">
          <div class="flex flex-col p-2 gap-4  w-full  ">
            <div class="flex flex-row text-lg">
             
                <span class="font-bold">${account.accountType.name}</span>
             
            </div>
            <div>
               
              <p class="font-bold text-2xl ${account.balance < 0 ? 'text-red-500' : 'text-green-500'}">$${account.balance}</p>
            </div>
          </div>
        </div>
        <div
          class=" flex flex-col p-4 border border border-gray-300 rounded-lg  gap-6 w-full bg-white">
          <div class="flex items-center justify-between w-full">
            <h2 class="font-bold text-xl">Últimos Movimientos</h2>
            <div class="flex items-center gap-2">
              <span>Movimientos por página: </span> <select
                class="select select-bordered w-fit bg-white" name="pageSize"
                onchange="this.form.submit()">
                <option value="10" ${listPage.pageSize == 10 ? 'selected':''}>
                  10</option>
                <option value="20" ${listPage.pageSize == 20 ? 'selected':''}>
                  20</option>
                <option value="30" ${listPage.pageSize == 30 ? 'selected':''}>
                  30</option>
              </select>
            </div>
          </div>
          <div>
            <div class="mb-4 ">
              <!--  <form method="get" action="AccountServlet"  class="flex justify-between p-2.5 mb-2"> -->
              <div class="flex justify-between p-2.5 mb-2">
                <label
                  class="input input-sm input-bordered flex items-center gap-2">
                  <input type="text" class="grow"
                  placeholder="Buscar cuenta, monto" name="searchInput">
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
                    viewBox="0 0 24 24" fill="none" stroke="currentColor"
                    stroke-width="2" stroke-linecap="round"
                    stroke-linejoin="round" data-lucide="search"
                    class="lucide lucide-search">
                    <circle cx="11" cy="11" r="8"></circle>
                    <path d="m21 21-4.3-4.3"></path></svg>
                </label>
                <div class="flex-end">
                  <input name="transactionDate" type="date"
                    class="border p-1 rounded border-gray-200">
                  <!--  falta la parte del servlet-->
                  <select name="movementTypeId"
                    class="select select-bordered select-sm w-fit bg-white">
                    <c:choose>
                      <c:when test="${empty movementTypes}">
                        <option disabled selected>Error: No hay tipos
                          de movimientos para mostrar</option>
                      </c:when>
                      <c:otherwise>
                        <option value="">Seleccione tipo de movimiento</option>
                        <c:forEach var="movementTpye" items="${movementTypes}">
                          <option value="${movementype.Id}">${movementype.name}
                          </option>
                        </c:forEach>
                      </c:otherwise>
                    </c:choose>
                  </select>
                </div>
              </div>
            </div>
            <div class="flex-rows gap-2.5 "></div>
    </form>
    <table class="table bg-white w-full">
      <thead>
        <tr>
          <th>Fecha</th>
          <th>Descripción</th>
          <th>Monto</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="movement" items="${movementsList}" varStatus="status">
          <tr class="hover">
            <td>${movement.movementDate}</td>
            <td>${movement.details}</td>
            <td class="text-green-600 font-semibold">${movement.amount}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    <div class="flex w-full items-center p-2.5">
      <span class="w-full"> Mostrando ${listPage.startElementPos + 1} a
        ${listPage.endElementPos} de ${listPage.totalElements} </span>
      <div class="join flex justify-end w-full">
        <c:forEach var="i" begin="1" end="${listPage.totalPages}">
          <button value="${i}" name="page" class="join-item btn">${i}</button>
        </c:forEach>
      </div>
    </div>
    </div>
    </div>
    </div>
    </form>
  </t:adminwrapper>
</t:masterpage>