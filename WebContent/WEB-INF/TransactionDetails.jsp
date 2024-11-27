<%@page import="domainModel.MovementTypeEnum" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:masterpage title="Confirmar Transferencia" customNavbar="true">
  <t:clientwrapper activeMenuItem="transferMenu">
    <div class="container md:max-w-md m-auto flex flex-col gap-4 justify-center py-4">
    
      <!-- Título -->
      
      <c:choose>
        <c:when test="${success == true}">
	      <div class="flex flex-col items-center">
	        <i data-lucide="circle-check" class="text-green-600 w-[48px] h-[48px]"></i>
	        <h1 class="font-bold text-2xl">
	          ¡Transacción exitosa!
	        </h1>
	        <p class="text-lg text-slate-600">
	          Tu transferencia se realizó correctamente.
	        </p>
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
	            <span class="text-xl font-bold">$ ${movement.amount}</span>
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
		        <c:when test="${movementTypeId == MovementTypeEnum.TRANSFER.id}">
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
		        <c:when test="${movementTypeId == MovementTypeEnum.LOAN_PAYMENT.id}">
			      <!-- FACU, ACA TENES QUE PONER LA INFO DEL PAGO DE CUOTA DE PRESTAMO -->
			      <!-- ACORDATE DE SETEAR EL SIGUIENTE ATRIBUTO DESDE EL SERVLET CORRESPONDIENTE: -->
			      <!-- request.setAttribute("movementTypeId", MovementTypeEnum.LOAN_PAYMENT.getId()); -->
			      <!-- TAMBIEN VAS A TENER QUE SETEAR LA VARIABLE BOOLEANA: -->
			      <!-- request.setAttribute("success", success); -->
			      <!-- PARA QUE FUNCIONEN LAS LINEAS 12 Y 39 DE ESTE JSP -->
		        </c:when>
		   	  </c:choose>
	
	          <span class="text-sm text-slate-600">Nro. de transacción ${movement.id}</span>
	
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
        <a href="${pageContext.request.contextPath}/Client/Transfer" class="btn btn-ghost">
          Nueva transferencia
        </a>
        <a href="${pageContext.request.contextPath}/Client" class="btn btn-primary">
          Ir a mi cuenta
        </a>
      </div>
    </div>
  </t:clientwrapper>
</t:masterpage>