<%@page import="domainModel.Client"%>
<%@page import="domainModel.Loan"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<jsp:useBean id="now" class="java.util.Date" />
<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="approvedLoans"
  value="${requestScope.approvedLoans != null ? 
                                requestScope.approvedLoans : emptyList }" />
<c:set var="pendingLoans"
  value="${requestScope.pendingLoans != null ? 
                                requestScope.pendingLoans : emptyList}" />
<c:set var="endedLoans"
  value="${requestScope.endedLoans != null ? 
                                requestScope.endedLoans : emptyList}" />
<c:set var="rejectedLoans"
  value="${requestScope.rejectedLoans != null ? 
                                requestScope.rejectedLoans : emptyList}" />

<t:masterpage title="Admin - Pr�stamos" customNavbar="true">
  <t:adminwrapper activeMenuItem="adminLoansMenu">
    <div class="p-4">
      <h1 class="text-xl font-normal mb-6">Gesti�n de Pr�stamos</h1>

      <!-- Resumen de pr�stamos -->
      <div class="bg-white rounded-lg shadow-sm mb-6">
        <div class="p-4">
          <h2 class="text-base font-medium mb-4">Resumen de
            Pr�stamos</h2>
          <div class="space-y-4">
            <div>
              <p class="text-sm text-gray-500">Monto total
                solicitado (pr�stamos pendientes):</p>
              <p class="text-lg text-blue-600 font-medium">$
                1.350.000</p>
            </div>
            <div>
              <p class="text-sm text-gray-500">Monto total adeudado
                (pr�stamos activos):</p>
              <p class="text-lg text-red-600 font-medium">$
                1.000.000</p>
            </div>
          </div>
        </div>
      </div>


      <!-- Pr�stamos en revisi�n -->
      <form id="pendingLoansForm" method="post" action="Loans">
        <div class="bg-white rounded-lg shadow-sm mb-6">
          <div class="p-4">
            <div class="overflow-x-auto">
              <h2 class="text-base font-medium mb-4 font-bold">Pr�stamos
                en revisi�n</h2>
              <input type="hidden" id="selectedLoanId"
                name="selectedLoanId" />
              <table class="w-full">
                <thead>
                  <tr class="text-sm border-b border-gray-200">
                    <th class="text-left py-2 font-bold">ID</th>
                    <th class="text-left py-2 font-bold">DNI</th>
                    <th class="text-left py-2 font-bold">Nombre y
                      Apellido</th>
                    <th class="text-left py-2 font-bold">Tipo de
                      pr�stamo</th>
                    <th class="text-left py-2 font-bold">Monto
                      Solicitado</th>
                    <th class="text-left py-2 font-bold">Cuotas</th>
                    <th class="text-left py-2 font-bold">Acciones</th>
                  </tr>
                </thead>
                <tbody>
                  <c:choose>
                    <c:when test="${empty pendingLoans}">
                      <!-- Mostrar mensaje si no hay prestamos pendientes -->
                      <tr>
                        <td colspan="6" class="text-center font-bold">No
                          hay pr�stamos pendientes para mostrar =D</td>
                      </tr>
                    </c:when>
                    <c:otherwise>
                      <c:forEach var="loan" items="${pendingLoans}"
                        varStatus="status">
                        <tr class="hover border-b border-gray-200">
                          <td>${loan.loanId}</td>
                          <td>${loan.client.dni}</td>
                          <td>"${loan.client.firstName}
                            ${loan.client.lastName}"</td>
                          <td>${loan.loanType.name}</td>
                          <td>${loan.requestedAmount}</td>
                          <td>${loan.installmentsQuantity}</td>
                          <!-- BOTONES DE ACCION, APROBAR o DESAPROBAR -->
                          <td class="py-3">
                            <div class="flex gap-2">
                              <%-- IMPORTANTE: Reemplazar el nro '1' por "${loan.id}"
                           para que funcione el env�o del formulario correctamente --%>
                              <button type="submit"
                                class="p-1.5 bg-green-500 text-white rounded-md"
                                onclick="setSelectedLoan('${loan.loanId}')"
                                name="action" value="approve">
                                <i data-lucide="check"></i>
                              </button>
                              <%-- IMPORTANTE: Reemplazar el nro '1' por "${loan.id}"
                           para que funcione el env�o del formulario correctamente --%>
                              <button type="submit"
                                class="p-1.5 bg-red-500 text-white rounded-md"
                                onclick="setSelectedLoan('${loan.loanId}')"
                                name="action" value="reject">
                                <i data-lucide="x"></i>
                              </button>
                            </div>
                          </td>
                          <td
                            class="flex flex-col items-center justify-center w-fit px-2.5 rounded-full border border-yellow-500 text-yellow-500 font-semibold">${loan.loanStatus.name}</td>
                        </tr>
                      </c:forEach>
                    </c:otherwise>
                  </c:choose>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </form>

      <!-- Pr�stamos activos/vigentes -->
      <div class="bg-white rounded-lg shadow-sm mb-6">
        <div class="p-4">
          <h2 class="text-base font-medium mb-4">Pr�stamos Activos/Vigentes</h2>
          <div class="overflow-x-auto">
            <table class="w-full">
              <thead>
                <tr class="text-sm border-b border-gray-200">
                  <th class="text-left py-2 font-bold">ID</th>
                  <th class="text-left py-2 font-bold">DNI</th>
                  <th class="text-left py-2 font-bold">Nombre y
                    Apellido</th>
                  <th class="text-left py-2 font-bold">Tipo de
                    pr�stamo</th>
                  <th class="text-left py-2 font-bold">Monto
                    Solicitado</th>
                  <th class="text-left py-2 font-bold">Cuotas</th>
                  <th class="text-left py-2 font-bold">Deuda
                    Pendiente</th>
                </tr>
              </thead>
              <tbody>
                <c:choose>
                  <c:when test="${empty approvedLoans}">
                    <!-- Mostrar mensaje si no hay prestamos pendientes -->
                    <tr>
                      <td colspan="6" class="text-center font-bold">No
                        hay pr�stamos vigentes =D</td>
                    </tr>
                  </c:when>
                  <c:otherwise>
                    <c:forEach var="loan" items="${approvedLoans}"
                      varStatus="status">
                      <tr class="hover border-b border-gray-200">
                        <td>${loan.loanId}</td>
                        <td>${loan.client.dni}</td>
                        <td>"${loan.client.firstName}
                          ${loan.client.lastName}"</td>
                        <td>${loan.loanType.name}</td>
                        <td>${loan.requestedAmount}</td>
                        <td>${loan.installmentsQuantity}</td>
                        <!--aca poner deuda pendiente-->
                        <td
                          class="flex flex-col items-center w-fit px-2.5 rounded-full border border-blue-500 text-blue-500 font-semibold">${loan.loanStatus.name}</td>
                      </tr>
                    </c:forEach>
                  </c:otherwise>
                </c:choose>
              </tbody>
            </table>
          </div>
        </div>
      </div>

      <div>
        <h2 class="text-base font-large mb-4 font-bold"> Historial de Prestamos </h2>
      </div>

      <!-- Pr�stamos rechazados -->
      <div class="bg-white rounded-lg shadow-sm mb-6">
        <div class="p-4">
          <h2 class="text-base font-medium mb-4">Pr�stamos
            Rechazados</h2>
          <div class="overflow-x-auto">
            <table class="w-full">
              <thead>
                <tr class="text-sm border-b border-gray-200">
                  <th class="text-left py-2 font-bold">ID</th>
                  <th class="text-left py-2 font-bold">DNI</th>
                  <th class="text-left py-2 font-bold">Nombre y
                    Apellido</th>
                  <th class="text-left py-2 font-bold">Tipo de
                    pr�stamo</th>
                  <th class="text-left py-2 font-bold">Monto
                    Solicitado</th>
                  <th class="text-left py-2 font-bold">Cuotas</th>
                  <th class="text-left py-2 font-bold">Deuda
                    Pendiente</th>
                </tr>
              </thead>
              <tbody>
                <c:choose>
                  <c:when test="${empty rejectedLoans}">
                    <!-- Mostrar mensaje si no hay prestamos rechazados -->
                    <tr>
                      <td colspan="6" class="text-center font-bold">No
                        hay pr�stamos rechazados =D</td>
                    </tr>
                  </c:when>
                  <c:otherwise>
                    <c:forEach var="loan" items="${rejectedLoans}"
                      varStatus="status">
                      <tr class="hover border-b border-gray-200">
                        <td>${loan.loanId}</td>
                        <td>${loan.client.dni}</td>
                        <td>"${loan.client.firstName}
                          ${loan.client.lastName}"</td>
                        <td>${loan.loanType.name}</td>
                        <td>${loan.requestedAmount}</td>
                        <td>${loan.installmentsQuantity}</td>
                        <!--aca poner deuda pendiente-->
                        <td
                          class="flex flex-col items-center w-fit px-2.5 rounded-full border border-red-500 text-red-500 font-semibold">${loan.loanStatus.name}</td>
                      </tr>
                    </c:forEach>
                  </c:otherwise>
                </c:choose>
              </tbody>
            </table>
          </div>
        </div>
      </div>

      <!-- Pr�stamos Finalizados -->
      <div class="bg-white rounded-lg shadow-sm mb-6">
        <div class="p-4">
          <h2 class="text-base font-medium mb-4">Pr�stamos
            Finalizados</h2>
          <div class="overflow-x-auto">
            <table class="w-full">
              <thead>
                <tr class="text-sm border-b border-gray-200">
                  <th class="text-left py-2 font-bold">ID</th>
                  <th class="text-left py-2 font-bold">DNI</th>
                  <th class="text-left py-2 font-bold">Nombre y
                    Apellido</th>
                  <th class="text-left py-2 font-bold">Tipo de
                    pr�stamo</th>
                  <th class="text-left py-2 font-bold">Monto
                    Solicitado</th>
                  <th class="text-left py-2 font-bold">Cuotas</th>
                  <th class="text-left py-2 font-bold">Estado</th>
                </tr>
              </thead>
              <tbody>
                <c:choose>
                  <c:when test="${empty endedLoans}">
                    <!-- Mostrar mensaje si no hay prestamos rechazados -->
                    <tr>
                      <td colspan="6" class="text-center font-bold">No
                        hay Pr�stamos finalizados =D</td>
                    </tr>
                  </c:when>
                  <c:otherwise>
                    <c:forEach var="loan" items="${endedLoans}"
                      varStatus="status">
                      <tr class="hover border-b border-gray-200">
                        <td>${loan.loanId}</td>
                        <td>${loan.client.dni}</td>
                        <td>"${loan.client.firstName}
                          ${loan.client.lastName}"</td>
                        <td>${loan.loanType.name}</td>
                        <td>${loan.requestedAmount}</td>
                        <td>${loan.installmentsQuantity}</td>
                        <!--aca poner deuda pendiente-->
                        <td
                          class="flex flex-col items-center w-fit px-2.5 rounded-full border border-green-500 text-green-500 font-semibold">${loan.loanStatus.name}</td>
                      </tr>
                    </c:forEach>
                  </c:otherwise>
                </c:choose>
              </tbody>
            </table>
          </div>
        </div>
      </div>

    </div>
    <script>
    	const setSelectedLoan = (id) => {
    		const selectedLoan = document.getElementById("selectedLoanId");
    		selectedLoan.value = id;
    		document.getElementById("pendingLoansForm").submit();
    	}
    </script>
  </t:adminwrapper>
</t:masterpage>