<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<t:masterpage title="Pedir Préstamo" customNavbar="true">
  <t:clientwrapper activeMenuItem="loansMenu">
    <form method="post" action="LoansServlet" class="container flex flex-col gap-4 mx-auto p-4 max-w-7xl mb-8">
      <div class="flex justify-between">
        <h1 class="font-bold text-xl">Solicitar Préstamo</h1>
        <button class="btn btn-primary">
          <i data-lucide="wallet"></i>
          Mis Préstamos
        </button>
      </div>
      <div class="flex flex-col items-center gap-4 p-8 bg-white rounded-xl drop-shadow">
        <h2 class="font-semibold text-lg self-start">Cuánto dinero necesitás?</h2>
        <div class="flex flex-col gap-2.5 w-full">
          <div class="flex justify-between">
            <span>$ 10.000</span>
            <span>$ 200.000</span>
          </div>
          <!-- el min y max habría que establecerlo en la db -->
          <!-- podría venir como un atributo desde el sevlet -->
          <%-- y usariamos min="<%=variableMin%>" --%>
          <input id="requestedAmountInput" name="requestedAmount" type="range" 
            min="10000" max="200000" value="50000" 
            oninput="updateValues(this.value, 'requestedAmount')" 
            class="range range-primary" />
          <span class="font-bold text-xl text-center">
            $ <span id="requestedAmount">50.000</span>
          </span>
        </div>
      </div>
      <div class="flex flex-col items-center gap-4 p-8 bg-white rounded-xl drop-shadow">
        <h2 class="font-semibold text-lg self-start">En cuántas cuotas?</h2>
        <div class="flex flex-col gap-2.5 w-full">
          <div class="flex justify-between">
            <span>1</span>
            <span>48</span>
          </div>
          <input id="quotaAmountInput" name="quotaAmount" type="range" min="1" max="48" value="24" 
            oninput="updateValues(this.value, 'quotaAmount')"
            class="range range-primary" />
          <span class="font-bold text-xl text-center">
            <span id="quotaAmount">24</span> Cuotas
          </span>
        </div>
      </div>
      <div class="flex flex-col items-center gap-4 p-8 bg-white rounded-xl drop-shadow">
        <h2 class="font-semibold text-lg self-start">Resumen del préstamo</h2>
        <div class="flex justify-between w-full">
          <span>Monto solicitado</span>
          <span>$ <span id="amountSummary">-</span></span>
        </div>
        <div class="flex justify-between w-full">
          <span>Plazo</span>
          <span><span id="quotaSummary">-</span> Cuotas</span>
        </div>
        <div class="divider m-0"></div>
        <div class="flex justify-between w-full">
          <span>Cuotas Mensual Estimada</span>
          <span class="font-semibold">$ <span id="finalQuota">-</span></span>
        </div>
      </div>
      <h1 class="font-bold text-lg">Motivo del préstamo</h1>
      <select class="select select-bordered w-full bg-white drop-shadow" name="loanType">
        <option selected>Selecciona un motivo</option>
        <option>Electrodomésticos del hogar</option>
        <option>Hipotecario</option>
      </select>
      <h1 class="font-bold text-lg">Dónde vas a recibir el dinero?</h1>
      <select class="select select-bordered w-full bg-white drop-shadow" name="destinationAccountId">
        <option selected>Selecciona una cuenta</option>
        <option>Cta. 1001 - $ 55.000</option>
        <option>Cta. 1002 - $ 200.000</option>
      </select>
      <button type="submit" class="btn btn-primary"
        name="action" value="requestLoan">
        Solicitar préstamo
      </button>
    </form>
  </t:clientwrapper>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/applyForLoan.js"></script>
</t:masterpage>