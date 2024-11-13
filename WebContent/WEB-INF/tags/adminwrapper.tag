<%-- Este JSP Tag envuelve al resto de páginas que 
    Pertenezcan al Área de Cliente. Lo hacemos así
    para poder utilizar el componente Drawer de DaisyUI
    que funciona como Navbar y Menú lateral --%>
<%@tag description="Admin Wrapper with Navbar + Sidebar Menu"
  pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@attribute name="activeMenuItem" required="true"%>
<div class="drawer lg:drawer-open">
  <input id="client-drawer" type="checkbox" class="drawer-toggle" />
  <div class="drawer-content overflow-y-hidden">
    <div class="navbar bg-red-600 drop-shadow">
      <div class="container-sm justify-items-end w-full">
        <div class="flex-1">
          <label for="client-drawer" class="btn btn-ghost text-white drawer-button lg:hidden">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
              class="inline-block h-5 w-5 stroke-current">  
              <path stroke-linecap="round" stroke-linejoin="round"
                stroke-width="2" d="M4 6h16M4 12h16M4 18h16"></path>
            </svg>
          </label> 
          <span class="btn btn-ghost text-xl text-white gap-0 font-bold lg:hidden">
            ADMINISTRACIÓN
          </span>
        </div>
        <div class="flex-none">
          <ul class="menu text-white menu-horizontal px-1 gap-3">
            <li>
              <a href="#" class="btn btn-ghost">
                <i data-lucide="log-out"></i>
                Salir
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <!-- Drawer Content -->
    <jsp:doBody />
  </div>
  <div class="drawer-side border-e text-black">
    <label for="client-drawer" aria-label="close sidebar" class="drawer-overlay"></label>
    <div class="bg-white min-h-full w-[18rem] p-4 drop-shadow-sm">
      <!-- Sidebar content here -->
      <div class="flex mb-8 pt-2 ps-2">
        <span
            class="btn btn-ghost text-xl text-red-600 gap-0 font-bold">ADMINISTRACIÓN</span>
      </div>
      <!-- MENU -->
      <ul class="menu text-lg gap-4">
        <li>
          <a href="AdminPanel.jsp" id="adminPanelMenu">
            <i data-lucide="layout-dashboard"></i>
            Inicio
          </a>  
        </li>
        <li>
          <a href="AdminClients" id="adminClientsMenu">
            <i data-lucide="users"></i>
            Clientes
          </a>
        </li>
        <li>
          <a href="AdminLoans.jsp" id="adminLoansMenu"> 
            <i data-lucide="wallet-minimal"></i>
             Préstamos
          </a>
        </li>
      </ul>
    </div>
  </div>
</div>
<script type="text/javascript">
	setActiveMenuItem('<%=activeMenuItem%>');
</script>