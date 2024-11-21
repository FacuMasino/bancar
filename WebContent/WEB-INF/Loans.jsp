<%@page import="domainModel.Client"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="approvedLoans" value="${requestScope.approvedLoans != null ? 
                                requestScope.approvedLoans : emptyList }"/>
<c:set var="pendingLoans" value="${requestScope.pendingLoans != null ? 
                                requestScope.pendingLoans : emptyList}" />

<t:masterpage title="Mis Pr�stamos" customNavbar="true">
  <t:clientwrapper activeMenuItem="loansMenu">
    <div class="container flex flex-col gap-4 mx-auto p-4 max-w-7xl">
      <!-- Pr�stamos otorgados -->
      <div class="flex justify-between items-center">
        <h2 class="text-xl font-semibold text-gray-700">
          Mis pr�stamos
        </h2>
        <a href="?action=apply" class="btn btn-primary">
          Solicitar pr�stamo
        </a>
      </div>
      <div class="divide-y divide-gray-300 rounded-lg shadow p-6 bg-white">

        <!-- Pr�stamos otorgados -->
        <c:choose>
          <c:when test="${not empty approvedLoans}">
            <c:forEach var="loan" items="${approvedLoans}">
              <div class="flex justify-between items-center bg-white p-4">
                <div>
                  <h3 class="text-lg font-medium text-gray-800">${loan.loanType.name}</h3>
                  <p class="text-gray-600">Saldo otorgado: $ ${loan.requestedAmount}</p>
                  <p class="text-gray-600">Cuotas pendientes: ${loan.pendingInstallments.size()}</p>
                </div>
                <div class="flex flex-col items-end">
                  <p class="text-gray-600 font-medium">$ ${loan.requestedAmount * 1.00 / loan.installmentsQuantity} / mes</p>
                  <p class="text-gray-600">
                    Vencimiento:
                    <fmt:formatDate type="date" dateStyle="short" timeStyle="short" value="${now}" />
                  </p>
                  <a href="PayLoan.jsp?id=AcaElIdDePrestamo" class="mt-2 text-blue-600 hover:underline flex items-center">
                    Pagar
                    <span class="ml-1">&#8594;</span> <!-- Flecha derecha -->
                  </a>
                </div>
              </div>
            </c:forEach>
          </c:when>
          <c:otherwise>
            <span>No ten�s pr�stamos vigentes.</span>
          </c:otherwise>
        </c:choose>

      </div>

      <!-- Pr�stamos pendientes de aprobaci�n -->      
      <h2 class="text-xl font-semibold text-gray-700">
        Pendientes de aprobaci�n
      </h2>
      <div class="divide-y divide-gray-300 rounded-lg shadow p-6 bg-white">

        <c:choose>
          <c:when test="${not empty pendingLoans}">
            <c:forEach var="loan" items="${pendingLoans}">
              <div class="flex justify-between items-center bg-white p-4">
                <div>
                  <h3 class="text-lg font-medium text-gray-800">${loan.loanType.name}</h3>
                  <p class="text-gray-600">Monto solicitado: $ ${loan.requestedAmount}</p>
                  <p class="text-gray-600">Fecha de solicitud: NO IMPLEMENTADO</p>
                </div>
                <div class="text-right flex items-center">
                  <span class="px-4 py-2 bg-yellow-200 text-yellow-700 font-medium rounded-lg">
                    En revisi�n
                  </span>
                </div>
              </div>
            </c:forEach>
          </c:when>
          <c:otherwise>
            <span>No ten�s pr�stamos pendientes de aprobaci�n.</span>
          </c:otherwise>
        </c:choose>
      </div>
      
      <!-- Historial de prestamos (todos) -->
      <h2 class="text-xl font-semibold text-gray-700">
        Historial de pr�stamos
      </h2>
      <div class="flex flex-col bg-white p-2.5 rounded-xl drop-shadow-sm">        
        <c:choose>
          <c:when test="${not empty client.loans}">
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
                  <option>En revisi�n</option>
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
                <c:forEach var="loan" items="${client.loans}">
                  <tr class="hover">
                    <th>${loan.loanId}</th>
                    <td>${loan.accountId}</td>
                    <td>${loan.loanType.name}</td>
                    <td>
                      <c:choose>
                        <c:when test="${loan.loanStatus.id eq 1}">
                          <span class="flex flex-col items-center w-fit px-2.5 rounded-full border border-yellow-600 text-yellow-700 font-semibold">
                            En Revisi�n
                          </span>
                        </c:when>
                        <c:when test="${loan.loanStatus.id eq 2}">
                          <span class="flex flex-col items-center w-fit px-2.5 rounded-full border border-blue-600 text-blue-600 font-semibold">
                            Vigente
                          </span>                          
                        </c:when>
                        <c:when test="${loan.loanStatus.id eq 2}">
                          <span class="flex flex-col items-center w-fit px-2.5 rounded-full border border-green-600 text-green-600 font-semibold">
                            Finalizado
                          </span>                        
                        </c:when>
                        <c:otherwise>
                          <span class="flex flex-col items-center w-fit px-2.5 rounded-full border border-red-600 text-red-600 font-semibold">
                            Rechazado
                          </span>
                        </c:otherwise>
                      </c:choose>
                    </td>
                    <td>$ ${loan.requestedAmount}</td>
                    <td>${loan.installmentsQuantity}</td>
                  </tr>
                </c:forEach>
               </tbody>
            </table>
          </c:when>
          <c:otherwise>
            <span class="p-4">No ten�s pr�stamos para mostrar.</span>
          </c:otherwise>
        </c:choose>
      </div>  
    </div>
  </t:clientwrapper>
</t:masterpage>