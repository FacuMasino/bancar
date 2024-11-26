<%@page import="domainModel.Client"%>
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
<c:set var="loanTypes"
  value="${requestScope.loanTypes != null ? 
                                requestScope.loanTypes : emptyList}" />
<c:set var="loanStatuses"
  value="${requestScope.loanStatuses != null ? 
                                requestScope.loanStatuses : emptyList}" />

<!-- Variables para el paginado -->
<c:set var="historyPageList"
  value="${requestScope.historyPage.content != null ? 
                              requestScope.historyPage.content : emptyList}" />
<c:set var="page" value="${requestScope.historyPage}" />

<t:masterpage title="Mis Préstamos" customNavbar="true">
  <t:clientwrapper activeMenuItem="loansMenu">
    <div class="container flex flex-col gap-4 mx-auto p-4 max-w-7xl">
      <!-- Préstamos otorgados -->
      <div class="flex justify-between items-center">
        <h2 class="text-xl font-semibold text-gray-700">Mis
          préstamos</h2>
        <a href="?action=apply" class="btn btn-primary"> Solicitar
          préstamo </a>
      </div>
      <div
        class="divide-y divide-gray-300 rounded-lg shadow p-6 bg-white">

        <!-- Préstamos otorgados -->
        <c:choose>
          <c:when test="${not empty approvedLoans}">
            <c:forEach var="loan" items="${approvedLoans}">
              <div
                class="flex justify-between items-center bg-white p-4">
                <div>
                  <h3 class="text-lg font-medium text-gray-800">${loan.loanType.name}</h3>
                  <p class="text-gray-600">Saldo otorgado: $
                    ${loan.requestedAmount}</p>
                  <p class="text-gray-600">Cuotas pendientes:
                    ${loan.pendingInstallments.size()}</p>
                </div>
                <div class="flex flex-col items-end">
                  <p class="text-gray-600 font-medium">$
                    ${loan.pendingInstallments[0].amount} / mes</p>
                  <%-- Obtengo el primer elemento de la lista para la fecha, ya que es la primer cuota que debe --%>
                  <c:set var="dueDate"
                    value="${loan.pendingInstallments[0].paymentDueDate}" />
                  <c:set var="dueDateClass"
                    value="${dueDate < now ? 'text-red-600' : 'text-gray-600'}" />
                  <p class="${dueDateClass}">
                    Vencimiento:
                    <fmt:formatDate type="date" dateStyle="short"
                      timeStyle="short" value="${dueDate}" />
                    <c:if test="${dueDate < now}">
                      <p class="text-red-600 font-bold text-xs">[VENCIDO]</p>
                    </c:if>
                  </p>
                  <a href="?action=payLoan&id=${loan.loanId}"
                    class="mt-2 text-blue-600 hover:underline flex items-center">
                    Pagar <span class="ml-1">&#8594;</span> <!-- Flecha derecha -->
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
      <h2 class="text-xl font-semibold text-gray-700">Pendientes de
        aprobación</h2>
      <div
        class="divide-y divide-gray-300 rounded-lg shadow p-6 bg-white">

        <c:choose>
          <c:when test="${not empty pendingLoans}">
            <c:forEach var="loan" items="${pendingLoans}">
              <div
                class="flex justify-between items-center bg-white p-4">
                <div>
                  <h3 class="text-lg font-medium text-gray-800">${loan.loanType.name}</h3>
                  <p class="text-gray-600">Monto solicitado: $
                    ${loan.requestedAmount}</p>
                  <p class="text-gray-600">
                    Fecha de solicitud:
                    <fmt:formatDate type="date" dateStyle="short"
                      timeStyle="short" value="${loan.creationDate}" />
                  </p>
                </div>
                <div class="text-right flex items-center">
                  <span
                    class="px-4 py-2 bg-yellow-200 text-yellow-700 font-medium rounded-lg">
                    En revisión </span>
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
      <h2 class="text-xl font-semibold text-gray-700">Historial de
        préstamos</h2>
      <form method="get" action="Loans"
        class="flex flex-col bg-white p-2.5 rounded-xl drop-shadow-sm">
        <div class="flex justify-between p-2.5 mb-2">
          <div class="flex gap-2.5 items-center">
            <span>Tamaño de página</span> <select name="pageSize"
              onchange="this.form.submit()"
              class="select select-bordered select-sm w-fit bg-white">
              <c:forEach var="size" items="${page.pageSizes}">
                <option ${param.pageSize == size ? 'selected':''}>
                  ${size}</option>
              </c:forEach>
            </select>
          </div>
          <div class="flex gap-2.5 items-center">
            <span>Filtrar</span> <select name="loanStatusId"
              onchange="this.form.submit()"
              class="select select-bordered select-sm w-fit bg-white">
              <c:choose>
                <c:when test="${not empty loanStatuses}">
                  <option value="0" selected>Cualquier estado</option>
                  <c:forEach var="status" items="${loanStatuses}">
                    <option value="${status.id}"
                      ${param.loanStatusId == status.id ? 'selected' : ''}>
                      ${status.name}</option>
                  </c:forEach>
                </c:when>
                <c:otherwise>
                  <option value="0" disabled>No se pudieron
                    obtener los datos</option>
                </c:otherwise>
              </c:choose>
            </select> <select name="loanTypeId" onchange="this.form.submit()"
              class="select select-bordered select-sm w-fit bg-white">
              <c:choose>
                <c:when test="${not empty loanTypes}">
                  <option value="0" selected>Cualquier motivo</option>
                  <c:forEach var="type" items="${loanTypes}">
                    <option value="${type.id}"
                      ${param.loanTypeId == type.id ? 'selected' : ''}>
                      ${type.name}</option>
                  </c:forEach>
                </c:when>
                <c:otherwise>
                  <option value="0" disabled>No se pudieron
                    obtener los datos</option>
                </c:otherwise>
              </c:choose>
            </select>
          </div>
        </div>
        <c:choose>
          <c:when test="${not empty historyPageList}">
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
                <c:forEach var="loan" items="${historyPageList}">
                  <tr class="hover">
                    <th>${loan.loanId}</th>
                    <td>${loan.account.id}</td>
                    <td>${loan.loanType.name}</td>
                    <td><c:choose>
                        <c:when test="${loan.loanStatus.id eq 1}">
                          <span
                            class="flex flex-col items-center w-fit px-2.5 rounded-full border border-yellow-600 text-yellow-700 font-semibold">
                            En Revisión </span>
                        </c:when>
                        <c:when test="${loan.loanStatus.id eq 2}">
                          <span
                            class="flex flex-col items-center w-fit px-2.5 rounded-full border border-blue-600 text-blue-600 font-semibold">
                            Vigente </span>
                        </c:when>
                        <c:when test="${loan.loanStatus.id eq 2}">
                          <span
                            class="flex flex-col items-center w-fit px-2.5 rounded-full border border-green-600 text-green-600 font-semibold">
                            Finalizado </span>
                        </c:when>
                        <c:otherwise>
                          <span
                            class="flex flex-col items-center w-fit px-2.5 rounded-full border border-red-600 text-red-600 font-semibold">
                            Rechazado </span>
                        </c:otherwise>
                      </c:choose></td>
                    <td>$ ${loan.requestedAmount}</td>
                    <td>${loan.installmentsQuantity}</td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
            <div class="flex w-full items-center p-2.5">
              <span class="w-full"> Mostrando
                ${page.startElementPos + 1} a ${page.endElementPos} de
                ${page.totalElements} </span>
              <div class="join flex justify-end w-full">
                <c:forEach var="i" begin="1" end="${page.totalPages}">
                  <button value="${i}" name="page" class="join-item btn">${i}</button>
                </c:forEach>
              </div>
            </div>
          </c:when>
          <c:otherwise>
            <span class="p-4">No tenés préstamos para mostrar.</span>
          </c:otherwise>
        </c:choose>
      </form>
    </div>
  </t:clientwrapper>
</t:masterpage>