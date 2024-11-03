<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<t:masterpage title="Transferir" customNavbar="true">
  <t:clientwrapper activeMenuItem="transferMenu">
    <div class="container flex flex-col gap-4 mx-auto p-4 max-w-7xl mb-8">
      <!-- DIV TITULO -->
      <div class=" gap-2.5 w-full ">
        <p class="font-bold text-3xl">Transferir dinero</p>
        <p class="text-gray-600 text-xl">Selecciona una cuenta y realiza tu
          transferencia</p>
      </div>
      <!-- CUENTA ORIGEN -->
      <div
        class=" flex flex-col md:max-w-[800px] h-52 border border border-gray-300 rounded-lg  gap-6 w-full bg-white  ">
        <div class=" bg-red-500  rounded-lg mb-1">
          <p class="font-bold font-sans text-2xl p-4 text-white ">Cuenta de
            Origen</p>
        </div>

        <div class="flex flex-col p-2 gap-4  w-full  ">
          <select class="bg-white font-bold font-sans select text-black select-bordered w-full">
            <option>Cta.10001</option>
            <option>Cta.10002</option>
          </select>
          <div class="flex flex-row   text-lg"><p> Saldo Disponible: <span class="font-bold"> $ 10.000</span></p>
          </div>
        </div>

      </div>
      <!-- DIV CUENTA DESTINO-->
      <div
        class=" flex flex-col md:max-w-[800px] h-72  gap-6 w-full bg-white border border-gray-300 rounded-lg">
        <div class="bg-red-500 rounded-lg ">
          <p class="font-bold font-sans text-2xl p-4  text-white flex-col  mb-1 ">Cuenta de destino</p>
        </div>
        <div class="flex flex-col  p-2 mb-8   w-full">
          <label for="destinatary" class="label"> <span class="label-text font-bold text-lg ">DNI / Nro.de Cuenta</span>
          </label> <input type="text" name="destinatary"
            placeholder="Ingrese DNI o Nro.de Cuenta destino "
            class="input input-bordered w-full" /> <label for="amountTransfer"
            class="label"> <span
            class="label-text font-bold text-lg  leading-6">Monto a transferir</span>
          </label> <input type="text" name="amountTransfer"
            placeholder="Ingrese monto a transferir "
            class="mb-8 input input-bordered w-full" />
        </div>
      </div>
      <!-- DIV BOTON-->
      <div
        class="flex  md:max-w-[800px] justify-end p-4 flex gap-3  w-full max-w-md overflow-hidden ">
        <button class="btn btn-ghost  h-[20px] w-[61px] ">Cancelar</button>
        <button class="btn btn-primary  h-[20px] w-[68px] gap-2 rounded-lg ">Continuar</button>
      </div>
      <!-- CIERRES DIV PADRE-->
    </div>

  </t:clientwrapper>
</t:masterpage>