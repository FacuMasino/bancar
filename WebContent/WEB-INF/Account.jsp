<%@page import="domainModel.Client"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="client" value="${requestScope.client}" />
<c:set var="movements" value="${requestScope.movements != null ? requestScope.movements : emptyList}" />

<t:masterpage title="Mi Cuenta" customNavbar="true">
  <t:clientwrapper activeMenuItem="accountMenu">
    <form method="get" action="Client"
      class="container flex flex-col gap-4 mx-auto p-4 max-w-7xl mb-8">
      <input type="hidden" name="clientId" value="${client.clientId}" />
      <div class="container mx-auto p-4">
        <div class="mb-2">
          <h1 class="font-bold text-3xl">Bienvenido/a</h1>
          <h1 class="font-bold text-3xl">${client.firstName }
            ${client.lastName }</h1>
        </div>
        <div class="flex items-center justify-between mb-4">
          <h2 class="font-bold text-lg">Mi Cuenta</h2>
          <input type="hidden" name="clientId"
            value="${client.clientId}" /> <select
            name="idSelectedAccount"
            class="bg-white w-64 font-bold font-sans select text-black select-bordered"
            onchange="this.form.submit()">
            <c:choose>
              <c:when test="${empty client.accounts}">
                <!-- Mostrar mensaje si accounts est� vac�a -->
                <option disabled selected>No hay cuentas
                  disponibles</option>
              </c:when>
              <c:otherwise>
                <c:forEach var="account" items="${client.accounts}"
                  varStatus="status">
                  <option value="${account.id}"
                    <c:if test="${account.id == idSelectedAccount}">selected</c:if>>
                    Cuenta Nro: ${account.id}</option>
                </c:forEach>
              </c:otherwise>
            </c:choose>
          </select>
        </div>
        <div
          class=" flex flex-col p-4 mb-4 border border border-gray-300 rounded-lg  gap-6 w-full bg-white">
          <div class="flex flex-col p-2 gap-4  w-full  ">
            <div class="flex flex-row text-lg">
              <p>Cuenta Nro: ${client.clientId}</p>
            </div>
            <div>
              <p class="font-bold text-2xl">Saldo:
                $ ${selectedAccountBalance}</p>
            </div>
          </div>
        </div>
        <div
          class=" flex flex-col p-4 border border border-gray-300 rounded-lg  gap-6 w-full bg-white">
          <div>
            <h2 class="font-bold text-xl">�ltimos Movimientos</h2>
          </div>
          <div>
            <div class="mb-4">
              <label
                class="input input-sm input-bordered flex items-center gap-2">
                <input type="text" class="grow"
                placeholder="Buscar cuenta, monto" name="searchInput">
                <svg xmlns="http://www.w3.org/2000/svg" width="16"
                  height="16" viewBox="0 0 24 24" fill="none"
                  stroke="currentColor" stroke-width="2"
                  stroke-linecap="round" stroke-linejoin="round"
                  data-lucide="search" class="lucide lucide-search">
                  <circle cx="11" cy="11" r="8"></circle>
                  <path d="m21 21-4.3-4.3"></path></svg>
              </label>
              <div class="flex gap-2.5">
                <input name="transactionDate" type="date"
                  class="border p-1 rounded border-gray-200"> <select
                  class="select select-bordered select-sm w-fit bg-white"
                  name="transactionType">
                  <option selected>Seleccione tipo</option>
                  <option>Transferencia</option>
                  <option>Pago</option>
                  <option>Cr�dito</option>
                </select>
              </div>
              <table class="table bg-white w-full">
                <thead>
                  <tr>
                    <th>Fecha</th>
                    <th>Descripci�n</th>
                    <th>Monto</th>
                  </tr>
                </thead>
                <tbody>
                  <c:choose>
                    <c:when test="${empty movements}">
                      <!-- Mostrar mensaje si no hay movimientos-->
                      <h3 class="font-bold text-3xl">No hay
                        movimientos disponibles</h3>
                    </c:when>
                    <c:otherwise>
                      <c:forEach var="movement" items="${movements}"
                        varStatus="status">
                        <tr class="hover">
                          <td>${movement.movementDate}</td>
                          <td>${movement.details}</td>
                          <td>${movement.amount}</td>
                        </tr>
                      </c:forEach>
                    </c:otherwise>
                  </c:choose>
                </tbody>
              </table>
            </div>
            <div class="flex justify-end">
              <div class="join">
                <button class="join-item btn">1</button>
                <button class="join-item btn btn-active">2</button>
                <button class="join-item btn">3</button>
                <button class="join-item btn">4</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </form>
  </t:clientwrapper>
</t:masterpage>