<%@page import="domainModel.Client"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="client" value="${requestScope.client}" />
<c:set var="movements" value="${requestScope.movementsPage.content != null ? requestScope.movementsPage.content : emptyList}" />
<c:set var="page" value="${requestScope.movementsPage}" />
<c:set var="selectedAccount" value="${requestScope.selectedAccount}" />

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
                <!-- Mostrar mensaje si accounts está vacía -->
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
              Saldo: $ ${selectedAccount.balance}
            </h2>
          </div>
        </div>
        <div
          class=" flex flex-col p-4 border border border-gray-300 rounded-lg  gap-6 w-full bg-white">
          <div class="flex justify-between">
            <h2 class="font-bold text-xl">Últimos Movimientos</h2>
            <label class="input input-sm input-bordered flex items-center gap-2">
              <input type="text" class="grow"
              placeholder="Buscar cuenta, monto" name="searchInput">
              <i data-lucide="search" class="w-[20px] h-[20px]"></i>
            </label>
          </div>
          <form method="get" action="Client">
            <div class="flex flex-col mb-4">
              <div class="flex justify-between">
                <div class="flex gap-2.5 items-center">
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
                  <input name="transactionDate" type="date"
                    class="border p-1 rounded border-gray-200"> <select
                    class="select select-bordered select-sm w-fit bg-white"
                    name="transactionType">
                    <option selected>Seleccione tipo</option>
                    <option>Transferencia</option>
                    <option>Pago</option>
                    <option>Crédito</option>
                  </select>
                </div>
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
                      <h3 class="font-bold text-3xl">No hay
                        movimientos disponibles</h3>
                    </c:when>
                    <c:otherwise>
                      <c:forEach var="movement" items="${movements}"
                        varStatus="status">
                        <tr class="hover">
                          <td>${movement.movementDate}</td>
                          <td>${movement.details}</td>
                          <td>
                            <span 
                              class="font-semibold ${movement.amount < 0 ? 'text-red-600':'text-green-600'}">
                              $ ${movement.amount}
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
                  <button value="${i}" name="page" class="join-item btn">${i}</button>
              </c:forEach>
              </div>  
            </div>
          </form>
        </div>
      </div>
    </div>
  </t:clientwrapper>
</t:masterpage>