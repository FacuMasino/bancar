<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Necesario para el formato de números como moneda -->
<fmt:setLocale value="es_AR"/>


<c:set var="clientsQty"  value="${requestScope.clientsQty != null ? requestScope.clientsQty : 0}" />
<c:set var="approvedLoansCount"  value="${requestScope.approvedLoansCount != null ? requestScope.approvedLoansCount : 0}" />
<c:set var="overdueLoansCount"  value="${requestScope.overdueLoansCount != null ? requestScope.overdueLoansCount : 0}" />
<c:set var="totalPendingAmount"  value="${requestScope.totalPendingAmount != null ? requestScope.totalPendingAmount : 0}" />
<c:set var="totalFunds"  value="${requestScope.totalFunds != null ? requestScope.totalFunds : 0}" />
<c:set var="defaultRate"  value="${requestScope.defaultRate != null ? requestScope.defaultRate : 0}" />
<c:set var="profitsEarned"  value="${requestScope.profitsEarned != null ? requestScope.profitsEarned : 0}" />
<c:set var="profitsToEarn"  value="${requestScope.profitsToEarn != null ? requestScope.profitsToEarn : 0}" />
<c:set var="provinces"  value="${requestScope.provinces != null ? requestScope.provinces : 0}" />
<c:set var="provinceClients"  value="${requestScope.provinceClients != null ? requestScope.provinceClients : 0}" />
<c:set var="loansGivenAmount"  value="${requestScope.loansGivenAmount != null ? requestScope.loansGivenAmount : 0}" />
<c:set var="periods"  value="${requestScope.periods != null ? requestScope.periods : 0}" />

<t:masterpage title="Admin - Panel" customNavbar="true">
  <t:adminwrapper activeMenuItem="adminPanelMenu">
    <script async src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
    <div class="container flex flex-col gap-6 mx-auto my-6 px-2">
      <p class="font-bold text-2xl mb-6">¡Bienvenido/a ${user.username}!</p>
      <div class="grid grid-cols-2 lg:grid-cols-4 gap-2.5">
        <div class="bg-white rounded rounded-lg flex flex-col justify-between gap-2.5 px-8 py-6">
          <div class="flex justify-between">
            <p>Fondos Totales</p>
            <i data-lucide="landmark"></i>
          </div>
          <p class="text-green-600 text-xl font-bold"><fmt:formatNumber value="${totalFunds}" type="currency"/></p>
        </div>
        <div class="bg-white rounded rounded-lg flex flex-col justify-between gap-2.5 px-8 py-6">
          <div class="flex justify-between">
            <p>Préstamos Activos</p>
            <i data-lucide="credit-card"></i>
          </div>
          <p class="text-orange-500  text-xl font-bold text-center">${approvedLoansCount}</p>
        </div>
        <div class="bg-white rounded rounded-lg flex flex-col justify-between gap-2.5 px-8 py-6">
          <div class="flex justify-between ">
            <p>Clientes</p>
            <i data-lucide="user"></i>
          </div>
          <p class="text-green-600 text-xl font-bold text-center">${clientsQty}</p>
        </div>
        <div class="bg-white rounded rounded-lg flex flex-col justify-between gap-2.5 px-8 py-6">
          <div class="flex justify-between">
            <p>Deuda Clientes</p>
            <i data-lucide="user"></i>
          </div>
          <p class="text-red-600 text-xl font-bold"> <fmt:formatNumber value="${totalPendingAmount}" type="currency"/></p>
        </div>
      </div>
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-2.5">
        <!-- TASA DE MOROSIDAD -->
        <div class="flex flex-col bg-white py-6 px-8 rounded">
          <div class="flex justify-between">
            <span class="font-semibold">Tasa de morosidad</span>
            <i data-lucide="info"></i>
          </div>
          <div class="flex justify-between items-center">
            <div class="space-y-1">
              <p class="text-xs text-muted">Préstamos con cuotas vencidas</p>
              <p class="text-xl font-bold">${overdueLoansCount}</p>
            </div>
            <div class="self-end text-2xl font-bold text-red-500">${defaultRate}%</div>
          </div>
          <div class="mt-4 h-1 w-full bg-red-200 rounded-full overflow-hidden">
            <div class="h-full bg-red-500 w-[${defaultRate}%]"></div>
          </div>
          <span class="mt-2 text-xs text-muted text-end">
            ${overdueLoansCount} de ${approvedLoansCount} préstamos vigentes
          </span>
        </div>
        <!-- BENEFICIOS -->
        <div class="flex flex-col bg-white py-6 px-8 rounded">
          <div class="flex justify-between">
            <span class="font-semibold">Beneficios de Préstamos</span>
            <i data-lucide="dollar-sign"></i>
          </div>
          <div class="flex flex-col gap-4">
            <div class="space-y-1">
              <p class="text-xs text-muted">Intereses de cuotas pagadas</p>
              <p class="text-xl font-bold text-green-600">
                <fmt:formatNumber value="${profitsEarned}" type="currency"/>
              </p>
            </div>
            <div class="space-y-1">
              <p class="text-xs text-muted">Intereses de cuotas por pagar</p>
              <div class="flex items-center gap-2">
                <p class="text-xl font-bold text-red-500">
                  <fmt:formatNumber value="${profitsToEarn}" type="currency"/>
                </p>
                <i data-lucide="trending-up"></i>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="grid gap-6 xl:grid-cols-12">
        <div class="xl:col-span-7">
          <div class="flex flex-col gap-4 rounded-lg p-8 bg-white">
            <p class="font-semibold text-center text-2xl">Flujo de dinero</p>
            <div class="flex justify-between">
              <p class="font-semibold">Período</p>
              <form method="get" action="Admin" class="flex justify-between gap-4">
                <div class="flex justify-around gap-4">
                  <c:set var="startDate" value="${param.startDate != null ? param.startDate : defaultStartDate}"/>
                  <c:set var="endDate" value="${param.endDate != null ? param.endDate : defaultEndDate}"/>
                  <input type="date" name="startDate" value="${startDate}">
                  <input type="date" name="endDate" value="${endDate}">
                </div>
                <button type="submit" class="btn btn-primary"
                  name="action" value="updateDateRange">Aplicar filtro</button>
              </form>
            </div>
              <div id="chartColumns"></div>
          </div>
        </div>
        <div class="xl:col-span-5">
          <div class="bg-white rounded-lg flex flex-col gap-4 p-8 h-[100%]">
            <p class="font-semibold text-center text-2xl">Clientes por provincia</p>
            <div class="flex flex-col items-center grow">
              <div id="chartDonut" class="flex items-center w-[100%] h-[100%]"></div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/adminPanel.js"></script>
    <script>
    	let provinces = ${provinces};
    	let provinceClients = ${provinceClients};
    	
    	drawDonutChart("chartDonut", provinceClients, provinces);
    	
    	let loansGivenAmount = ${loansGivenAmount};
    	let periods = ${periods};
    	
    	let transfers = ${transfersDoneAmount};
    	
      	drawBarChart("chartColumns", loansGivenAmount, transfers, periods);
    </script>
  </t:adminwrapper>
</t:masterpage>
