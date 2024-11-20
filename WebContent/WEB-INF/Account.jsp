<%@page import="domainModel.Client"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="client" value="${requestScope.client}" />

<t:masterpage title="Mi Cuenta" customNavbar="true">
  <t:clientwrapper activeMenuItem="accountMenu">
    <form method="get" action="Clients"
      class="container flex flex-col gap-4 mx-auto p-4 max-w-7xl mb-8">
      <input type="hidden" name="clientId" value="${client.clientId}" />
      <div class="container mx-auto p-4">
        <div class="mb-2">
          <h1 class="font-bold text-3xl">Bienvenido/a</h1><h1 class="font-bold text-3xl">${client.firstName } ${client.lastName }</h1>
        </div>
        <div class="flex items-center justify-between mb-4">
          <h2 class="font-bold text-lg">Mi Cuenta</h2>
          <select
            class="bg-white w-64 font-bold font-sans select text-black select-bordered">
            <option>Cta.10001</option>
            <option>Cta.10002</option>
          </select>
        </div>
        <div
          class=" flex flex-col p-4 mb-4 border border border-gray-300 rounded-lg  gap-6 w-full bg-white">
          <div class="flex flex-col p-2 gap-4  w-full  ">
            <div class="flex flex-row text-lg">
              <p>
                Cuenta Nro.<br> <span class="font-bold">10001</span>
              </p>
            </div>
            <div>
              <p class="font-bold text-2xl">$10.000,00</p>
            </div>
          </div>
        </div>
        <div
          class=" flex flex-col p-4 border border border-gray-300 rounded-lg  gap-6 w-full bg-white">
          <div>
            <h2 class="font-bold text-xl">�ltimos Movimientos</h2>
          </div>
          <div>
            <div class="mb-4">
              <!--  <form method="get" action="AccountServlet" 
                class="flex justify-between p-2.5 mb-2"> -->
              <label
                class="input input-sm input-bordered flex items-center gap-2">
                <input type="text" class="grow"
                placeholder="Buscar cuenta, monto" name="searchInput">
                <svg xmlns="http://www.w3.org/2000/svg" width="16"
                  height="16" viewBox="0 0 24 24" fill="none"
                  stroke="currentColor" stroke-width="2"
                  stroke-linecap="round" stroke-linejoin="round"
                  data-lucide="search" class="lucide lucide-search">
                  <circle cx="11" cy="11" r="8"></circle>
                  <path d="m21 21-4.3-4.3"></path></svg>
              </label>
              <div class="flex gap-2.5">
                <input name="transactionDate" type="date"
                  class="border p-1 rounded border-gray-200"> <select
                  class="select select-bordered select-sm w-fit bg-white"
                  name="transactionType">
                  <option selected>Seleccione tipo</option>
                  <option>Transferencia</option>
                  <option>Pago</option>
                  <option>Cr�dito</option>
                </select>
              </div>
              <!--  </form>-->
              <table class="table bg-white w-full">
                <thead>
                  <tr>
                    <th>Fecha</th>
                    <th>Descripci�n</th>
                    <th>Monto</th>
                    <th>Acciones</th>
                  </tr>
                </thead>
                <tbody>
                  <tr class="hover">
                    <td>01/10/2024</td>
                    <td>Dep�sito de n�mina</td>
                    <td class="text-green-600 font-semibold">+$1,500.00</td>
                    <td><a
                      href="TransactionDetails.jsp?id=123&successAlert=false"
                      class="btn btn-ghost" title="Ver detalle"> <i
                        data-lucide="eye"></i>
                    </a></td>
                  </tr>
                  <tr class="hover">
                    <td>03/10/2024</td>
                    <td>Pago de servicios</td>
                    <td class="text-red-600 font-semibold">-$200.00</td>
                    <td><a
                      href="TransactionDetails.jsp?id=123&successAlert=false"
                      class="btn btn-ghost" title="Ver detalle"> <i
                        data-lucide="eye"></i>
                    </a></td>
                  </tr>
                  <tr class="hover">
                    <td>05/10/2024</td>
                    <td>Transferencia recibida</td>
                    <td class="text-green-600 font-semibold">+$300.00</td>
                    <td><a
                      href="TransactionDetails.jsp?id=123&successAlert=false"
                      class="btn btn-ghost" title="Ver detalle"> <i
                        data-lucide="eye"></i>
                    </a></td>
                  </tr>
                  <tr class="hover">
                    <td>07/10/2024</td>
                    <td>Pago tarjeta de cr�dito</td>
                    <td class="text-red-600 font-semibold">-$500.00</td>
                    <td><a
                      href="TransactionDetails.jsp?id=123&successAlert=false"
                      class="btn btn-ghost" title="Ver detalle"> <i
                        data-lucide="eye"></i>
                    </a></td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div class="flex justify-end">
              <div class="join">
                <button class="join-item btn">1</button>
                <button class="join-item btn btn-active">2</button>
                <button class="join-item btn">3</button>
                <button class="join-item btn">4</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </form>
  </t:clientwrapper>
</t:masterpage>