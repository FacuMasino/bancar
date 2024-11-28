<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Necesario para el formato de números como moneda -->
<fmt:setLocale value="es_AR"/>

<t:masterpage title="Confirmar Transferencia" customNavbar="true">
  <t:clientwrapper activeMenuItem="transferMenu">
    <div class="container md:max-w-[800px] mx-auto flex-col p-4">

      <!-- Título -->

      <div class="flex-col items-start mb-4  gap-2.5">
        <p class="font-bold text-3xl">Confirmar transferencia</p>
        <p class="text-gray-600 text-xl">Verificá que los datos sean correctos antes de confirmar.</p>
      </div>
      <div class="flex-col md:max-w-[800px] items-center justify-center bg-white rounded-xl  max-w-md w-full overflow-hidden pb-8">
        <div class="flex-col items-start bg-red-600 rounded-t">
          <p class="font-bold font-sans  text-xl text-white mb-1 py-4 leading-5  px-6">Resumen de transferencia</p>
        </div>

        <!-- Monto -->

        <div class="flex flex-col items-center py-4">
          <p class="text-lg  text-slate-600 ">Monto a transferir</p>
          <p class="font-bold  font-sans text-2xl  text-slate-600 "> <fmt:formatNumber value="${movement.amount}" type="currency" /></p>
        </div>

        <div class="flex flex-col items-start px-8 gap-6">
          <div class="flex flex-rows p-2.5 bg-slate-100 rounded-xl w-full space-x-4 ">
            <div class="flex">
              <i data-lucide="app-window"></i>
            </div>

            <!-- Cuenta de origen -->

            <div class="flex flex-col leading-none">
              <p class="text-lg  text-slate-600 ">Cuenta de origen</p>
              <p class="font-bold font-sans text-x  text-slate-600 ">Cta. Nro. ${originAccount.id}</p>
              <p class="text-lg  order-1 text-slate-600 ">Saldo Disponible</p>
              <p class="font-bold order-2 font-sans text-x  text-slate-600 ">$ ${originAccount.balance}</p>
            </div>

          </div>
        </div>
        <div class="flex justify-center items-center mt-2 mb-2">
          <i data-lucide="arrow-down"></i>
        </div>
        <div class="flex flex-col   px-8 gap-6  ">
          <div class="flex flex-rows items-start bg-slate-100  space-x-4  rounded-xl p-2.5  w-full  ">
            <div class="flex rounded-sm ">
              <i data-lucide="user"></i>
            </div>

            <!-- Cuenta de destino -->

            <div class="flex flex-col leading-none">
              <p class="text-lg  text-slate-600">Cuenta de destino</p>
              <p class="font-bold font-sans text-x  text-slate-600 ">
              	${destinationClient.firstName} ${destinationClient.lastName}
              </p>
              <p class="text-lg  order-1 text-slate-600 ">
              	Cta. Nro. ${destinationAccount.id}
              </p>
              <p class="font-bold order-2 font-sans text-x  text-slate-600 ">
              	DNI ${destinationClient.dni}
              </p>
            </div>

          </div>
        </div>
      </div>

      <!-- Botones -->

      <div class="flex  md:max-w-[800px]  justify-end p-4 flex gap-3  w-full max-w-md overflow-hidden ">
        <a href="${pageContext.request.contextPath}/Client/Transfer" class="btn btn-ghost">
          Cancelar
        </a>
        <button class="btn btn-primary" type="submit"
          onclick="modal_confirmation_Transfer.showModal()">Transferir</button>
        <dialog id="modal_confirmation_Transfer" class="modal">
          <div class="modal-box bg-white">
            <h3 class="text-lg font-bold">Transferir</h3>
            <p class="py-4">Esta acción no se puede deshacer. ¿Desea continuar?</p>
            <div class="modal-action">
              <form method="post" action="Transfer">
                <a href="Transfer.jsp" class="btn">Cancelar</a>
                <button type="submit" class="btn btn-primary" name="action" value="goToDetails">
                  Continuar
                </button>
              </form>
            </div>
          </div>
        </dialog>
      </div>

    </div>
  </t:clientwrapper>
</t:masterpage>