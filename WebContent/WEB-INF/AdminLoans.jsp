<%@page import="domainModel.Client"%>
<%@page import="domainModel.Loan"%>
<%@page import="domainModel.LoanStatusEnum"%>
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

<t:masterpage title="Admin - Préstamos" customNavbar="true">
  <t:adminwrapper activeMenuItem="adminLoansMenu">
    <div class="p-4">
      <h1 class="text-xl font-bold mb-6">Gestión de Préstamos</h1>

      <!-- Resumen de préstamos -->
      <div class="bg-white rounded-lg shadow-sm mb-6">
        <div class="p-4">
          <h2 class="text-base font-medium mb-4">Resumen de
            Préstamos</h2>
          <div class="space-y-4">
            <div>
              <p class="text-sm text-gray-500">Monto total
                solicitado (préstamos pendientes):</p>
              <p class="text-lg text-blue-600 font-medium">$
                1.350.000</p>
            </div>
            <div>
              <p class="text-sm text-gray-500">Monto total adeudado
                (préstamos activos):</p>
              <p class="text-lg text-red-600 font-medium">$
                1.000.000</p>
            </div>
          </div>
        </div>
      </div>


      <!-- Préstamos en revisión -->
      <form id="pendingLoansForm" method="post" action="Loans">
        <div class="bg-white rounded-lg shadow-sm mb-6">
          <div class="p-4">
            <div class="overflow-x-auto">
              <h2 class="text-base font-medium mb-4 font-bold">Préstamos
                en revisión</h2>
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
                      préstamo</th>
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
                          hay préstamos pendientes para mostrar =D</td>
                      </tr>
                    </c:when>
                    <c:otherwise>
                      <c:forEach var="loan" items="${pendingLoans}"
                        varStatus="status">
                        <tr class="hover border-b border-gray-200">
                          <td>${loan.loanId}</td>
                          <td>${loan.client.dni}</td>
                          <td>
                            ${loan.client.firstName} ${loan.client.lastName}
                          </td>
                          <td>${loan.loanType.name}</td>
                          <td>${loan.requestedAmount}</td>
                          <td>${loan.installmentsQuantity}</td>
                          <!-- BOTONES DE ACCION, APROBAR o DESAPROBAR -->
                          <td class="py-3">
                            <div class="flex gap-2">
                              <%-- IMPORTANTE: Reemplazar el nro '1' por "${loan.id}"
                           para que funcione el envío del formulario correctamente --%>
                              <button type="submit"
                                class="p-1.5 bg-green-500 text-white rounded-md"
                                onclick="setSelectedLoan('${loan.loanId}')"
                                name="action" value="approve">
                                <i data-lucide="check"></i>
                              </button>
                              <%-- IMPORTANTE: Reemplazar el nro '1' por "${loan.id}"
                           para que funcione el envío del formulario correctamente --%>
                              <button type="submit"
                                class="p-1.5 bg-red-500 text-white rounded-md"
                                onclick="setSelectedLoan('${loan.loanId}')"
                                name="action" value="reject">
                                <i data-lucide="x"></i>
                              </button>
                            </div>
                          </td>
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
      
      <!-- Préstamos rechazados -->
      <div class="bg-white rounded-lg shadow-sm mb-6">
        <div class="p-4">
          <h2 class="text-base font-medium mb-4">
            Historial de Préstamos
          </h2>
          <div class="overflow-x-auto">
            <table class="table">
              <thead>
                <tr class="text-sm border-b border-gray-200">
                  <th class="text-left py-2 font-bold">ID</th>
                  <th class="text-left py-2 font-bold">DNI</th>
                  <th class="text-left py-2 font-bold">Nombre y
                    Apellido</th>
                  <th class="text-left py-2 font-bold">Tipo de
                    préstamo</th>
                  <th class="text-left py-2 font-bold">Monto
                    Solicitado</th>
                  <th class="text-left py-2 font-bold">Cuotas</th>
                  <th class="text-left py-2 font-bold">Estado</th>
                  <th class="text-left py-2 font-bold">Deuda
                    Pendiente</th>
                </tr>
              </thead>
              <tbody>
                <c:choose>
                  <c:when test="${empty allLoans}">
                    <!-- Mostrar mensaje si no hay prestamos -->
                    <tr>
                      <td colspan="7" class="text-center font-normal">
                        No hay préstamos para mostrar
                      </td>
                    </tr>
                  </c:when>
                  <c:otherwise>
                    <c:forEach var="loan" items="${allLoans}"
                      varStatus="status">
                      <tr class="hover border-b border-gray-200">
                        <td>${loan.loanId}</td>
                        <td>${loan.client.dni}</td>
                        <td>
                          ${loan.client.firstName} ${loan.client.lastName}
                        </td>
                        <td>${loan.loanType.name}</td>
                        <td>$ ${loan.requestedAmount}</td>
                        <td>${loan.installmentsQuantity}</td>
                        <td>
                          <c:choose>
                            <c:when test="${loan.loanStatus.id eq LoanStatusEnum.PENDING.id}">
                              <span
                                class="flex flex-col items-center w-fit px-2.5 rounded-full border border-yellow-600 text-yellow-700 font-semibold">
                                En Revisión </span>
                            </c:when>
                            <c:when test="${loan.loanStatus.id eq LoanStatusEnum.APPROVED.id}">
                              <span
                                class="flex flex-col items-center w-fit px-2.5 rounded-full border border-blue-600 text-blue-600 font-semibold">
                                Vigente </span>
                            </c:when>
                            <c:when test="${loan.loanStatus.id eq LoanStatusEnum.FINISHED.id}">
                              <span
                                class="flex flex-col items-center w-fit px-2.5 rounded-full border border-green-600 text-green-600 font-semibold">
                                Finalizado </span>
                            </c:when>
                            <c:otherwise>
                              <span
                                class="flex flex-col items-center w-fit px-2.5 rounded-full border border-red-600 text-red-600 font-semibold">
                                Rechazado </span>
                            </c:otherwise>
                          </c:choose>
                        </td>
                        <!-- Deuda pendiente -->
                        <c:set var="overdueAmount" value="0" />
                        <c:forEach var="installment" items="${loan.pendingInstallments}">
                            <c:set var="overdueAmount" value="${overdueAmount + installment.amount}" />
                        </c:forEach>
                        <c:choose>
                          <c:when test="${overdueAmount > 0}">
                            <td>$ ${overdueAmount}</td>
                          </c:when>
                          <c:otherwise>
                            <td>-</td>
                          </c:otherwise>
                        </c:choose>
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