<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<t:masterpage title="Confirmar Transferencia" customNavbar="true">
  <t:clientwrapper activeMenuItem="transferMenu">
    <div class="container md:max-w-md m-auto flex flex-col gap-4 justify-center py-4">
      <%-- Esta pagina sirve para 3 tipos de transacciones:
          Transferencia, Pago, Acreditación.
          Y el mensaje de "exito" solo se debe mostrar si se
          indica un parametro boolean que lo esté indicando.
          Ejemplo: successAlert=true/false.
          
          Si se trata del detalle luego de una accion de transf/pago
          entonces mostrar "Transferencia/Operacion exitosa" y
          subtitulo "Comprobante de transferencia/pago". 
          
          Si se trata ver el detalle de prestamo o transferencia
          recibida y el usuario solo esta mirando el mismo entonces
          mostrar como titulo un "Detalle de transaccón" y subtitulo
          "Comprobante de operación".
          
          Si se trata ver el detalle de un pago realizado (no la acción
          de pagar, successAlert=true) entonces de titulo "Detalle
          del pago" y subtitulo "Comprobante de pago".
       --%>
      <div class="flex flex-col items-center">
        <i data-lucide="circle-check" class="text-green-600 w-[48px] h-[48px]"></i>
        <h1 class="font-bold text-2xl">
          <%--successAlert=true ? el usuario realizó la acción de transferir/pagar.
              Cambiar condicionalmente depende si es Transferencia/Operación --%>
          ¡Transferencia exitosa!
          <%--successAlert=false ? el usuario solo observa el detalle.
              Detalle del pago/Detalle de transacción(si recibio transf o el prestamo)
           --%>
        </h1>
        <%-- solo mostrar si el usuario realizó la acción transferir/pagar --%>
        <p class="text-lg text-slate-600">
          <%-- transferencia/operacion --%>
          Tu transferencia se realizó correctamente
        </p>
      </div>
      <div id="printableReceipt" class="flex flex-col rounded-lg bg-white drop-shadow">
        <div class="flex flex-col items-center bg-red-600 rounded-t-lg w-full px-6 py-4">
          <h2 class="text-white font-bold w-fit">
            <%-- Comprobante de transferencia/pago --%>
            Comprobante de transferencia
          </h2>
        </div>
        <div class="flex flex-col items-start px-8 py-6 gap-6">
          <div class="flex flex-col">
            <span class="text-sm text-slate-600">Fecha y hora</span>
            <span class="text-sm font-bold">5 de noviembre de 2024, 04:43pm</span>
          </div>
          <div class="flex flex-col">
            <span class="text-sm text-slate-600">Monto</span>
            <span class="text-xl font-bold">$ 5.000</span>
          </div>
          <div class="flex bg-slate-100 gap-2.5 p-2.5 rounded-lg w-full">
            <i data-lucide="credit-card"></i>
            <div class="flex flex-col">
              <span>Cuenta origen</span>
              <span class="font-bold">Bianchini Gonzalo</span>
              <span>Cta. 1001</span>
              <span>DNI 39.000.000</span>
            </div>
          </div>
          <div class="flex bg-slate-100 gap-2.5 p-2.5 rounded-lg w-full">
            <i data-lucide="arrow-right-to-line"></i>
            <div class="flex flex-col">
              <%-- Si es un pago mostrar "Detalle del pago" --%>
              <span>Cuenta destino</span>
              <%-- Si es un prestamo mostrar "Prestamo ID xx" --%>
              <span class="font-bold">Masino Facundo</span>
              <%-- Si es un prestamo mostrar cuota X de X --%>
              <span>Cta. 1002</span>
              <span>DNI 39.000.000</span>
            </div>
          </div>
          <span class="text-sm text-slate-600">Nro. de transacción 100001</span>
          <button class="btn btn-ghost border-gray-200 self-center"
            onclick="printDiv('printableReceipt')">            
            <i data-lucide="printer"></i>
            Imprimir comprobante
          </button>
        </div>
      </div>
      <div class="flex gap-2.5 justify-center">
        <a href="Transfer.jsp" class="btn btn-ghost">Nueva tranferencia</a>
        <a href="Account.jsp" class="btn btn-primary">Ir a mi cuenta</a>
      </div>
    </div>
  </t:clientwrapper>
</t:masterpage>