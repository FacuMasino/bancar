<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<t:masterpage title="Pedir Pr�stamo" customNavbar="true">
  <t:clientwrapper activeMenuItem="loansMenu">
    <form method="post" action="Loans" class="container flex flex-col gap-4 mx-auto p-4 max-w-7xl mb-8">
      <div class="flex justify-between">
        <h1 class="font-bold text-xl">Solicitar Pr�stamo</h1>
        <button class="btn btn-primary">
          <i data-lucide="wallet"></i>
          Mis Pr�stamos
        </button>
      </div>
      <div class="flex flex-col items-center gap-3 p-6 bg-white rounded-xl drop-shadow">
        <h2 class="font-semibold text-lg self-start">Cu�nto dinero necesit�s?</h2>
        <div class="flex flex-col gap-2.5 w-full">
          <div class="flex justify-between">
            <span>$ 100.000</span>
            <span>$ 200.000.000</span>
          </div>
          <!-- el min y max habr�a que establecerlo en la db -->
          <!-- podr�a venir como un atributo desde el sevlet -->
          <%-- y usariamos min="<%=variableMin%>" --%>
          <input id="requestedAmountInput" name="requestedAmount" type="range" 
            min="100000" max="2000000" value="50000" 
            oninput="updateValues(this.value, 'requestedAmount')" 
            class="range range-primary" />
          <span class="font-bold text-xl text-center">
            $ <span id="requestedAmount">100.000</span>
          </span>
        </div>
      </div>
      <div class="flex flex-col items-center gap-3 p-6 bg-white rounded-xl drop-shadow">
        <h2 class="font-semibold text-lg self-start">En cu�ntas cuotas?</h2>
        <div class="flex flex-col gap-2.5 w-full">
          <div class="flex justify-between">
            <span>1</span>
            <span>48</span>
          </div>
          <input id="installmentsInput" name="installmentsQty" type="range" min="1" max="48" value="24" 
            oninput="updateValues(this.value, 'installments')"
            class="range range-primary" />
          <span class="font-bold text-xl text-center">
            <span id="installments">24</span> Cuotas
          </span>
        </div>
      </div>
      <div class="flex flex-col items-center gap-3 p-6 bg-white rounded-xl drop-shadow">
        <h2 class="font-semibold text-lg self-start">Resumen del pr�stamo</h2>
        <div class="flex justify-between w-full">
          <span>Monto solicitado</span>
          <span>$ <span id="amountSummary">-</span></span>
        </div>
        <div class="flex justify-between w-full">
          <span>Monto total con intereses</span>
          <span>$ <span id="finalCost">-</span></span>
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
          <span>Cuotas Mensual</span>
          <span class="font-semibold">$ <span id="installmentsCost">-</span></span>
        </div>
      </div>
      <h1 class="font-bold text-lg">Motivo del pr�stamo</h1>
      <select class="select select-bordered w-full bg-white drop-shadow" name="loanType">
        <option selected>Selecciona un motivo</option>
        <option>Electrodom�sticos del hogar</option>
        <option>Hipotecario</option>
      </select>
      <h1 class="font-bold text-lg">D�nde vas a recibir el dinero?</h1>
      <select class="select select-bordered w-full bg-white drop-shadow" name="destinationAccountId">
        <option selected>Selecciona una cuenta</option>
        <option>Cta. 1001 - $ 55.000</option>
        <option>Cta. 1002 - $ 200.000</option>
      </select>
      <button type="submit" class="btn btn-primary"
        name="action" value="requestLoan">
        Solicitar pr�stamo
      </button>
    </form>
  </t:clientwrapper>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/applyForLoan.js"></script>
</t:masterpage>