<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<t:masterpage title="Transferir" customNavbar="true">
  <t:clientwrapper activeMenuItem="transferMenu">
    <form method="get" action="#" class="container flex flex-col gap-4 mx-auto p-4 max-w-5xl mb-8">
      <!-- DIV TITULO -->
      <div class=" gap-2.5 w-full ">
        <p class="font-bold text-3xl">Transferir dinero</p>
        <p class="text-gray-600 text-xl">Selecciona una cuenta y realiza tu
          transferencia</p>
      </div>
      <!-- CUENTA ORIGEN -->
      <div
        class="flex flex-col border border border-gray-300 rounded-lg w-full bg-white  ">
        <div class="bg-red-600  rounded-t-lg px-6 py-4">
          <p class="font-bold font-sans text-lg text-white ">Cuenta de Origen</p>
        </div>

        <div class="flex flex-col p-6 gap-4  w-full">
          <div class="form-control w-full">
            <label class="label pt-0">
              <span class="label-text font-semibold text-lg ">Cuenta</span>
            </label>
            <select name="originAccount" class="bg-white select text-black select-bordered w-full">
              <option>Cta. 1001 - $ 10.000</option>
              <option>Cta. 1002 - $ 50.000</option>
            </select>
          </div>
        </div>

      </div>
      <!-- DIV CUENTA DESTINO-->
      <div
        class=" flex flex-col w-full bg-white border border-gray-300 rounded-lg">
        <div class="bg-red-600 rounded-t-lg px-6 py-4">
          <p class="font-bold text-lg text-white">Cuenta de destino</p>
        </div>
        <div class="flex flex-col p-6 w-full gap-4">
          <div class="form-control w-full">
            <label for="destinatary" class="label pt-0">
              <span class="label-text font-semibold text-lg ">DNI / Nro.de Cuenta</span>
            </label>
            <input type="text" name="destinatary" placeholder="Ingrese DNI o Nro.de Cuenta destino"
              class="input input-bordered w-full" required/>
          </div>
          <div class="form-control w-full">
            <label for="amountTransfer" class="label">
              <span class="label-text font-semibold text-lg  leading-6">Monto a transferir</span>
            </label>
            <input type="number" name="amountTransfer" placeholder="Ingrese monto a transferir"
              class="input input-bordered w-full" min="1" required/>
          </div>
        </div>
      </div>
      <!-- DIV DESCRIPCIÓN -->
      <div
        class=" flex flex-col w-full bg-white border border-gray-300 rounded-lg">
        <div class="bg-red-600 rounded-t-lg px-6 py-4">
          <p class="font-bold font-sans text-lg text-white">Descripción</p>
        </div>
        <div class="flex flex-col p-6 w-full gap-4">
          <div class="form-control w-full">
            <label class="label pt-0">
              <span class="label-text font-semibold text-lg ">Concepto</span>
            </label>
            <select name="transferConcept" class="bg-white select text-black select-bordered w-full">
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
            <input type="text" name="transferReference" placeholder="Ingrese una referencia"
              class="input input-bordered w-full" required/>
          </div>
        </div>
      </div>
      <!-- DIV BOTON-->
      <div
        class="flex justify-end p-4 flex gap-3 w-full">
        <button class="btn btn-ghost">Cancelar</button>
        <button class="btn btn-primary">Continuar</button>
      </div>
      <!-- CIERRES DIV PADRE-->
    </form>

  </t:clientwrapper>
</t:masterpage>