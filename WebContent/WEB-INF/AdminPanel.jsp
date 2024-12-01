<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="clientsQty"  value="${requestScope.clientsQty != null ? requestScope.clientsQty : 0}" />
<c:set var="approvedLoansCount"  value="${requestScope.approvedLoansCount != null ? requestScope.approvedLoansCount : 0}" />
<c:set var="overdueLoansCount"  value="${requestScope.overdueLoansCount != null ? requestScope.overdueLoansCount : 0}" />
<c:set var="totalPendingAmount"  value="${requestScope.totalPendingAmount != null ? requestScope.totalPendingAmount : 0}" />
<c:set var="totalFunds"  value="${requestScope.totalFunds != null ? requestScope.totalFunds : 0}" />
<c:set var="defaultRate"  value="${requestScope.defaultRate != null ? requestScope.defaultRate : 0}" />
<c:set var="profitsEarned"  value="${requestScope.profitsEarned != null ? requestScope.profitsEarned : 0}" />
<c:set var="profitsToEarn"  value="${requestScope.profitsToEarn != null ? requestScope.profitsToEarn : 0}" />

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
          <p class="text-green-600 text-xl font-bold">$${totalFunds}</p>
        </div>
        <div class="bg-white rounded rounded-lg flex flex-col justify-between gap-2.5 px-8 py-6">
          <div class="flex justify-between">
            <p>Préstamos Activos</p>
            <i data-lucide="credit-card"></i>
          </div>
          <p class="text-red-600 text-xl font-bold">${approvedLoansCount}</p>
        </div>
        <div class="bg-white rounded rounded-lg flex flex-col justify-between gap-2.5 px-8 py-6">
          <div class="flex justify-between">
            <p>Clientes</p>
            <i data-lucide="user"></i>
          </div>
          <p class="text-green-600 text-xl font-bold">${clientsQty}</p>
        </div>
        <div class="bg-white rounded rounded-lg flex flex-col justify-between gap-2.5 px-8 py-6">
          <div class="flex justify-between">
            <p>Deuda Clientes</p>
            <i data-lucide="user"></i>
          </div>
          <p class="text-red-600 text-xl font-bold">$ ${totalPendingAmount}</p>
        </div>
      </div>
      <div class="grid gap-6 xl:grid-cols-12">
        <div class="xl:col-span-7">
          <div class="flex flex-col gap-4 rounded-lg p-8 bg-white">
            <p class="font-semibold">Flujo de dinero</p>
            <div class="flex justify-between">
              <p class="font-semibold">Período</p>
              <form method="get" action="AdminReportsServlet" class="flex justify-between gap-4">
                <div class="flex justify-around gap-4">
                  <input type="date" name="startDate">
                  <input type="date" name="endDate">
                </div>
                <button type="submit" class="btn btn-primary"
                  name="action" value="updateDateRange">Aplicar filtro</button>
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
        <div class="xl:col-span-5">
          <div class="bg-white rounded-lg flex flex-col gap-4 p-8">
            <p class="font-semibold">Tasa de Morosidad : Préstamos con cuotas vencidas / Préstamos vigentes</p>
            <h3 class="font-semibold">Préstamos con cuotas vencidas: ${overdueLoansCount}</h3>
            <h3 class="font-semibold">Préstamos vigentes: ${approvedLoansCount}</h3>
            <div class="flex flex-col items-center">
              <span class="text-xl font-bold text-red-600">${defaultRate}% </span>
            </div>
          </div>
        </div>
        <div class="xl:col-span-5">
          <div class="bg-white rounded-lg flex flex-col gap-4 p-8">
            <h3 class="text-l font-bold">Beneficios de Préstamos (intereses de cuotas pagadas):</h3> 
            <h3 class="text-l font-bold text-green-600">$ ${profitsEarned}</h3>
            <h3 class="text-l font-bold">Beneficio de Préstamos futuro (intereses de cuotas por pagar):</h3> 
            <h3 class="text-l font-bold text-red-600">$ ${profitsToEarn}</h3>
          </div>
        </div>
      </div>
    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/adminPanel.js"></script>
    <script>
    	// Cuando reciba los atributos debe quedar así:
    	// let provinces = ${provinces};
    	// let provinceClients = ${provinceClients};
    	
    	let provinces = ['Córdoba', 'Mendoza', 'Buenos Aires'];
    	let provinceClients = [25, 30, 50];
    	
    	drawDonutChart("chartDonut", provinceClients, provinces);
    	
    	// Cuando reciba los atributos debe quedar así:
    	// let loans = ${chartLoans};
    	// let transfers = ${chartTransfers};
    	// let dates = ${chartDates};
    	
    	let loans = [44, 55, 57, 56, 61]; // No es cantidad, sino total de dinero
    	let transfers = [76, 85, 101, 98, 87];
    	let dates = ['Mar', 'Abr', 'May', 'Jun', 'Ago'];
    	
    	// El orden y la cantidad de datos deben coincidir en los 3 arrays
    	// en dates puede haber nombres de meses o números.
    	// En el caso de que el usuario seleccione un rango menor a 1 mes por ej
    	// 20 dias, entonces en dates tendrias ['1', '2', ...] y asi hasta 20 dias.
    	
      	drawBarChart("chartColumns", loans, transfers, dates);
    </script>
  </t:adminwrapper>
</t:masterpage>
