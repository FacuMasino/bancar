<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Necesario para el formato de números como moneda -->
<fmt:setLocale value="es_AR"/>

<c:set var="client" value="${requestScope.client}" />
<c:set var="setIsDisabled" value="${!client.activeStatus ? 'disabled':''}" />

<t:masterpage title="Admin - Detalles Cliente" customNavbar="true">
  <t:adminwrapper activeMenuItem="adminClientsMenu">
    <form method="get" action="Clients"
      class="container flex flex-col gap-4 mx-auto p-4 max-w-7xl mb-8">
      <div class="flex justify-between">
        <div class="flex gap-1 items-center">
          <a href="Clients" title="Volver"> <i
            data-lucide="circle-chevron-left"></i>
          </a>
          <h1 class="font-bold text-xl">Gestión de Clientes</h1>
        </div>
        <div class="flex gap-2.5">
          <button name="action" value="edit"
            class="btn btn-tertiary bg-base-200 border-gray-200"
            ${setIsDisabled}>Editar Cliente</button>
          <c:choose>
            <c:when test="${client.activeStatus}">
              <a
                href="Accounts?clientId=${client.clientId}"
                class="btn btn-primary"> Gestionar Cuentas </a>
            </c:when>
          </c:choose>
        </div>
      </div>
      <div class="flex flex-col p-6 bg-white rounded-lg drop-shadow-sm">
        <!-- Informacion Personal -->
        <div class="flex gap-2">
          <i class="text-sm" data-lucide="user"></i>
          <h2 class="font-bold text-xl mb-6">Informacion Personal</h2>
        </div>
        <div class="grid grid-cols-1 sm:grid-cols-2 gap-6">
          <div>
            <div class="flex flex-row justify-between gap-6">
              <div class="flex w-1/2">
                <span class="font-bold">ID:</span>
              </div>
              <div class="flex w-1/2">
                <input type="hidden" name="clientId" value="${client.clientId}" />
                <span
                  class="${client.activeStatus == false ? 'text-red-500' : 'text-green-500'} font-semibold">${client.clientId}</span>
              </div>
            </div>
             <div class="flex flex-row justify-between gap-6">   
              <div class="flex w-1/2">
                <span class="font-bold">Apellido y Nombre: </span>
              </div>
              <div class="flex w-1/2">
                <span> ${client.lastName} , ${client.firstName}</span>
              </div>
            </div>
            <div class="flex flex-row justify-between gap-6">
              <div class="flex w-1/2">
                <span class="font-bold">DNI:</span>
              </div>
              <div class="flex w-1/2">
                <span>${client.dni}</span>
              </div>
            </div>   
            <div class="flex flex-row justify-between gap-6">
              <div class="flex w-1/2">
                <span class="font-bold">CUIL:</span>
              </div>
              <div class="flex w-1/2">
                <span>${client.cuil}</span>
              </div>
            </div>
            <div class="flex flex-row justify-between gap-6">
              <div class="flex w-1/2">
                <span class="font-bold">Fecha de nacimiento:</span>
              </div>
              <div class="flex w-1/2">
                <span>${client.birthDate}</span>
              </div>
            </div>
            <div class="flex flex-row justify-between gap-6">
              <div class="flex w-1/2">
                <span class="font-bold">Género:</span>
              </div>
              <div class="flex w-1/2">
                <span>${client.sex != null ? client.sex : 'No especificado'}</span>
              </div>
            </div>
            <div class="flex flex-row justify-between gap-6">
              <div class="flex w-1/2">
                <span class="font-bold">Nacionalidad:</span>
              </div>
              <div class="flex w-1/2">
                <span>${client.nationality.name}</span>
              </div>
            </div>
          </div>
          <div>
            <div class="flex flex-row justify-between gap-6">
              <!-- Falta PAIS de Address. Se agrega?-->
              <div class="flex w-1/2">
                <span class="font-bold">Domicilio:</span>
              </div>
              <div class="flex w-1/2">
                <span>${client.address.streetName}
                  ${client.address.streetNumber} ${client.address.flat}</span>
              </div>
            </div>
            <c:if test="${not empty client.address.details}">
              <div class="flex flex-row justify-between gap-6">
                <div class="flex w-1/2">
                  <span class="font-bold">Observaciones:</span>
                </div>
                <div class="flex w-1/2">
                  <span>${client.address.details}</span>

                </div>
              </div>
            </c:if>
            <div class="flex flex-row justify-between gap-6">
              <div class="flex w-1/2">
                <span class="font-bold">Ciudad y CP:</span>
              </div>
              <div class="flex w-1/2">
                <span>${client.address.city.name}
                  (${client.address.city.zipCode})</span>
              </div>
            </div>
            <div class="flex flex-row justify-between gap-6">
              <div class="flex w-1/2">
                <span class="font-bold">Provincia:</span>
              </div>
              <div class="flex w-1/2">
                <span>${client.address.province.name}</span>
              </div>
            </div>
            <div class="flex flex-row justify-between gap-6">
              <div class="flex w-1/2">
                <span class="font-bold">Email:</span>
              </div>
              <div class="flex w-1/2">
                <span>${client.email}</span>
              </div>
            </div>
            <div class="flex flex-row justify-between gap-6">
              <div class="flex w-1/2">
                <span class="font-bold">Telefono:</span>
              </div>
              <div class="flex w-1/2">
                <span>${client.phone}</span>
              </div>
            </div>
            <div class="flex flex-row justify-between gap-6">
              <div class="flex w-1/2">
                <span class="font-bold">Estado:</span>
              </div>
              <div class="flex w-1/2">
                <span
                  class="flex flex-col items-center w-fit px-2.5 rounded-full border font-semibold 
                  ${client.activeStatus ? 'border-green-600 text-green-600' : 'border-red-600 text-red-600'}">
                  ${client.activeStatus ? 'Activo' : 'Baja'} </span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="flex flex-col p-6 bg-white rounded-lg drop-shadow-sm">
        <!-- Resumen de cuentas -->
        <div class="flex gap-2">
          <i class="text-sm" data-lucide="circle-dollar-sign"></i>
          <h2 class="font-bold text-xl mb-6">Resumen de Cuentas</h2>
        </div>
        <div class="flex flex-col w-full">
          <table class="table bg-white w-full">
            <thead>
              <tr>
                <th class="text-gray-700">Nro</th>
                <th class="text-gray-700">Tipo Cuenta</th>
                <th class="text-gray-700">Saldo</th>
                <!--  
                  <th class="text-gray-700">Deuda</th>
                  -->
              </tr>
            </thead>
            <tbody>
              <c:choose>
                <c:when test="${empty client.accounts}">
                  <!-- Mostrar mensaje si no hay prestamos -->
                  <tr>
                    <td colspan="3" class="text-center">
                      El cliente no posee cuentas
                    </td>
                  </tr>
                </c:when>
                <c:otherwise>
                  <c:forEach var="account" items="${client.accounts}"
                    varStatus="status">
                    <tr class="hover">
                      <td>${account.id}</td>
                      <td>${account.accountType.name}</td>
                      <td class="text-green-600 font-semibold"> <fmt:formatNumber value="${account.balance}" type="currency" /></td>
                    </tr>
                  </c:forEach>
                </c:otherwise>
             </c:choose>
            </tbody>
          </table>
        </div>
      </div>
      <div class="flex flex-col p-6 bg-white rounded-lg drop-shadow-sm">
        <!-- Prestamos -->
        <div class="flex gap-2">
          <i class="text-sm" data-lucide="credit-card"></i>
          <h2 class="font-bold text-xl mb-6">Prestamos</h2>
        </div>
        <div class="flex flex-col w-full">
          <table class="table bg-white w-full">
            <thead>
              <tr>
                <th class="text-gray-700">ID Prestamo</th>
                <th class="text-gray-700">Fecha de solicitud</th>
                <th class="text-gray-700">Tipo</th>
                <th class="text-gray-700">Monto Solicitado</th>
                <th class="text-gray-700">Estado</th>
              </tr>
            </thead>
            <tbody>
              <%-- If en JSTL --%>
              <c:choose>
                <c:when test="${empty client.loans}">
                  <!-- Mostrar mensaje si no hay prestamos -->
                  <tr>
                    <td colspan="5" class="text-center">
                      No hay préstamos para mostrar.
                    </td>
                  </tr>
                </c:when>
                <c:otherwise>
                  <c:forEach var="loan" items="${client.loans}"
                    varStatus="status">
                    <tr class="hover">
                     <td>${loan.loanId}</td>
                      <td>
                        <fmt:formatDate type="date" dateStyle="short"
                          timeStyle="short" value="${loan.creationDate}"/>
                      </td>
                      <td>${loan.loanType.name}</td>
                      <td class="text-black-600 font-semibold"><fmt:formatNumber value="${loan.requestedAmount}" type="currency" /></td>                   
                     <td> <c:choose>
                        <c:when test="${loan.loanStatus.id == 1}">
                          <span
                            class="flex flex-col items-center w-fit px-2.5 rounded-full border border-yellow-600 text-yellow-700 font-semibold">
                            En Revisión </span>
                        </c:when>
                        <c:when test="${loan.loanStatus.id == 2}">
                          <span
                            class="flex flex-col items-center w-fit px-2.5 rounded-full border border-blue-600 text-blue-600 font-semibold">
                            Vigente </span>
                        </c:when>
                        <c:when test="${loan.loanStatus.id == 3}">
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
                    </tr>
                  </c:forEach>
                </c:otherwise>
              </c:choose>
            </tbody>
          </table>
        </div>
      </div>
    </form>
  </t:adminwrapper>
</t:masterpage>