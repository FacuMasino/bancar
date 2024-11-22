<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="now" class="java.util.Date"/>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="loan" value="${requestScope.loan}"/>
<c:set var="outstandingBalance" value="${requestScope.outstandingBalance}" />

<t:masterpage title="Pagar Préstamos" customNavbar="true">
  <t:clientwrapper activeMenuItem="loansMenu">
  	<form method="post" action="Loans" class="container flex flex-col gap-4 mx-auto p-4 max-w-7xl mb-8">
    	<div class="flex justify-between">
  			<div class="flex flex-col gap-6 w-full">
  				<div class="flex">
  					<p class="font-semibold text-xl">Pagar Préstamo</p>
  				</div>
  				<div class="flex flex-col gap-6 p-8 bg-white rounded drop-shadow-sm">
  					<div class="flex justify-between ">
  						<p class="font-semibold text-xl">${loan.loanType.name}</p>
  						<div class="flex flex-col">
  							<p class="text-xl text-slate-600">Monto total del préstamo</p>
  							<p class="font-semibold text-xl text-end">$ ${loan.requestedAmount}</p>
  						</div>
  					</div>
  					<div class="flex flex-col">
  						<div class="flex">
  							<div class="flex flex-col justify-between w-full">
  								<p class="text-slate-600">Cuota a pagar</p>
  								<p class="text-black text-xl font-semibold">$ ${loan.pendingInstallments[0].amount}</p>
  							</div>
  							<div class="flex flex-col justify-between w-full">
  								<p class="text-slate-600">Cuota a pagar</p>
  								<p class="text-black">${loan.pendingInstallments[0].number} de ${loan.installmentsQuantity}</p>
  							</div>
  						</div>
  					</div>
  					<div class="flex flex-col">
  						<div class="flex">
  							<div class="flex flex-col justify-between w-full">
  								<p class="text-slate-600">Vencimiento</p>
  								<p class="text-black">
                    <fmt:formatDate type="date" dateStyle="short" timeStyle="short" 
                      value="${loan.pendingInstallments[0].paymentDueDate}" />                                  
                    <c:if test="${loan.pendingInstallments[0].paymentDueDate < now}">
                      <span class="text-red-600 font-bold text-xs">[VENCIDO]</span>
                    </c:if>
                  </p>
  							</div>
  								<div class="flex flex-col  justify-between w-full">
  									<p class="text-slate-600">Deuda pendiente</p>
  									<p class="text-black">$ ${outstandingBalance}</p>
  								</div>
  						</div>
  					</div>
  				</div>
  				<!-- Listado de cuentas -->
          <div class="flex flex-col gap-6 ">
            <p class="text-xl text-black text-semibold gap-6 w-full">
              Selecciona una cuenta debito
            </p>
            <select name="debitAccountId"
              class="bg-white select text-black select-bordered w-full drop-shadow-sm">
              <c:choose>
                <c:when test="${not empty client.accounts}">
                  <option value="0" selected>Selecciona una cuenta</option>
                  <c:forEach var="account" items="${client.accounts}">
                    <option value="${account.id}">
                      Cta. ${account.id} - $ ${account.balance}
                    </option>
                  </c:forEach>
                </c:when>
                <c:otherwise>
                  <option value="0" disabled>
                    No se pudieron obtener las cuentas
                  </option>
                </c:otherwise>
              </c:choose>
            </select>
            <%-- Se envía el id del préstamo en un input oculto --%>
            <input type="hidden" name="loanId" value="${loan.loanId}" />
            <button type="submit" class="btn btn-primary" name="action"
              value="payInstallment">
                Confirmar Pago
            </button>
          </div>
      </div>
		</div>
	</form>
  </t:clientwrapper>
</t:masterpage>