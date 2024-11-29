<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:masterpage title="Pedir Préstamo" customNavbar="true">
  <t:clientwrapper activeMenuItem="loansMenu">
    <form method="post" action="Loans" class="container flex flex-col gap-4 mx-auto p-4 max-w-7xl mb-8">
      <div class="flex justify-between">
        <h1 class="font-bold text-xl">Solicitar Préstamo</h1>
        <button class="btn btn-primary">
          <i data-lucide="wallet"></i>
          Mis Préstamos
        </button>
      </div>
      <div class="flex flex-col items-center gap-3 p-6 bg-white rounded-xl drop-shadow">
        <h2 class="font-semibold text-lg self-start">Cuánto dinero necesitás?</h2>
        <div class="flex flex-col gap-2.5 w-full">
          <div class="flex justify-between">
            <span>$ 100.000</span>
            <span>$ 2.000.000</span>
          </div>
          <!-- el min y max habría que establecerlo en la db -->
          <!-- podría venir como un atributo desde el sevlet -->
          <%-- y usariamos min="<%=variableMin%>" --%>
          <input id="requestedAmountInput" name="requestedAmount" type="range" 
            min="100000" max="2000000" value="50000" 
            oninput="updateValues(this.value, 'requestedAmount', true)" 
            class="range range-primary" />
          <span class="font-bold text-xl text-center">
            <span id="requestedAmount">100.000</span>
          </span>
        </div>
      </div>
      <div class="flex flex-col items-center gap-3 p-6 bg-white rounded-xl drop-shadow">
        <h2 class="font-semibold text-lg self-start">En cuántas cuotas?</h2>
        <div class="flex flex-col gap-2.5 w-full">
          <div class="flex justify-between">
            <span>1</span>
            <span>48</span>
          </div>
          <input id="installmentsInput" name="installmentsQty" type="range" min="1" max="48" value="24" 
            oninput="updateValues(this.value, 'installments', false)"
            class="range range-primary" />
          <span class="font-bold text-xl text-center">
            <span id="installments">24</span> Cuotas
          </span>
        </div>
      </div>
      <div class="flex flex-col items-center gap-3 p-6 bg-white rounded-xl drop-shadow">
        <h2 class="font-semibold text-lg self-start">Resumen del préstamo</h2>
        <div class="flex justify-between w-full">
          <span>Monto solicitado</span>
          <span><span id="amountSummary">-</span></span>
        </div>
        <div class="flex justify-between w-full">
          <span>Monto total con intereses</span>
          <span><span id="finalCost">-</span></span>
        </div>
        <div class="flex justify-between w-full">
          <span>Plazo</span>
          <span><span id="quotaSummary">-</span> Cuotas</span>
        </div>
        <div class="flex justify-between w-full">
          <input type="hidden" id="interestRateInput" name="interestRate" value="5.50" />
          <span>Tasa efectiva anual</span>
          <span>5.50 %</span>
        </div>
        <div class="divider m-0"></div>
        <div class="flex justify-between w-full">
          <span>Cuota Mensual</span>
          <span class="font-semibold"><span id="installmentsCost">-</span></span>
        </div>
      </div>
      <h1 class="font-bold text-lg">Motivo del préstamo</h1>
      <select class="select select-bordered w-full bg-white drop-shadow" name="loanType">
        <c:choose>
            <c:when test="${not empty loanTypes}">
              <option value="0" selected>Selecciona un motivo</option>
              <c:forEach var="type" items="${loanTypes}">
                <option value="${type.id}">${type.name}</option>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <option value="0" disabled>No se pudieron obtener los datos</option>
            </c:otherwise>
        </c:choose>
      </select>
      <h1 class="font-bold text-lg">Dónde vas a recibir el dinero?</h1>
      <select class="select select-bordered w-full bg-white drop-shadow" name="destinationAccountId">
        <c:choose>
          <c:when test="${not empty client.accounts}">
            <option value="0" selected>Selecciona una cuenta</option>
            <c:forEach var="account" items="${client.accounts}">
              <option value="${account.id}">Cta. ${account.id} - $ ${account.balance}</option>
            </c:forEach>
          </c:when>
          <c:otherwise>
            <option value="0" disabled>No se pudieron obtener las cuentas</option>
          </c:otherwise>
        </c:choose>
      </select>
      <button type="submit" class="btn btn-primary"
        name="action" value="request">
        Solicitar préstamo
      </button>
    </form>
  </t:clientwrapper>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/applyForLoan.js"></script>
</t:masterpage>