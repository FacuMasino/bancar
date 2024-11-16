<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<t:masterpage title="Admin - Pr�stamos" customNavbar="true">
  <t:adminwrapper activeMenuItem="adminLoansMenu">
    <div class="p-4">
      <h1 class="text-xl font-normal mb-6">
        Gesti�n de Pr�stamos
      </h1>

      <!-- Pr�stamos en revisi�n -->
      <form id="pendingLoansForm" method="post" action="AdminLoansServlet" class="bg-white rounded-lg shadow-sm mb-6">
        <div class="p-4">
          <h2 class="text-base font-medium mb-4">Pr�stamos en revisi�n</h2>
          <div class="overflow-x-auto">
            <input type="hidden" id="selectedLoanId" name="selectedLoanId" /> 
            <table class="w-full">
              <thead>
                <tr class="text-sm border-b border-gray-200">
                  <th class="text-left py-2 font-normal">ID</th>
                  <th class="text-left py-2 font-normal">DNI</th>
                  <th class="text-left py-2 font-normal">Nombre y Apellido</th>
                  <th class="text-left py-2 font-normal">Tipo de pr�stamo</th>
                  <th class="text-left py-2 font-normal">Monto Solicitado</th>
                  <th class="text-left py-2 font-normal">Cuotas</th>
                  <th class="text-left py-2 font-normal">Acciones</th>
                </tr>
              </thead>
              <tbody class="text-sm">
                <tr class="border-b border-gray-100">
                  <td class="py-3">01</td>
                  <td class="py-3">38000000</td>
                  <td class="py-3">Bianchini Gonzalo</td>
                  <td class="py-3">Personal</td>
                  <td class="py-3">$ 50.000</td>
                  <td class="py-3">6</td>
                  <td class="py-3">
                    <div class="flex gap-2">
                      <button type="submit" class="p-1.5 bg-green-500 text-white rounded-md"
                        onclick="setSelectedLoan('1')" name="action" value="approve">
                        <i data-lucide="check"></i>
                      </button>
                      <button type="submit" class="p-1.5 bg-red-500 text-white rounded-md"
                        onclick="setSelectedLoan('1')" name="action" value="reject">
                        <i data-lucide="x"></i>
                      </button>
                    </div>
                  </td>
                </tr>
                <tr class="border-b border-gray-100">
                  <td class="py-3">02</td>
                  <td class="py-3">36000000</td>
                  <td class="py-3">Bertello Ana Clara</td>
                  <td class="py-3">Hipotecario</td>
                  <td class="py-3">$ 1.000.000</td>
                  <td class="py-3">48</td>
                  <td class="py-3">
                    <div class="flex gap-2">
                      <button type="submit" class="p-1.5 bg-green-500 text-white rounded-md"
                        onclick="setSelectedLoan('2')" name="action" value="approve">
                        <i data-lucide="check"></i>
                      </button>
                      <button type="submit" class="p-1.5 bg-red-500 text-white rounded-md"
                        onclick="setSelectedLoan('2')" name="action" value="reject">
                        <i data-lucide="x"></i>
                      </button>
                    </div>
                  </td>
                </tr>
                <tr class="border-b border-gray-100">
                  <td class="py-3">03</td>
                  <td class="py-3">39000000</td>
                  <td class="py-3">Malvicino Maximiliano</td>
                  <td class="py-3">Automotriz</td>
                  <td class="py-3">$ 300.000</td>
                  <td class="py-3">24</td>
                  <td class="py-3">
                    <div class="flex gap-2">
                      <button type="submit" class="p-1.5 bg-green-500 text-white rounded-md"
                        onclick="setSelectedLoan('3')" name="action" value="approve">
                        <i data-lucide="check"></i>
                      </button>
                      <button type="submit" class="p-1.5 bg-red-500 text-white rounded-md"
                        onclick="setSelectedLoan('3')" name="action" value="reject">
                        <i data-lucide="x"></i>
                      </button>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </form>

      <!-- Pr�stamos activos -->
      <div class="bg-white rounded-lg shadow-sm mb-6">
        <div class="p-4">
          <h2 class="text-base font-medium mb-4">Pr�stamos Activos</h2>
          <div class="overflow-x-auto">
            <table class="w-full">
              <thead>
                <tr class="text-sm border-b border-gray-200">
                  <th class="text-left py-2 font-normal">ID</th>
                  <th class="text-left py-2 font-normal">DNI</th>
                  <th class="text-left py-2 font-normal">Nombre y Apellido</th>
                  <th class="text-left py-2 font-normal">Tipo de pr�stamo</th>
                  <th class="text-left py-2 font-normal">Monto Solicitado</th>
                  <th class="text-left py-2 font-normal">Deuda Pendiente</th>
                </tr>
              </thead>
              <tbody class="text-sm">
                <tr class="border-b border-gray-100">
                  <td class="py-3">01</td>
                  <td class="py-3">39000000</td>
                  <td class="py-3">Masino Facundo</td>
                  <td class="py-3">Personal</td>
                  <td class="py-3">$ 50.000</td>
                  <td class="py-3">$ 50.000</td>
                </tr>
                <tr class="border-b border-gray-100">
                  <td class="py-3">02</td>
                  <td class="py-3">38000000</td>
                  <td class="py-3">Romero Juan</td>
                  <td class="py-3">Hipotecario</td>
                  <td class="py-3">$ 1.000.000</td>
                  <td class="py-3">$ 950.000</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>

      <!-- Resumen de pr�stamos -->
      <div class="bg-white rounded-lg shadow-sm">
        <div class="p-4">
          <h2 class="text-base font-medium mb-4">Resumen de Pr�stamos</h2>
          <div class="space-y-4">
            <div>
              <p class="text-sm text-gray-500">Monto total solicitado (pr�stamos pendientes):</p>
              <p class="text-lg text-blue-600 font-medium">$ 1.350.000</p>
            </div>
            <div>
              <p class="text-sm text-gray-500">Monto total adeudado (pr�stamos activos):</p>
              <p class="text-lg text-red-600 font-medium">$ 1.000.000</p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <script>
    	const setSelectedLoan = (id) => {
    		const selectedLoan = document.getElementById("selectedLoanId");
    		selectedLoan.value = id;
    		document.getElementById("pendingLoansForm").submit();
    	}
    </script>
  </t:adminwrapper>
</t:masterpage>