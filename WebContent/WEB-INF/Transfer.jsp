<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="accounts" value="${requestScope.accounts != null ? requestScope.accounts : emptyList}" />

<t:masterpage title="Transferir" customNavbar="true">
  <t:clientwrapper activeMenuItem="transferMenu">
    <form method="post" action="Transfer" class="container flex flex-col gap-4 mx-auto p-4 max-w-5xl mb-8">

      <!-- Título -->

      <div class=" gap-2.5 w-full ">
        <p class="font-bold text-3xl">Transferir dinero</p>
        <p class="text-gray-600 text-xl">Seleccioná una cuenta y realizá tu transferencia</p>
      </div>

      <!-- Cuenta de origen -->

      <div class="flex flex-col border border border-gray-300 rounded-lg w-full bg-white  ">
        <div class="bg-red-600  rounded-t-lg px-6 py-4">
          <p class="font-bold font-sans text-lg text-white ">Cuenta de origen</p>
        </div>

        <div class="flex flex-col p-6 gap-4  w-full">
          <div class="form-control w-full">
            <label class="label pt-0">
              <span class="label-text font-semibold text-lg ">Cuenta</span>
            </label>
            <select name="originAccountId" class="bg-white select text-black select-bordered w-full">
              <c:choose>
                <c:when test="${empty accounts}">
                  <option disabled selected>Error: No hay cuentas para mostrar.</option>
                </c:when>
                <c:otherwise>
                  <c:set var="draftAccountId" value="${originAccount.id}" />
                  <c:forEach var="account" items="${accounts}">
                    <option value="${account.id}" ${draftAccountId==account.id ? 'selected' :''}>
                      CBU: ${account.cbu} - Saldo: $ ${account.balance} (${account.accountType.name})
                    </option>
                  </c:forEach>
                </c:otherwise>
              </c:choose>
            </select>            
          </div>
        </div>

      </div>

      <!-- Cuenta de destino -->

      <div class=" flex flex-col w-full bg-white border border-gray-300 rounded-lg">
        <div class="bg-red-600 rounded-t-lg px-6 py-4">
          <p class="font-bold text-lg text-white">Cuenta de destino</p>
        </div>
        <div class="flex flex-col p-6 w-full gap-4">
          <div class="form-control w-full">
            <label for="destinationAccountCbu" class="label pt-0">
              <span class="label-text font-semibold text-lg ">CBU</span>
            </label>
            <input type="number" name="destinationAccountCbu" placeholder="Ingrese el CBU de destino"
              class="input input-bordered w-full" value="${destinationAccount.cbu}" required />
          </div>
          <div class="form-control w-full">
            <label for="transferAmount" class="label">
              <span class="label-text font-semibold text-lg  leading-6">Monto a transferir</span>
            </label>
            <input type="number" name="transferAmount" placeholder="Ingrese monto a transferir"
              class="input input-bordered w-full" min="1" value="${movement.amount}" required />
          </div>
        </div>
      </div>

      <!-- Descripción -->

      <div class=" flex flex-col w-full bg-white border border-gray-300 rounded-lg">
        <div class="bg-red-600 rounded-t-lg px-6 py-4">
          <p class="font-bold font-sans text-lg text-white">Descripción</p>
        </div>
        <div class="flex flex-col p-6 w-full gap-4">
          <div class="form-control w-full">
            <label class="label pt-0">
              <span class="label-text font-semibold text-lg ">Concepto</span>
            </label>
            <select name="transferType" class="bg-white select text-black select-bordered w-full">
              <option>Seleccione el motivo</option>
              <option>Alquileres</option>
              <option>Haberes</option>
              <option>Servicios</option>
              <option>Varios</option>
            </select>
          </div>
          <div class="form-control w-full">
            <label class="label">
              <span class="label-text font-semibold text-lg  leading-6">Referencia</span>
            </label>
            <input type="text" name="transferDescription" placeholder="Ingrese una referencia"
              class="input input-bordered w-full" required />
          </div>
        </div>
      </div>

      <!-- Botón-->

      <div class="flex justify-end p-4 flex gap-3 w-full">
        <a href="Account.jsp" class="btn btn-ghost">Cancelar</a>
        <button type="submit" class="btn btn-primary" name="action" value="goToConfirmation">
          Continuar
        </button>
      </div>

    </form>
  </t:clientwrapper>
</t:masterpage>