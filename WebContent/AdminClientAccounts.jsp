<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<t:masterpage title="Admin - Cuentas del cliente" customNavbar="true">
  <t:adminwrapper activeMenuItem="adminClientsMenu">
    <div class="container flex flex-col gap-4 mx-auto p-4 max-w-7xl mb-8">
      <div class="flex justify-between">
        <h1 class="font-bold text-xl">Gestionar Cuentas</h1>
      </div>
      <div class="flex flex-col p-6 bg-white rounded-lg drop-shadow-sm">
        <h2 class="font-bold text-xl mb-6">Cliente: Masino Facundo</h2>
        <div class="flex flex-col w-fit">
          <div class="flex w-full justify-between gap-2">
            <span class="font-bold">ID del cliente:</span>
            <span>4</span>
          </div>
          <div class="flex w-full justify-between gap-2">
            <span class="font-bold">Cuentas activas:</span>
            <span>2</span>
          </div>
        </div>
      </div>
      <div class="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
        <%-- Acá iría un For iterando sobre las cuentas del cliente --%>
        <!-- Cuenta 1 -->
        <div class="flex flex-col p-6 bg-white rounded-lg w-full drop-shadow-sm">
          <h2 class="font-bold text-xl mb-4">Cuenta Corriente</h2>
          <span class="font-bold text-xl text-green-600">$ 30.000</span>
          <div class="flex flex-col w-fit mb-4">
            <div class="flex justify-between gap-2">
              <span>Nro. de cuenta:</span>
              <span>1010</span>
            </div>
            <div class="flex justify-between gap-2 text-red-600">
              <span>Deuda:</span>
              <span>$ 10.000</span>
            </div>
            <div class="flex justify-between gap-2 text-blue-600">
              <span>Préstamos:</span>
              <span>1</span>
            </div>
          </div>
          <div class="flex justify-between">
            <%-- Los parametros de openEditModal deben asignarse
                De forma dinámica, ejemplo:
                "openEditModal(<%=accNumber%>,<%=accBalance%>"
            --%>
            <button type="button" onclick="openEditModal(1010,10000)"
              class="btn btn-sm btn-ghost border-gray-200">
              Modificar
           </button>
            <button class="btn btn-sm btn-primary">
              <i class="w-[16px]" data-lucide="trash"></i>
            </button>
          </div>
        </div>
        <!-- Cuenta 2 -->
        <div class="flex flex-col p-6 bg-white rounded-lg w-full drop-shadow-sm">
          <h2 class="font-bold text-xl mb-4">Caja de ahorro</h2>
          <span class="font-bold text-xl text-green-600">$ 300.000</span>
          <div class="flex flex-col w-fit mb-4">
            <div class="flex justify-between gap-2">
              <span>Nro. de cuenta:</span>
              <span>1011</span>
            </div>
            <div class="flex justify-between gap-2 text-red-600">
              <span>Deuda:</span>
              <span>$ 0</span>
            </div>
            <div class="flex justify-between gap-2 text-blue-600">
              <span>Préstamos:</span>
              <span>0</span>
            </div>
          </div>
          <div class="flex justify-between">
            <%-- Los parametros de openEditModal deben asignarse
                  De forma dinámica, ejemplo:
                  "openEditModal(<%=accNumber%>,<%=accBalance%>"
              --%>
            <button type="button" onclick="openEditModal(1011,300000)"
              class="btn btn-sm btn-ghost border-gray-200">
              Modificar
           </button>
            <button class="btn btn-sm btn-primary">
              <i class="w-[16px]" data-lucide="trash"></i>
            </button>
          </div>
        </div>
      </div>
      <%--Este div se debe mostrar condicionalmente,
          Solo si el cliente tiene menos de 3 cuentas --%>
      <form method="get" action="#" class="flex flex-col p-6 bg-white rounded-lg w-full items-end gap-6 drop-shadow-sm">
        <h2 class="font-bold text-xl w-full">Crear Cuenta</h2>
        <div class="flex items-center justify-end w-full gap-2.5">
          <span>Tipo de cuenta:</span>
          <select name="accountType" class="select select-bordered bg-white w-1/2 drop-shadow">
            <option selected>Selecciona una cuenta</option>
            <option>Cuenta Corriente</option>
            <option>Caja de Ahorro</option>
          </select>
        </div>
        <button class="btn btn-primary w-fit">
            Crear cuenta
         </button>
      </form>
      <%-- Este es el modal que se abre al hacer click en modificar --%>
      <dialog id="modal_edit_account" class="modal">
        <form method="get" action="#" class="modal-box bg-white">
          <h3 class="text-lg font-bold">Modificar Saldo</h3>
          <div class="py-4 flex items-center justify-end gap-2.5">
            <span>Saldo:</span>
            <input id="accountNumberInput" type="hidden" name="accountNumber"/>
            <input id="balanceInput" type="number" name="accountBalance" class="input input-bordered w-full max-w-xs" min="0" required/>
          </div>
          <div class="modal-action">
              <!-- if there is a button in form, it will close the modal -->
              <button type="button" onclick="modal_edit_account.close()" class="btn">Cancelar</button>
              <button type="submit" class="btn btn-primary">Modificar</button>
          </div>
        </form>
      </dialog>
    </div>
    <script>
      const openEditModal = (accountNumber, actualBalance) => {
        const modalAccountNumber = document.getElementById("accountNumberInput");
        const modalBalanceInput = document.getElementById("balanceInput");
        
        modalAccountNumber.value = accountNumber;
        modalBalanceInput.value = actualBalance;
        modal_edit_account.showModal();
      }
    </script>
  </t:adminwrapper>
</t:masterpage>