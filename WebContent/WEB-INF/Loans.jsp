<%@page import="domainModel.Client"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="approvedLoans" value="${requestScope.approvedLoans != null ? 
                                requestScope.approvedLoans : emptyList }"/>
<c:set var="pendingLoans" value="${requestScope.pendingLoans != null ? 
                                requestScope.pendingLoans : emptyList}" />

<t:masterpage title="Mis Préstamos" customNavbar="true">
  <t:clientwrapper activeMenuItem="loansMenu">
    <div class="container flex flex-col gap-4 mx-auto p-4 max-w-7xl">
      <!-- Préstamos otorgados -->
      <div class="flex justify-between items-center">
        <h2 class="text-xl font-semibold text-gray-700">
          Mis préstamos
        </h2>
        <a href="?action=apply" class="btn btn-primary">
          Solicitar préstamo
        </a>
      </div>
      <div class="divide-y divide-gray-300 rounded-lg shadow p-6 bg-white">

        <!-- Préstamos otorgados -->
        <c:choose>
          <c:when test="${not empty approvedLoans}">
            <c:forEach var="loan" items="${approvedLoans}">
              <div class="flex justify-between items-center bg-white p-4">
                <div>
                  <h3 class="text-lg font-medium text-gray-800">${loan.loanType.name}</h3>
                  <p class="text-gray-600">Saldo otorgado: ${loan.requestedAmount}</p>
                  <p class="text-gray-600">Cuotas pendientes: NO IMPLEMENTADO</p>
                </div>
                <div class="flex flex-col items-end">
                  <p class="text-gray-600 font-medium">$ NO IMPLEMENTADO / mes</p>
                  <p class="text-gray-600">Vencimiento: NO IMPLEMENTADO</p>
                  <a href="PayLoan.jsp?id=AcaElIdDePrestamo" class="mt-2 text-blue-600 hover:underline flex items-center">
                    Pagar
                    <span class="ml-1">&#8594;</span> <!-- Flecha derecha -->
                  </a>
                </div>
              </div>
            </c:forEach>
          </c:when>
          <c:otherwise>
            <span>No tenés préstamos vigentes.</span>
          </c:otherwise>
        </c:choose>

      </div>

      <!-- Préstamos pendientes de aprobación -->      
      <h2 class="text-xl font-semibold text-gray-700">
        Pendientes de aprobación
      </h2>
      <div class="divide-y divide-gray-300 rounded-lg shadow p-6 bg-white">

        <c:choose>
          <c:when test="${not empty pendingLoans}">
            <c:forEach var="loan" items="${pendingLoans}">
              <div class="flex justify-between items-center bg-white p-4">
                <div>
                  <h3 class="text-lg font-medium text-gray-800">${loan.loanType.name}</h3>
                  <p class="text-gray-600">Monto solicitado: ${loan.requestedAmount}</p>
                  <p class="text-gray-600">Fecha de solicitud: NO IMPLEMENTADO</p>
                </div>
                <div class="text-right flex items-center">
                  <span class="px-4 py-2 bg-yellow-200 text-yellow-700 font-medium rounded-lg">
                    En revisión
                  </span>
                </div>
              </div>
            </c:forEach>
          </c:when>
          <c:otherwise>
            <span>No tenés préstamos pendientes de aprobación.</span>
          </c:otherwise>
        </c:choose>
      </div>
      
      <!-- Historial de prestamos (todos) -->
      <h2 class="text-xl font-semibold text-gray-700">
        Historial de préstamos
      </h2>
      <div class="flex flex-col bg-white p-2.5 rounded-xl drop-shadow-sm">
        <div class="flex justify-between p-2.5 mb-2">
          <label class="input input-sm input-bordered flex items-center gap-2">
            <input type="text" class="grow" placeholder="Buscar cuenta, monto" name="searchInput">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" data-lucide="search" class="lucide lucide-search"><circle cx="11" cy="11" r="8"></circle><path d="m21 21-4.3-4.3"></path></svg>
          </label>
          <div class="flex gap-2.5">
            <select class="select select-bordered select-sm w-fit bg-white">
              <option selected>Seleccione estado</option>
              <option>Finalizado</option>
              <option>Vigente</option>
              <option>En revisión</option>
              <option>Rechazado</option>
            </select>
            <select class="select select-bordered select-sm w-fit bg-white">
            <option selected>Seleccione tipo</option>
            <option>Personal</option>
            <option>Hipotecario</option>
            <option>Etc..</option>
          </select>
          </div>
        </div>
        <table class="table">
          <!-- head -->
          <thead>
            <tr>
              <th>ID</th>
              <th>Cuenta</th>
              <th>Tipo</th>
              <th>Estado</th>
              <th>Monto</th>
              <th>Cuotas</th>
            </tr>
          </thead>
          <tbody>
            <!-- row 1 -->
            <tr class="hover">
              <th>1</th>
              <td>1002</td>
              <td>Personal</td>
              <td>
                <span class="flex flex-col items-center w-fit px-2.5 rounded-full border border-green-600 text-green-600 font-semibold">
                  Finalizado
                </span>
              </td>
              <td>$ 100.000</td>
              <td>12</td>
            </tr>
            <!-- row 2 -->
            <tr class="hover">
              <th>2</th>
              <td>1002</td>
              <td>Hipotecario</td>
              <td>
                <span class="flex flex-col items-center w-fit px-2.5 rounded-full border border-blue-600 text-blue-600 font-semibold">
                  Vigente
                </span>
              </td>
              <td>$ 500.000</td>
              <td>48</td>
            </tr>
            <!-- row 3 -->
            <tr class="hover">
              <th>3</th>
              <td>1003</td>
              <td>Automotriz</td>
              <td>
                <span class="flex flex-col items-center w-fit px-2.5 rounded-full border border-yellow-500 text-yellow-500 font-semibold">
                  En Revisión
                </span>
              </td>
              <td>$ 1.000.000</td>
              <td>48</td>
            </tr>
            <!-- row 3 -->
            <tr class="hover">
              <th>4</th>
              <td>1003</td>
              <td>Automotriz</td>
              <td>
                <span class="flex flex-col items-center w-fit px-2.5 rounded-full border border-red-600 text-red-600 font-semibold">
                  Rechazado
                </span>
              </td>
              <td>$ 1.000.000</td>
              <td>48</td>
            </tr>
          </tbody>
        </table>
      </div>  
    </div>
  </t:clientwrapper>
</t:masterpage>