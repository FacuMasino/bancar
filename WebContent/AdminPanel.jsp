<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<t:masterpage title="Admin - Panel" customNavbar="true">
  <t:adminwrapper activeMenuItem="adminPanelMenu">
    <script async src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
    <div class="container flex flex-col gap-6 mx-auto my-6 px-2">
      <p class="font-bold text-2xl mb-6">Bienvenido/a!</p>
      <div class="grid grid-cols-2 lg:grid-cols-4 gap-2.5">
        <div class="bg-white rounded rounded-lg flex flex-col justify-between gap-2.5 px-8 py-6">
          <div class="flex justify-between">
            <p>Fondos Totales</p>
            <i data-lucide="landmark"></i>
          </div>
          <p class="text-green-600 text-xl font-bold">$10.950.324,90</p>
        </div>
        <div class="bg-white rounded rounded-lg flex flex-col justify-between gap-2.5 px-8 py-6">
          <div class="flex justify-between">
            <p>Préstamos Activos</p>
            <i data-lucide="credit-card"></i>
          </div>
          <p class="text-red-600 text-xl font-bold">100</p>
        </div>
        <div class="bg-white rounded rounded-lg flex flex-col justify-between gap-2.5 px-8 py-6">
          <div class="flex justify-between">
            <p>Clientes</p>
            <i data-lucide="user"></i>
          </div>
          <p class="text-green-600 text-xl font-bold">100</p>
        </div>
        <div class="bg-white rounded rounded-lg flex flex-col justify-between gap-2.5 px-8 py-6">
          <div class="flex justify-between">
            <p>Deuda Clientes</p>
            <i data-lucide="user"></i>
          </div>
          <p class="text-red-600 text-xl font-bold">$3.000.000</p>
        </div>
      </div>
      <div class="grid gap-6 xl:grid-cols-12">
        <div class="xl:col-span-7">
          <div class="flex flex-col gap-4 rounded-lg p-8 bg-white">
            <p class="font-semibold">Flujo de dinero</p>
            <div class="flex justify-between">
              <p class="font-semibold">Período</p>
              <form class="flex justify-between">
                <div class="flex justify-around">
                  <input type="date"> <input type="date">
                </div>
                <button type="submit" class="btn btn-primary">Aplicar
                  filtro</button>
              </form>
            </div>
              <div id="chartColumns"></div>
          </div>
        </div>
        <div class="xl:col-span-5">
          <div class="bg-white rounded-lg flex flex-col gap-4 p-8">
            <p class="font-semibold">Clientes por provincia</p>
            <div class="flex flex-col items-center">
              <div id="chartDonut"></div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <script defer>
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
    <script defer>
      var options = {
              series: [25, 25, 50],
              chart: {
              width: 400,
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
  </t:adminwrapper>
</t:masterpage>
