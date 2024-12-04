<%-- Este JSP Tag envuelve al resto de páginas que 
    Pertenezcan al Área de Cliente. Lo hacemos así
    para poder utilizar el componente Drawer de DaisyUI
    que funciona como Navbar y Menú lateral --%>
<%@tag description="Client Wrapper with Navbar + Sidebar Menu"
  pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="username" value="${sessionScope.user.username}" />
<%@attribute name="activeMenuItem" required="true"%>

<div class="drawer lg:drawer-open">
  <input id="client-drawer" type="checkbox" class="drawer-toggle" />
  <div class="drawer-content overflow-y-hidden">
    <div class="navbar bg-white drop-shadow-sm">
      <div class="container-sm justify-items-end w-full">
        <div class="flex-1">
          <label for="client-drawer"
            class="btn btn-ghost drawer-button lg:hidden"> <svg
              xmlns="http://www.w3.org/2000/svg" fill="none"
              viewBox="0 0 24 24"
              class="inline-block h-5 w-5 stroke-current">  
              <path stroke-linecap="round" stroke-linejoin="round"
                stroke-width="2" d="M4 6h16M4 12h16M4 18h16"></path>
            </svg>
          </label> <span
            class="btn btn-ghost font-normal text-xl text-red-600 gap-0 lg:hidden">
            BANC<span class="font-bold">AR</span>
          </span>
        </div>
        <form method="post"
          action="${pageContext.request.contextPath}/Login"
          class="flex-none">
          <ul class="menu menu-horizontal px-1 gap-3">
            <li>
              <a id="myProfileMenu" 
                href="${pageContext.request.contextPath}/Client?action=viewProfile"
                class="btn btn-ghost">
                    Mi Perfil
              </a>
            </li>
            <li>
              <details>
                <summary class="flex">
                  <div class="bg-gray-100 rounded-full p-1">
                    <i data-lucide="user"></i>
                  </div>
                  <div class="flex flex-col">
                    <span>Usuario</span>
                    <span class="font-bold">${username}</span>
                  </div>
                </summary>
                <ul class="bg-white shadow !mt-5 rounded p-2 w-full">
                  <li>
                    <button value="logout" name="action">
                      Salir
                    </button>
                  </li>
                </ul>
              </details>
            </li>
          </ul>
        </form>
      </div>
    </div>
    <!-- Drawer Content -->
    <jsp:doBody />
  </div>
  <div class="drawer-side border-e text-black">
    <label for="client-drawer" aria-label="close sidebar"
      class="drawer-overlay"></label>
    <div class="bg-white min-h-full w-[18rem] p-4 drop-shadow-sm">
      <!-- Sidebar content here -->
      <div class="flex mb-8 pt-2 ps-2">
        <span
          class="btn btn-ghost font-normal text-xl text-red-600 gap-0">BANC<span
          class="font-bold">AR</span></span>
      </div>
      <!-- MENU -->
      <ul class="menu text-lg gap-4">
        <li><a href="${pageContext.request.contextPath}/Client"
          id="accountMenu"> <i data-lucide="layout-dashboard"></i>
            Mi Cuenta
        </a></li>
        <li><a
          href="${pageContext.request.contextPath}/Client/Transfer"
          id="transferMenu"> <i data-lucide="arrow-left-right"></i>
            Transferir
        </a></li>
        <li><a
          href="${pageContext.request.contextPath}/Client/Loans"
          id="loansMenu"> <i data-lucide="wallet-minimal"></i>
            Préstamos
        </a></li>
      </ul>
    </div>
  </div>
</div>
<script type="text/javascript">
	setActiveMenuItem('<%=activeMenuItem%>');
</script>