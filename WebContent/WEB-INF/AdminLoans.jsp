<%@page import="domainModel.Client"%>
<%@page import="domainModel.Loan"%>
<%@page import="domainModel.LoanStatusEnum"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<jsp:useBean id="now" class="java.util.Date" />
<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Necesario para el formato de números como moneda -->
<fmt:setLocale value="es_AR"/>

<c:set var="allLoans"
  value="${requestScope.historyPage.content != null ? 
                                requestScope.historyPage.content : emptyList }" />
<c:set var="pendingLoans"
  value="${requestScope.pendingsPage.content != null ? 
                                requestScope.pendingsPage.content : emptyList}" />

<t:masterpage title="Admin - Préstamos" customNavbar="true">
  <t:adminwrapper activeMenuItem="adminLoansMenu">
    <form method="get" action="Loans" class="container flex flex-col gap-4 mx-auto p-4 max-w-7xl mb-8">
      <h1 class="text-xl font-bold mb-6 text-black">Gestión de Préstamos</h1>

      <!-- Resumen de préstamos -->
      <div class="bg-white rounded-lg shadow-sm mb-6">
        <div class="p-4">
          <h2 class="text-base font-medium mb-4">
            Resumen de préstamos
          </h2>
          <div class="space-y-4">
            <div>
              <p class="text-sm text-gray-500">
                Monto total solicitado (préstamos pendientes):
              </p>
              <p class="text-lg text-blue-600 font-medium">
                $ 1.350.000  (NO IMPLEMENTADO)
              </p>
            </div>
            <div>
              <p class="text-sm text-gray-500">
                Monto total adeudado (préstamos activos):
              </p>
              <p class="text-lg text-red-600 font-medium">
                $ 1.000.000 (NO IMPLEMENTADO)
              </p>
            </div>
          </div>
        </div>
      </div>


      <!-- Préstamos en revisión -->
      <div class="bg-white rounded-lg shadow-sm mb-6 p-4">
        <h2 class="text-base font-medium mb-4">
          Préstamos en revisión
        </h2>
        
        <!-- OPCIONES del listado/página -->
        <div class="flex justify-between p-2.5 mb-2">
          <div class="flex gap-2.5 items-center">              
            <span>Tamaño de página</span>
            <select name="pendingsPageSize"
              onchange="this.form.submit()"
              class="select select-bordered select-sm w-fit bg-white">
              <c:forEach var="size" items="${pendingsPage.pageSizes}">
                <option ${param.pendingsPageSize == size ? 'selected':''}>
                  ${size}
                </option>
              </c:forEach>
            </select>
          </div>
          
          <!-- FILTROS -->
          <div class="flex gap-2.5 items-center">
            <span>Filtrar</span>
            <select name="pendingTypeId" onchange="this.form.submit()"
              class="select select-bordered select-sm w-fit bg-white">
              <c:choose>
                <c:when test="${not empty loanTypes}">
                  <option value="0" selected>Cualquier motivo</option>
                  <c:forEach var="type" items="${loanTypes}">
                    <option value="${type.id}"
                      ${param.pendingTypeId == type.id ? 'selected' : ''}>
                      ${type.name}
                    </option>
                  </c:forEach>
                </c:when>
                <c:otherwise>
                  <option value="0" selected disabled>
                    No se pudieron obtener los datos
                  </option>
                </c:otherwise>
              </c:choose>
            </select>
          </div>
        </div>
        
        <!-- Parámetro necesario para enviar el ID de Préstamo -->
        <input type="hidden" id="selectedLoanId" name="selectedLoanId" />
        
        <!-- TABLA -->
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
              <th class="text-left py-2 font-bold">Acciones</th>
            </tr>
          </thead>
          <tbody>
            <c:choose>
              <c:when test="${empty pendingLoans}">
                <!-- Mostrar mensaje si no hay prestamos pendientes -->
                <tr>
                  <td colspan="7" class="text-center">
                    No hay préstamos pendientes para mostrar
                  </td>
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
                    <td>
                      <fmt:formatNumber value="${loan.requestedAmount}" type="currency" />                            
                    </td>
                    <td>${loan.installmentsQuantity}</td>
                    <!-- BOTONES DE ACCION, APROBAR o DESAPROBAR -->
                    <td class="py-3">
                      <div class="flex gap-2">
                        <%-- IMPORTANTE: Reemplazar el nro '1' por "${loan.id}"
                     para que funcione el envío del formulario correctamente --%>
                        <button type="button"
                          class="p-1.5 bg-green-500 text-white rounded-md"
                          onclick="setSelectedLoan(${loan.loanId},'approve')">
                          <i data-lucide="check"></i>
                        </button>
                        <%-- IMPORTANTE: Reemplazar el nro '1' por "${loan.id}"
                     para que funcione el envío del formulario correctamente --%>
                        <button type="button"
                          class="p-1.5 bg-red-500 text-white rounded-md"
                          onclick="setSelectedLoan(${loan.loanId}, 'reject')">
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
        
        <!-- BOTONES PAGINADO -->
        <div class="flex w-full items-center p-2.5">
          <span class="w-full"> Mostrando
            ${pendingsPage.startElementPos + 1} a ${pendingsPage.endElementPos} de
            ${pendingsPage.totalElements} </span>
          <div class="join flex justify-end w-full">
            <c:forEach var="i" begin="1" end="${pendingsPage.totalPages}">
              <button value="${i}" name="pendingsPage" class="join-item btn"
                ${pendingsPage.totalPages <= 1 ? 'disabled' : ''}>
                ${i}
              </button>
            </c:forEach>
          </div>
        </div>
        
      </div>
      
      <!-- Historial de préstamos -->
      <div class="bg-white rounded-lg shadow-sm mb-6 p-4">
        <h2 class="text-base font-medium mb-4">
          Historial de préstamos
        </h2>
        
        <!-- OPCIONES del listado/página -->
        <div class="flex justify-between p-2.5 mb-2">
          <div class="flex gap-2.5 items-center">              
            <span>Tamaño de página</span>
            <select name="historyPageSize"
              onchange="this.form.submit()"
              class="select select-bordered select-sm w-fit bg-white">
              <c:forEach var="size" items="${historyPage.pageSizes}">
                <option ${param.historyPageSize == size ? 'selected':''}>
                  ${size}
                </option>
              </c:forEach>
            </select>
          </div>
          
          <!-- FILTROS -->
          <div class="flex gap-2.5 items-center">
            <span>Filtrar</span>
            <select name="historyStatusId"
              onchange="this.form.submit()"
              class="select select-bordered select-sm w-fit bg-white">
              <c:choose>
                <c:when test="${not empty loanStatuses}">
                  <option value="0" selected>Cualquier estado</option>
                  <c:forEach var="status" items="${loanStatuses}">
                    <option value="${status.id}"
                      ${param.historyStatusId == status.id ? 'selected' : ''}>
                      ${status.name}
                    </option>
                  </c:forEach>
                </c:when>
                <c:otherwise>
                  <option value="0" selected disabled>
                    No se pudieron obtener los datos
                  </option>
                </c:otherwise>
              </c:choose>
            </select>
            <select name="historyTypeId" onchange="this.form.submit()"
              class="select select-bordered select-sm w-fit bg-white">
              <c:choose>
                <c:when test="${not empty loanTypes}">
                  <option value="0" selected>Cualquier motivo</option>
                  <c:forEach var="type" items="${loanTypes}">
                    <option value="${type.id}"
                      ${param.historyTypeId == type.id ? 'selected' : ''}>
                      ${type.name}
                    </option>
                  </c:forEach>
                </c:when>
                <c:otherwise>
                  <option value="0" selected disabled>
                    No se pudieron obtener los datos
                  </option>
                </c:otherwise>
              </c:choose>
            </select>
          </div>
      </div>
        
      <!-- Tabla -->
      <div class="overflow-x-auto">
        <table class="table">
          <thead>
            <tr class="text-sm border-b border-gray-200 text-base">
              <th>ID</th>
              <th>DNI</th>
              <th>Nombre y Apellido</th>
              <th>Tipo de préstamo</th>
              <th>Monto Solicitado</th>
              <th>Cuotas</th>
              <th>Estado</th>
              <th>Deuda Pendiente</th>
            </tr>
          </thead>
          <tbody>
            <c:choose>
              <c:when test="${empty allLoans}">
                <!-- Mostrar mensaje si no hay prestamos -->
                <tr>
                  <td colspan="8" class="text-center font-normal">
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
                    <td>
                      <fmt:formatNumber value="${loan.requestedAmount}" type="currency" />
                    </td>
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
                        <td>
                          <fmt:formatNumber value="${overdueAmount}" type="currency" />
                        </td>
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
        
        <!-- BOTONES PAGINADO -->
        <div class="flex w-full items-center p-2.5">
          <span class="w-full"> Mostrando
            ${historyPage.startElementPos + 1} a ${historyPage.endElementPos} de
            ${historyPage.totalElements}
          </span>
          <div class="join flex justify-end w-full">
            <c:forEach var="i" begin="1" end="${historyPage.totalPages}">
              <button value="${i}" name="historyPage" class="join-item btn"
                ${historyPage.totalPages <= 1 ? 'disabled' : ''}>
                ${i}
              </button>
            </c:forEach>
          </div>
        </div>
        
      </div>
    </div>

    </form>
    <script>
    	// Esta función crea y emula un formulario con método POST
    	// Para los botones Aprobar/Rechazar, ya que el listado necesita
    	// estar dentro de un form con método GET para el paginado y filtrado
    	const setSelectedLoan = (id, action) => {
        // Form con método POST
        const form = document.createElement("form");
        form.method = "POST";
        form.action = "Loans"; // URL de nuestro Servlet que recibirá los parámetros
        
        // Agrego el campo oculto selectedLoanId al form
        const selectedLoanId = document.getElementById("selectedLoanId");
        selectedLoanId.value = id;
        form.appendChild(selectedLoanId);
        
        // Creo un botón oculto para emular el botón clickeado en el form
        const btnAction = document.createElement("input");
        btnAction.name = "action";
        btnAction.value = action;
        form.appendChild(btnAction);
        
        // Agrego el form oculto al body de la página
        document.body.appendChild(form);
        form.submit(); // Envío el form
    	}
    </script>
  </t:adminwrapper>
</t:masterpage>