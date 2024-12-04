<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="now" class="java.util.Date"/>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!-- Necesario para el formato de números como moneda -->
<fmt:setLocale value="es_AR"/>

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
              	  <p class="font-semibold text-xl text-end">
                    <fmt:formatNumber value="${loan.requestedAmount}" type="currency" />
                  </p>
              	</div>
              </div>
              <div class="flex flex-col">
              	<div class="flex">
                  <div class="flex flex-col justify-between w-full">
                  	<p class="text-slate-600">Cuota a pagar</p>
                  	<p class="text-black text-xl font-semibold">
                      <fmt:formatNumber value="${loan.pendingInstallments[0].amount}" type="currency" />
                    </p>
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
                    <p class="text-black">
                      <fmt:formatNumber value="${outstandingBalance}" type="currency" />
                    </p>
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
                          Cta. ${account.id} - <fmt:formatNumber value="${account.balance}" type="currency" />
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
                <input type="hidden" name="installmentId" value="${loan.pendingInstallments[0].installmentId}" />
                <button type="button" class="btn btn-primary"
                  onclick="modal_pay_loan.showModal()">
                    Confirmar Pago
                </button>
              </div>
          </div>
        </div>
    
        <!-- MODAL Confirmación de pago -->
        <dialog id="modal_pay_loan" class="modal">
          <div class="modal-box bg-white">
            <div class="sm:flex sm:items-start">
              <div class="mx-auto flex size-12 shrink-0 items-center justify-center rounded-full bg-red-100 sm:mx-0 sm:size-10">
                <svg class="size-6 text-red-600" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true" data-slot="icon">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126ZM12 15.75h.007v.008H12v-.008Z"></path>
                </svg>
              </div>
              <div class="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left">
                <h3 class="text-base font-semibold text-gray-900" id="modal-title">Confirmación</h3>
                <div class="my-2">
                  <p class="text-gray-500">
                    Confirma que desea realizar el pago de
                    <span class="font-semibold">
                      <fmt:formatNumber value="${loan.pendingInstallments[0].amount}" type="currency" />
                    </span>
                    ? el monto será debitado de la cuenta seleccionada.
                  </p>
                </div>
              </div>
            </div>
            <div class="modal-action">
                <button type="submit" class="btn btn-primary" name="action"
                  value="payInstallment">
                    Confirmar Pago
                </button>
                <button type="button" class="btn" 
                  onclick="modal_pay_loan.close()">
                  Cancelar
                </button>
            </div>
          </div>
        </dialog>
	</form>
  </t:clientwrapper>
</t:masterpage>