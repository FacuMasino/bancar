<%@page import="domainModel.Client"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- Variables
JSTL --%>

<c:set var="client" value="${requestScope.client}" />
<c:set var="accountTypes" value="${requestScope.accountTypes != null ? requestScope.accountTypes : emptyList}" />

<t:masterpage title="Admin - Cuentas del cliente" customNavbar="true">
  <t:adminwrapper activeMenuItem="adminClientsMenu">
    <div class="container flex flex-col gap-4 mx-auto p-4 max-w-7xl mb-8">
      <div class="flex justify-between">
        <h1 class="font-bold text-xl">Gestionar Cuentas</h1>
      </div>
      <div class="flex flex-col p-6 bg-white rounded-lg drop-shadow-sm">
        <h2 class="font-bold text-xl mb-6">
          Cliente: <span>${client.firstName} ${client.lastName}</span>
        </h2>

        <div class="flex flex-col w-fit">
          <div class="flex w-full justify-between gap-2">
            <span class="font-bold">ID del cliente:</span> <span>${client.clientId}</span>
          </div>
          <div class="flex w-full justify-between gap-2">
            <span class="font-bold">Cantidad de Cuentas:</span>

            <c:choose>
              <c:when test="${not empty client.accounts}">
                <span>${client.accounts.size()}</span>
              </c:when>
              <c:otherwise>
                <span>El cliente no tiene cuentas.</span>
              </c:otherwise>
            </c:choose>
          </div>
          <div class="flex justify-between gap-2 text-red-600">
            <span class="font-bold">Deuda:(no va por enunciado....)</span> <span>$
              10.000</span>
          </div>
          <div class="flex justify-between gap-2 text-blue-600">
            <span class="font-bold">Cantidad de Pr&eacute;stamos:</span>
            <c:choose>
              <c:when test="${not empty client.loans}">
                <span>${client.loans.size()}</span>
              </c:when>
              <c:otherwise>
                <span>El cliente no tiene prestamos.</span>
              </c:otherwise>
            </c:choose>
          </div>
        </div>
      </div>
      <div class="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
        <%-- Ac� ir�a un For iterando sobre las cuentas del cliente --%>
        <c:choose>
          <c:when test="${not empty client.accounts}">
            <c:forEach var="account" items="${client.accounts}"
              varStatus="status">
              <div
                class="flex flex-col p-6 bg-white rounded-lg w-full drop-shadow-sm">
                <h2 class="font-bold text-xl mb-6">
                  Cuenta: <span>${account.getAccountType().getName()}</span>
                </h2>
                <span class="font-bold text-xl text-green-600">${account.balance}</span>
                <div class="flex flex-col w-fit mb-4">
                  <div class="flex justify-between gap-2">
                    <span>Nro. de cuenta:</span> <span>${account.id }</span>
                  </div>
                </div>

                <div class="flex justify-between">
                  <button type="button"
                    onclick="openEditModal(${account.id }, ${account.balance})"
                    class="btn btn-sm btn-ghost border-gray-200">
                    Modificar</button>
                  <button class="btn btn-sm btn-primary" type="button"
                    onclick="openDeleteModal(${account.id })">
                    <i class="w-[16px]" data-lucide="trash"></i>
                  </button>
                </div>
              </div>
            </c:forEach>
          </c:when>
          <c:otherwise>
            <span>El cliente no tiene cuentas.</span>
          </c:otherwise>
        </c:choose>
      </div>

      <%--Este div se debe mostrar condicionalmente, Solo si el cliente tiene
      menos de 3 cuentas --%>
      <c:if test="${client.accounts.size() < 3}">
        <form method="post" action="Accounts"
          class="flex flex-col p-6 bg-white rounded-lg w-full items-end gap-6 drop-shadow-sm">
          <h2 class="font-bold text-xl w-full">Crear Cuenta</h2>
          <div class="flex items-center justify-end w-full gap-2.5">
            <span>Tipo de cuenta:</span> <select name="accountType"
              class="select select-bordered bg-white w-1/2 drop-shadow">
              <c:choose>
                <c:when test="${empty accountTypes}">
                  <!-- Mostrar mensaje si tipo de cuentas está vacía (no va a ocurrir) -->
                  <option disabled selected>Error: No hay tipos de cuenta para mostrar</option>
                </c:when>
                <c:otherwise>
                  <c:forEach var="accountType" items="${accountTypes}">
                    <option selected>Seleccione una cuenta</option>
                    <option value="${accountType.id}"
                      ${accountType.id ? 'selected' :''}>
                      ${accountType.name}</option>
                  </c:forEach>
                </c:otherwise>
              </c:choose>
            </select>
          </div>
          <button type="submit" name="action" value="newAccount"
            class="btn btn-primary w-fit">Crear cuenta</button>
        </form>
      </c:if>

      <%-- Este es el modal que se abre al hacer click en eliminar --%>
      <dialog id="modal_delete_account" class="modal">
      <form method="post" action="Accounts" class="modal-box bg-white">
        <input type="hidden" name="clientId" value="${client.clientId}" />
        <input id="accountNumberDelete" type="hidden" name="accountId" />
        <h3 class="text-lg font-bold">Eliminar cuenta</h3>
        <p class="pt-4 pb-1">Est&aacute; seguro que desea eliminar la
          cuenta?</p>
        <p class="py-1 text-red-600">Esta acci&oacute;n no se puede
          deshacer!</p>
        <div class="modal-action">
          <button type="button" onclick="modal_delete_account.close()"
            class="btn">Cancelar</button>
          <button type="submit" name="action" value="deleteAccount"
            class="btn btn-primary">Eliminar</button>
        </div>
      </form>
      </dialog>
      <%-- Este es el modal que se abre al hacer click en modificar --%>
      <dialog id="modal_edit_account" class="modal">
      <form method="post" action="Accounts" class="modal-box bg-white">
        <input type="hidden" name="clientId" value="${client.clientId}" />
        <h3 class="text-lg font-bold">Modificar Saldo</h3>
        <div class="py-4 flex items-center justify-end gap-2.5">
          <span>Saldo:</span> <input id="accountNumberEdit" type="hidden"
            name="accountId" /> <input id="balanceInput" type="number"
            name="accountBalance" class="input input-bordered w-full max-w-xs"
            min="0" required />
        </div>
        <div class="modal-action">
          <!-- if there is a button in form, it will close the modal -->
          <button type="button" onclick="modal_edit_account.close()" class="btn">
            Cancelar</button>
          <button type="submit" name="action" value="editAccount"
            class="btn btn-primary">Modificar</button>
        </div>
      </form>
      </dialog>
    </div>
    <script>
      const openEditModal = (accountNumber, actualBalance) => {
        const modalAccountNumber =
          document.getElementById("accountNumberEdit");
        const modalBalanceInput = document.getElementById("balanceInput");

        modalAccountNumber.value = accountNumber;
        modalBalanceInput.value = actualBalance;
        modal_edit_account.showModal();
      };
      const openDeleteModal = (accountNumber) => {
        const modalAccountNumber =
          document.getElementById("accountNumberDelete");
        
        modalAccountNumber.value = accountNumber;
        modal_delete_account.showModal();
      };
    </script>
  </t:adminwrapper>
</t:masterpage>
