<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="client" value="${requestScope.client}" />

<t:masterpage title="Admin - Detalles Cliente" customNavbar="true">
  <t:adminwrapper activeMenuItem="adminClientsMenu">
    <form method="get" action="AdminClients"
      class="container flex flex-col gap-4 mx-auto p-4 max-w-7xl mb-8">
      <div class="flex justify-between">
        <h1 class="font-bold text-xl">Gestión de Clientes</h1>
        <div class="flex gap-2.5">
          <button name="action" value="edit" class="btn btn-tertiary bg-base-200 border-gray-200">
            Editar Cliente </button> <a href="/AdminClientAccounts?clientId=${client.clientId}"
            class="btn btn-primary"> Gestionar Cuentas </a>
        </div>
      </div>
      <div class="flex flex-col p-6 bg-white rounded-lg drop-shadow-sm">
        <!-- Informacion Personal -->
        <div class="flex gap-2">
          <i class="text-sm" data-lucide="user"></i>
          <h2 class="font-bold text-xl mb-6">Informacion Personal</h2>
        </div>
        <div class="flex flex-col w-1/2 gap-5">
          <div class="flex flex-row justify-between gap-6">
            <div class="flex w-1/2">
              <span class="font-bold">ID:</span>
            </div>
            <div class="flex w-1/2">
              <input type="hidden" name="clientId" value="${client.clientId}" />
              <span>${client.clientId}</span>
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
              <span class="font-bold">Nombres:</span>
            </div>
            <div class="flex w-1/2">
              <span>${client.firstName} ${client.lastName}</span>
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
              <span class="font-bold">Direccion:</span>
            </div>
            <div class="flex w-1/2">
              <span>${client.address.streetName} ${client.address.streetNumber} ${client.address.flat}</span>
            </div>
          </div>
          <div class="flex flex-row justify-between gap-6">
            <div class="flex w-1/2">
              <span class="font-bold">Estado:</span>
            </div>
            <div class="flex w-1/2">
              <!-- Falta darle vida al ACTIVO-INACTIVO -->
              <span class="flex flex-col items-center w-fit px-2.5 rounded-full border border-green-600 text-green-600 font-semibold">${client.activeStatus ? 'Activo' : 'Inactivo'}</span>
            
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
              <tr class="hover">
                <td>1001</td>
                <td>Caja de Ahorros</td>
                <td class="text-green-600 font-semibold">$1,500.00</td>
              </tr>
              <tr class="hover">
                <td>1005</td>
                <td>Cuenta Corriente</td>
                <td class="text-red-600 font-semibold">-$200.00</td>
              </tr>
              <tr class="hover">
                <td>1008</td>
                <td>Caja de Ahorros</td>
                <td class="text-green-600 font-semibold">$300.00</td>
              </tr>
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
                <th class="text-gray-700">Tipo</th>
                <th class="text-gray-700">Monto</th>
                <th class="text-gray-700">Cuotas</th>
                <th class="text-gray-700">Estado</th>
                <!--  
                  <th class="text-gray-700">Campo extra</th>
                  -->
              </tr>
            </thead>
            <tbody>
              <tr class="hover">
                <td>9001</td>
                <td>Prestamo Personal</td>
                <td class="text-black-600 font-semibold">$15,000.00</td>
                <td class="text-black-600 font-semibold">24</td>
                <td>
               	  <span class="flex flex-col items-center w-fit px-2.5 rounded-full border border-yellow-500 text-yellow-500 font-semibold">
                    En Revision
               	  </span>
                </td>
              </tr>
              <tr class="hover">
                <td>9078</td>
                <td>Prestamo Hipotecario</td>
                <td class="text-black-600 font-semibold">$850,000.00</td>
                <td class="text-black-600 font-semibold">60</td>
                <td>
                  <span class="flex flex-col items-center w-fit px-2.5 rounded-full border border-green-600 text-green-600 font-semibold">
                    Aprobado
               	  </span>
                </td>
              </tr>
              <tr class="hover">
                <td>9101</td>
                <td>Prestamo Personal</td>
                <td class="text-black-600 font-semibold">$80,000.00</td>
                <td class="text-black-600 font-semibold">48</td>
                <td>
                  <span class="flex flex-col items-center w-fit px-2.5 rounded-full border border-red-600 text-red-600 font-semibold">
                    Rechazado
               	  </span>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </form>
  </t:adminwrapper>
</t:masterpage>




