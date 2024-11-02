<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<t:masterpage title="Pagar Préstamos" customNavbar="true">
  <t:clientwrapper activeMenuItem="loansMenu">
  	<form method="get" action="#" class="container flex flex-col gap-4 mx-auto p-4 max-w-7xl mb-8">
    <div class="flex justify-between">
    			<!-- FLEX FLEX-COL GRAL -->
				<div class="flex flex-col gap-6 w-full">
					<!-- 1ER FLEX -->
					<div class="flex">
						<p class="font-semibold text-xl">Pagar Préstamo</p>
					</div>
					<!-- 2DO FLEX FLEX-COL -->
					<div class="flex flex-col gap-6 p-8 bg-white rounded">
						<div class="flex justify-between ">
							<p class="font-semibold text-xl">Préstamo Personal</p>
							<div class="flex flex-col">
								<p class="text-xl text-slate-600">Monto total del préstamo</p>
								<p class="font-semibold text-xl">$120.000</p>
							</div>
						</div>
						<div class="flex flex-col">
							<div class="flex">
								<div class="flex flex-col justify-between w-full">
									<p class="text-slate-600">Cuota a pagar</p>
									<p class="text-black">$5000</p>
								</div>
								<div class="flex flex-col justify-between w-full">
									<p class="text-slate-600">Cuota a pagar</p>
									<p class="text-black">23 de 24</p>
								</div>
							</div>
						</div>
						<div class="flex flex-col">
							<div class="flex">
								<div class="flex flex-col justify-between w-full">
									<p class="text-slate-600">Vencimiento</p>
									<p class="text-black">26/11/2024</p>
								</div>
								<div class="flex flex-col  justify-between w-full">
									<p class="text-slate-600">Deuda pendiente</p>
									<p class="text-black">$115.000</p>
								</div>
							</div>
						</div>
					</div>
					<!-- 3ER FLEX FLEX-COL -->
					<div class="flex flex-col gap-6 w-full">
						<p class="text-xl text-black text-semibold gap-6">Selecciona una cuenta debito</p>
						<div class="flex flex-col gap-6 p-8 bg-white w-full">
							<select class="select select-bordered w-full max-w-xs">
								<option disabled selected></option>
							</select>
						</div>
						<button class="btn btn-primary">Confirma Pago</button>
					</div>
				</div>
	</div>
	</form>
  </t:clientwrapper>
</t:masterpage>