<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<t:masterpage title="Admin - Panel" customNavbar="true">
  <t:adminwrapper activeMenuItem="adminPanelMenu">
    <div class="container md:max-w-[1600px] mx-auto my-6 px-2">
    	<p class="font-bold text-xl mb-6">Bienvenido/a!</p>
    </div>
    <div class="flex justify-around">
    	<div class="bg-white rounded rounded-lg flex flex-col gap-4 p-8 w-96">
    		<div class="flex justify-between">
    			<p>Fondos Totales</p>
    			<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-landmark"><line x1="3" x2="21" y1="22" y2="22"/><line x1="6" x2="6" y1="18" y2="11"/><line x1="10" x2="10" y1="18" y2="11"/><line x1="14" x2="14" y1="18" y2="11"/><line x1="18" x2="18" y1="18" y2="11"/><polygon points="12 2 20 7 4 7"/></svg>
    		</div>
    		<p class="text-green-600 text-xl font-bold">$10.950.324,90</p>
    	</div>
    	<div class="bg-white rounded rounded-lg flex flex-col gap-4 p-8 w-96">
    		<div class="flex justify-between">
				<p>Préstamos Activos</p>
    			<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-credit-card"><rect width="20" height="14" x="2" y="5" rx="2"/><line x1="2" x2="22" y1="10" y2="10"/></svg>    		
    		</div>
    		<p class="text-red-600 text-xl font-bold">100</p>
    	</div>
    	<div class="bg-white rounded rounded-lg flex flex-col gap-4 p-8 w-96">
    		<div class="flex justify-between">
    			<p>Clientes</p>
    			<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-user"><path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
    		</div>
    		<p class="text-green-600 text-xl font-bold">100</p>
    	</div>
    	<div class="bg-white rounded rounded-lg flex flex-col gap-4 p-8 w-96">
    		<div class="flex justify-between">
    			<p>Deuda Clientes</p>
    			<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-credit-card"><rect width="20" height="14" x="2" y="5" rx="2"/><line x1="2" x2="22" y1="10" y2="10"/></svg>    		
    		</div>
    		<p class="text-red-600 text-xl font-bold">$3.000.000</p>
    	</div>
    </div>
    <div class="flex mt-10 justify-around">
    	<div class="w-7/12 bg-white rounded rounded-lg flex flex-col gap-4 p-8">
    		<p class="font-semibold">Flujo de dinero</p>
    		<div class="flex justify-between">
    			<div class="flex items-center w-full justify-between">
    				<p class="font-semibold">Período</p>
    				<form class="w-3/4">
    					<div class="flex justify-between">
    						<div class="w-2/3 flex justify-around">
		    					<input type="date">
			    				<input type="date">
    						</div>
    						<div>
								<button type="submit" class="btn btn-primary">Aplicar filtro</button>  			
    						</div>
    					</div>
    					
    				</form>
    				
    			</div>
    			
    		</div>
    		<div id="chartColumns" ></div>
    	</div>
    	<div class="w-2/5 bg-white rounded rounded-lg flex flex-col gap-4 p-8">
    		<p class="font-semibold">Clientes por provincia</p>
    		<div id="chartDonut"></div>
    	</div>
    </div>
    <div id="chartColumns"></div>
  </t:adminwrapper>
</t:masterpage>
<script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
<script>
var options = {
        series: [{
        name: 'Prestamos Otorgados',
        data: [44, 55, 57, 56, 61, 58, 63, 60, 66]
      }, {
        name: 'Transferencias Realizadas',
        data: [76, 85, 101, 98, 87, 105, 91, 114, 94]
      }],
        chart: {
        type: 'bar',
        height: 350
      },
      plotOptions: {
        bar: {
          horizontal: false,
          columnWidth: '55%',
          endingShape: 'rounded'
        },
      },
      dataLabels: {
        enabled: false
      },
      stroke: {
        show: true,
        width: 2,
        colors: ['transparent']
      },
      xaxis: {
        categories: ['Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct'],
      },
      yaxis: {
        title: {
          text: '$ (Miles)'
        }
      },
      fill: {
        opacity: 1
      },
      tooltip: {
        y: {
          formatter: function (val) {
            return "$ " + val + " mil"
          }
        }
      }
      };

      var chart = new ApexCharts(document.querySelector("#chartColumns"), options);
      chart.render();
</script>
<script>
var options = {
        series: [25, 25, 50],
        chart: {
        width: 600,
        type: 'pie',
      },
      labels: ['Córdoba', 'Mendoza', 'Buenos Aires'],
      responsive: [{
        breakpoint: 480,
        options: {
          chart: {
            width: 200
          },
          legend: {
            position: 'bottom'
          }
        }
      }]
      };

      var chart = new ApexCharts(document.querySelector("#chartDonut"), options);
      chart.render();
</script>
