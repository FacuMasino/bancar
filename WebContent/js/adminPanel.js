/* Funcionalidades AdminPanel.jsp */

/* Devuelve una promesa a la espera de que todos los
 * elementos <i data-lucide=""> sean reemplazados por
 * SVGs.
*/
const waitForLucideSVGs = async ()=> {
  return new Promise((resolve) => {
    const interval = setInterval(() => {
    // Obtiene todos los elementos con el atributo data-lucide
	  const nodes = document.querySelectorAll('[data-lucide]');
	  // Se verifica que cada nodo sea de tipo SVG
	  const allSVG = Array.from(nodes).every(node => node instanceof SVGElement);
	
	  if (allSVG) {
	    clearInterval(interval);
	    resolve();
	  }
    }, 100);
  });
}

const drawDonutChart = async (divId, clients, provinces) => {
	var options = {
	  series: clients,
	  chart: {
	  width: 400,
	  type: 'pie',
	},
	labels: provinces,
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
	
	// No se dibuja el chart hasta que todos los SVGs de Lucide se hayan 
	// renderizado, asi ApexCharts calcula correctamente el ancho maximo disponible
	await waitForLucideSVGs();
	
	var chart = new ApexCharts(document.querySelector("#chartDonut"), options);
	chart.render();
}

const drawBarChart = async (divId, loans, transfers, dates) => {
	var options = {
	        series: [{
	        name: 'Prestamos Otorgados',
	        data: loans
	      }, {
	        name: 'Transferencias Realizadas',
	        data: transfers
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
	        categories: dates
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

	// No se dibuja el chart hasta que todos los SVGs de Lucide se hayan 
	// renderizado, asi ApexCharts calcula correctamente el ancho maximo disponible
	await waitForLucideSVGs();
	
	var chart = new ApexCharts(document.querySelector("#"+divId), options);
	chart.render();
}