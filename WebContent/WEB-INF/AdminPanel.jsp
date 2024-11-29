<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="clientsQty"  value="${requestScope.clientsQty != null ? requestScope.clientsQty : 0}" />
<c:set var="approvedLoansQty"  value="${requestScope.approvedLoansQty != null ? requestScope.approvedLoansQty : 0}" />
<c:set var="totalPendingAmount"  value="${requestScope.totalPendingAmount != null ? requestScope.totalPendingAmount : 0}" />


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
          <p class="text-green-600 text-xl font-bold">$10.950.324,90 (WIP)</p>
        </div>
        <div class="bg-white rounded rounded-lg flex flex-col justify-between gap-2.5 px-8 py-6">
          <div class="flex justify-between">
            <p>Préstamos Activos</p>
            <i data-lucide="credit-card"></i>
          </div>
          <p class="text-red-600 text-xl font-bold">${approvedLoansQty}</p>
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
      </div>
    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/adminPanel.js"></script>
    <script>
      	drawBarChart("chartColumns");
    	drawDonutChart("chartDonut");
    </script>
  </t:adminwrapper>
</t:masterpage>
