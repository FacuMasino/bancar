<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<t:masterpage title="Mis Préstamos" customNavbar="true">
  <t:clientwrapper activeMenuItem="loansMenu">
    <div class="p-4">
      <!-- Préstamos otorgados -->
      <div class="mb-8">
        <div class="flex justify-between items-center mb-6">
          <h2 class="text-xl font-semibold text-gray-700">
            Mis préstamos
          </h2>
          <button class="btn btn-primary">
            Solicitar préstamo
          </button>
        </div>
        <div class="divide-y divide-gray-300">

          <!-- Préstamo otorgado 1 (reemplazar por for) -->
          <div class="flex justify-between items-center bg-gray-50 p-4">
            <div>
              <h3 class="text-lg font-medium text-gray-800">Préstamo personal</h3>
              <p class="text-gray-600">Saldo otorgado: $150,000</p>
              <p class="text-gray-600">Cuotas pendientes: 23 de 48</p>
            </div>
            <div class="flex flex-col items-end">
              <p class="text-gray-600 font-medium">$5000 / mes</p>
              <p class="text-gray-600">Vencimiento: 23/11/24</p>
              <button class="mt-2 text-blue-600 hover:underline flex items-center">
                Pagar
                <span class="ml-1">&#8594;</span> <!-- Flecha derecha -->
              </button>
            </div>
          </div>

          <!-- Préstamo otorgado 2 (reemplazar por for) -->
          <div class="flex justify-between items-center bg-gray-50 p-4">
            <div>
              <h3 class="text-lg font-medium text-gray-800">Préstamo hipotecario</h3>
              <p class="text-gray-600">Saldo otorgado: $1,000,000</p>
              <p class="text-gray-600">Cuotas pendientes: 15 de 30</p>
            </div>
            <div class="flex flex-col items-end">
              <p class="text-gray-600 font-medium">$20000 / mes</p>
              <p class="text-gray-600">Vencimiento: 15/12/24</p>
              <button class="mt-2 text-blue-600 hover:underline flex items-center">
                Pagar
                <span class="ml-1">&#8594;</span> <!-- Flecha derecha -->
              </button>
            </div>
          </div>

        </div>
      </div>

      <!-- Préstamos pendientes de aprobación -->
      <div>
        <h2 class="text-xl font-semibold text-gray-700 mb-6">
          Pendientes de aprobación
        </h2>
        <div class="divide-y divide-gray-300">

          <!-- Préstamo pendiente 1 (reemplazar por for) -->
          <div class="flex justify-between items-center bg-gray-50 p-4">
            <div>
              <h3 class="text-lg font-medium text-gray-800">Préstamo automotriz</h3>
              <p class="text-gray-600">Monto solicitado: $6,000,000.00</p>
              <p class="text-gray-600">Fecha de solicitud: 01/10/24</p>
            </div>
            <div class="text-right flex items-center">
              <span class="px-4 py-2 bg-yellow-200 text-yellow-700 font-medium rounded-lg">
                En revisión
              </span>
            </div>
          </div>

          <!-- Préstamo pendiente 2 (reemplazar por for) -->
          <div class="flex justify-between items-center bg-gray-50 p-4">
            <div>
              <h3 class="text-lg font-medium text-gray-800">Préstamo educativo</h3>
              <p class="text-gray-600">Monto solicitado: $500,000.00</p>
              <p class="text-gray-600">Fecha de solicitud: 10/09/24</p>
            </div>
            <div class="text-right flex items-center">
              <span class="px-4 py-2 bg-yellow-200 text-yellow-700 font-medium rounded-lg">
                En revisión
              </span>
            </div>
          </div>

        </div>
      </div>
    </div>
  </t:clientwrapper>
</t:masterpage>