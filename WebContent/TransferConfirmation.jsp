<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<t:masterpage title="Confirmar Transferencia" customNavbar="true">
  <t:clientwrapper activeMenuItem="transferMenu">
    <!-- DIV PADRE -->
    <div class="container md:max-w-[800px] mx-auto flex-col  ">
      <!-- DIV titulo -->
      <div class="flex-col items-start mb-4  gap-2.5">
        <p class="font-bold text-3xl">Confirmar transferencia</p>
        <p class="text-gray-600 text-xl">Verifica que los datos sean correctos antes de confirmar</p>
      </div>
      <!-- DIV CENTRAL -->
      <div class="flex-col md:max-w-[800px] items-center justify-center bg-white rounded-xl  max-w-md w-full overflow-hidden pb-8">
        <div class="flex-col items-start bg-red-500 rounded-t">
          <p class="font-bold font-sans  text-2xl text-white mb-1 py-6 leading-5  px-8">Resumen de transferencia</p>
        </div>
        <div class="flex flex-col items-center px-8 mb-4">
          <p class="text-lg   text-slate-600 ">Monto a transferir</p>
          <p class="font-bold  font-sans text-2xl  text-slate-600 ">$5.000</p>
        </div>
        <div class="flex flex-col items-start px-8 gap-6">
          <div class="flex flex-rows p-2.5 bg-slate-100 rounded-xl w-full space-x-4 ">
            <div class="flex">
              <i data-lucide="app-window"></i>
            </div>
            <div class="flex flex-col leading-none">
              <p class="text-lg  text-slate-600 ">Cuenta Origen</p>
              <p class="font-bold font-sans text-x  text-slate-600 ">Cta.1001</p>
              <p class="text-lg  order-1 text-slate-600 ">Saldo Disponible</p>
              <p class="font-bold order-2 font-sans text-x  text-slate-600 ">$55.000</p>
            </div>
          </div>
        </div>
        <div class="flex justify-center items-center mt-2 mb-2">
          <i data-lucide="arrow-down"></i>
        </div>
        <div class="flex flex-col   px-8 gap-6  ">
          <div  class="flex flex-rows items-start bg-slate-100  space-x-4  rounded-xl p-2.5  w-full  ">
            <div class="flex rounded-sm ">
              <i data-lucide="user"></i>
            </div>
            <div class="flex flex-col leading-none">
              <p class="text-lg  text-slate-600">Cuenta Destino</p>
              <p class="font-bold font-sans text-x  text-slate-600 ">Masino Facundo</p>
              <p class="text-lg  order-1 text-slate-600 ">Cuenta.1005</p>
              <p class="font-bold order-2 font-sans text-x  text-slate-600 ">DNI 10.010.010</p>
            </div>
          </div>
        </div>
        <!-- CIERRE DIV CENTRAL -->
      </div>
      <!-- DIV INFERIOR -->
      <div class="flex  md:max-w-[800px]  justify-end p-4 flex gap-3  w-full max-w-md overflow-hidden ">
        <button class="btn btn-ghost">Cancelar</button>
        <button class="btn btn-primary" type="submit">Transferir</button>
      </div>
      <!-- CIERRE  DIV PADRE -->
    </div>
  </t:clientwrapper>
</t:masterpage>