<%@page import="domainModel.MovementTypeEnum" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!-- Necesario para el formato de números como moneda -->
<fmt:setLocale value="es_AR"/>

<t:masterpage title="Detalle de movimiento" customNavbar="true">
  <t:clientwrapper activeMenuItem="transferMenu">
    <div class="container md:max-w-md m-auto flex flex-col gap-4 justify-center py-4">
    
      <!-- Título -->
      
      <c:choose>
        <c:when test="${success == true}">
	      <div class="flex flex-col items-center">
	        <i data-lucide="circle-check" class="text-green-600 w-[48px] h-[48px]"></i>
            <c:choose>
              <%-- Si se trata de una operación que acaba de suceder --%>
              <c:when test="${isCurrent == true}">
      	        <h1 class="font-bold text-2xl">
      	          ¡Transacción exitosa!
      	        </h1>
      	        <p class="text-lg text-slate-600">
      	          Tu 
                  ${movementTypeId == MovementTypeEnum.TRANSFER.id ? " transferencia " : " operación "}
                  se realizó correctamente.
      	        </p>
              </c:when>
              <%-- Si está viendo el detalle de una operación pasada --%>
              <c:otherwise>
                <h1 class="font-bold text-2xl">
                  Detalle de transacción
                </h1>
                <p class="text-lg text-slate-600">
                  Comprobante de operación
                </p>
              </c:otherwise>
            </c:choose>
	      </div>
        </c:when>
        <c:otherwise>
	      <div class="flex flex-col items-center">
	        <i data-lucide="x-circle" class="text-red-600 w-[48px] h-[48px]"></i>
	        <h1 class="font-bold text-2xl">
	          Error en la transacción
	        </h1>
	        <p class="text-lg text-slate-600">
	          Hubo un problema al realizar la operación.
	        </p>
	      </div>
        </c:otherwise>
   	  </c:choose>

	  <!-- Comprobante -->

      <c:choose>
        <c:when test="${success == true}">
	      <div id="printableReceipt" class="flex flex-col rounded-lg bg-white drop-shadow">
	        <div class="flex flex-col items-center bg-red-600 rounded-t-lg w-full px-6 py-4">
	          <h2 class="text-white font-bold w-fit">
	            Comprobante de transferencia
	          </h2>
	        </div>
	
	        <div class="flex flex-col items-start px-8 py-6 gap-6">
	        
	          <!-- Fecha y hora -->
	
	          <div class="flex flex-col">
	            <span class="text-sm text-slate-600">Fecha y hora</span>
	            <span class="text-sm font-bold">${movement.formattedDateTime}</span>
	          </div>
	
			  <!-- Monto -->
	
	          <div class="flex flex-col">
	            <span class="text-sm text-slate-600">Monto</span>
	            <span class="text-xl font-bold">
                <c:set var="amount" value="${movement.amount > 0 ? movement.amount : movement.amount * -1}" />
                <fmt:formatNumber value="${amount}" type="currency" />
              </span>
	          </div>
	
			  <!-- Cuenta de origen -->
	
	          <div class="flex bg-slate-100 gap-2.5 p-2.5 rounded-lg w-full">
	            <i data-lucide="credit-card"></i>
	            <div class="flex flex-col">
	              <span>Cuenta de origen</span>
	              <span class="font-bold">
	                ${originClient.firstName} ${originClient.lastName}
	              </span>
	              <span>Cta. Nro. ${originAccount.id}</span>
	              <span>DNI ${originClient.dni}</span>
	            </div>
	          </div>
	
			  <!-- Cuenta de destino / Cuota del préstamo -->
			  
		      <c:choose>
		        <c:when test="${movement.movementType.id == MovementTypeEnum.TRANSFER.id}">
		          <div class="flex bg-slate-100 gap-2.5 p-2.5 rounded-lg w-full">
		            <i data-lucide="arrow-right-to-line"></i>
		            <div class="flex flex-col">
		              <span>Cuenta de destino</span>
		              <span class="font-bold">
		                ${destinationClient.firstName} ${destinationClient.lastName}
		              </span>
		              <span>Cta. Nro. ${destinationAccount.id}</span>
		              <span>DNI ${destinationClient.dni}</span>
		            </div>
		          </div>
		        </c:when>
		        <c:when test="${movement.movementType.id == MovementTypeEnum.LOAN_PAYMENT.id}">
			      <div class="flex bg-slate-100 gap-2.5 p-2.5 rounded-lg w-full">
		            <i data-lucide="arrow-right-to-line"></i>
		            <div class="flex flex-col">
		              <span>Pago de préstamo</span>
		              <span class="font-bold">
		                Préstamo ${paidLoan.loanId}
		              </span>
		              <span>Cuota ${installment.number} de ${paidLoan.installmentsQuantity}</span>
		              <span>
                        Prox. Vencimiento
                        <fmt:formatDate type="date" dateStyle="short" timeStyle="short" 
                          value="${paidLoan.pendingInstallments[0].paymentDueDate}" /> 
                      </span>
		            </div>
		          </div>
		        </c:when>
		   	  </c:choose>
	
	          <span class="text-sm text-slate-600">Nro. de transacción ${movement.transactionId}</span>
	
	          <button class="btn btn-ghost border-gray-200 self-center"
	            onclick="printDiv('printableReceipt')">            
	            <i data-lucide="printer"></i>
	            Imprimir comprobante
	          </button>
	        </div>
	      </div>
        </c:when>
   	  </c:choose>
      
      <!-- Botones -->

      <div class="flex gap-2.5 justify-center">
        <c:if test="${movement.movementType.id == MovementTypeEnum.TRANSFER.id}">
          <a href="${pageContext.request.contextPath}/Client/Transfer" class="btn btn-ghost">
            Nueva transferencia
          </a>
        </c:if>
        <a href="${pageContext.request.contextPath}/Client" class="btn btn-primary">
          Ir a mi cuenta
        </a>
      </div>
    </div>
  </t:clientwrapper>
</t:masterpage>