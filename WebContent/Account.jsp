<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<t:masterpage title="Mi Cuenta" customNavbar="true">
  <t:clientwrapper activeMenuItem="accountMenu">
<div class="container mx-auto p-4">
	<div class="mb-2">
		<h1 class="font-bold text-xl">Bienvenido/a Ana Clara!</h1>
	</div>
	<div class="flex items-center justify-between mb-4">
		<h2 class="font-bold">Mi Cuenta</h2>
		<select class="bg-white w-64 font-bold font-sans select text-black select-bordered">
	        <option>Cta.10001</option>
	        <option>Cta.10002</option>
	      </select>
	</div>
	<div class=" flex flex-col p-4 mb-4 border border border-gray-300 rounded-lg  gap-6 w-full bg-white">
		<div class="flex flex-col p-2 gap-4  w-full  ">
			<div class="flex flex-row text-lg">
				<p>Cuenta Nro.<br><span class="font-bold">10001</span></p>
			</div>
			<div>
				<p class="font-bold text-4xl">$10.000,00</p>
			</div>
		</div>
	</div>
    <div class=" flex flex-col p-4 border border border-gray-300 rounded-lg  gap-6 w-full bg-white">
		<div>
		 <h2 class="font-bold">Últimos Movimientos</h2>
		</div>
     	<div>
			<div class="overflow-x-auto mb-4">
				<table class="table w-full">
				<thead>
					<tr>
					<th class="bg-gray-200 text-gray-700">Fecha</th>
					<th class="bg-gray-200 text-gray-700">Descripción</th>
					<th class="bg-gray-200 text-gray-700">Monto</th>
					</tr>
				</thead>
				<tbody>
					<tr>
					<td>01/10/2024</td>
					<td>Depósito de nómina</td>
					<td class="text-green-600 font-semibold">+$1,500.00</td>
					</tr>
					<tr class="hover">
					<td>03/10/2024</td>
					<td>Pago de servicios</td>
					<td class="text-red-600 font-semibold">-$200.00</td>
					</tr>
					<tr>
					<td>05/10/2024</td>
					<td>Transferencia recibida</td>
					<td class="text-green-600 font-semibold">+$300.00</td>
					</tr>
					<tr class="hover">
					<td>07/10/2024</td>
					<td>Pago tarjeta de crédito</td>
					<td class="text-red-600 font-semibold">-$500.00</td>
					</tr>
				</tbody>
				</table>
			</div>
			<div class="flex justify-end">
				<div class="join">
					<button class="join-item btn">1</button>
					<button class="join-item btn btn-active">2</button>
					<button class="join-item btn">3</button>
					<button class="join-item btn">4</button>
				</div>  
			</div>
		</div>
	</div>
	<div class="flex justify-end">
		<div role="alert" class="alert alert-success mt-20 w-80">
			<svg
			  xmlns="http://www.w3.org/2000/svg"
			  class="h-6 w-6 shrink-0 stroke-current"
			  fill="none"
			  viewBox="0 0 24 24">
			  <path
			    stroke-linecap="round"
			    stroke-linejoin="round"
			    stroke-width="2"
			    d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
			</svg>
 			<span>Transferencia realizada con éxito!</span>
		</div>
	</div>  
</div>
  </t:clientwrapper>
</t:masterpage>