<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<t:masterpage title="Admin - Clientes" customNavbar="true">
  <t:adminwrapper activeMenuItem="adminClientsMenu">
    <form method="get" action="#" class="container flex flex-col gap-4 mx-auto p-4 max-w-7xl mb-8">
      <div class="flex justify-between">
        <h1 class="font-bold text-xl">Gestión de Clientes</h1>
        <div class="flex gap-2.5">
          <label class="input input-bordered flex items-center gap-2">
            <input type="text" class="grow" placeholder="Buscar cliente" name="searchInput" />
            <i data-lucide="search"></i>
          </label>
          <a href="AdminNewClient.jsp" class="btn btn-primary">
            Nuevo Cliente
          </a>
        </div>
      </div>
      <div class="flex flex-col bg-white p-2.5 rounded-xl drop-shadow-sm">
        <table class="table">
          <!-- head -->
          <thead>
            <tr>
              <th>ID</th>
              <th>DNI</th>
              <th>Nombre y Apellido</th>
              <th>Estado</th>
              <th>Cuentas</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            <!-- row 1 -->
            <tr class="hover">
              <th>1</th>
              <td>38000000</td>
              <td>Bianchini Gonzalo</td>
              <td>
                <span class="flex flex-col items-center w-fit px-2.5 rounded-full border border-green-600 text-green-600 font-semibold">
                  Activo
                </span>
              </td>
              <td>2</td>
              <td class="flex items-center">
                <div class="dropdown dropdown-end">
                  <div tabindex="0" role="button" class="p-0.5">
                    <i class="text-sm" data-lucide="ellipsis-vertical"></i>
                  </div>
                  <ul tabindex="0" class="dropdown-content menu bg-white rounded-box z-[1] w-52 p-2 border-slate-200 drop-shadow">
                    <li><a href="AdminClient.jsp?id=1">Ver cliente</a></li>
                    <li><a href="AdminEditClient.jsp?id=1">Editar cliente</a></li>
                    <li><a href="AdminClientAccounts.jsp?id=1">Gestionar cuentas</a></li>
                  </ul>
                </div>
              </td>
            </tr>
            <!-- row 2 -->
            <tr class="hover">
              <th>2</th>
              <td>36000000</td>
              <td>Bertello Ana Clara</td>
              <td>
                <span class="flex flex-col items-center w-fit px-2.5 rounded-full border border-green-600 text-green-600 font-semibold">
                  Activo
                </span>
              </td>
              <td>3</td>
              <td class="flex items-center">
                <div class="dropdown dropdown-end">
                  <div tabindex="0" role="button" class="p-0.5">
                    <i class="text-sm" data-lucide="ellipsis-vertical"></i>
                  </div>
                  <ul tabindex="0" class="dropdown-content menu bg-white rounded-box z-[1] w-52 p-2 border-slate-200 drop-shadow">
                    <li><a href="AdminClient.jsp?id=2">Ver cliente</a></li>
                    <li><a href="AdminEditClient.jsp?id=2">Editar cliente</a></li>
                    <li><a href="AdminClientAccounts.jsp?id=2">Gestionar cuentas</a></li>
                  </ul>
                </div>
              </td>
            </tr>
            <!-- row 3 -->
            <tr class="hover">
              <th>3</th>
              <td>39000000</td>
              <td>Malvicino Maximiliano</td>
              <td>
                <span class="flex flex-col items-center w-fit px-2.5 rounded-full border border-red-600 text-red-600 font-semibold">
                  Baja
                </span>
              </td>
              <td>1</td>
              <td class="flex items-center">
                <div class="dropdown dropdown-end">
                  <div tabindex="0" role="button" class="p-0.5">
                    <i class="text-sm" data-lucide="ellipsis-vertical"></i>
                  </div>
                  <ul tabindex="0" class="dropdown-content menu bg-white rounded-box z-[1] w-52 p-2 border-slate-200 drop-shadow">
                    <li><a href="AdminClient.jsp?id=3">Ver cliente</a></li>
                    <li><a href="AdminEditClient.jsp?id=3">Editar cliente</a></li>
                    <li><a href="AdminClientAccounts.jsp?id=3">Gestionar cuentas</a></li>
                  </ul>
                </div>
              </td>
            </tr>
            <!-- row 4 -->
            <tr class="hover">
              <th>4</th>
              <td>39000000</td>
              <td>Masino Facundo</td>
              <td>
                <span class="flex flex-col items-center w-fit px-2.5 rounded-full border border-green-600 text-green-600 font-semibold">
                  Activo
                </span>
              </td>
              <td>2</td>
              <td class="flex items-center">
                <div class="dropdown dropdown-end">
                  <div tabindex="0" role="button" class="p-0.5">
                    <i class="text-sm" data-lucide="ellipsis-vertical"></i>
                  </div>
                  <ul tabindex="0" class="dropdown-content menu bg-white rounded-box z-[1] w-52 p-2 border-slate-200 drop-shadow">
                    <li><a href="AdminClient.jsp?id=4">Ver cliente</a></li>
                    <li><a href="AdminEditClient.jsp?id=4">Editar cliente</a></li>
                    <li><a href="AdminClientAccounts.jsp?id=4">Gestionar cuentas</a></li>
                  </ul>
                </div>
              </td>
            </tr>
            <!-- row 4 -->
            <tr class="hover">
              <th>5</th>
              <td>40000000</td>
              <td>Romero Juan</td>
              <td>
                <span class="flex flex-col items-center w-fit px-2.5 rounded-full border border-green-600 text-green-600 font-semibold">
                  Activo
                </span>
              </td>
              <td>0</td>
              <td class="flex items-center">
                <div class="dropdown dropdown-end">
                  <div tabindex="0" role="button" class="p-0.5">
                    <i class="text-sm" data-lucide="ellipsis-vertical"></i>
                  </div>
                  <ul tabindex="0" class="dropdown-content menu bg-white rounded-box z-[1] w-52 p-2 border-slate-200 drop-shadow">
                    <li><a href="AdminClient.jsp?id=5">Ver cliente</a></li>
                    <li><a href="AdminEditClient.jsp?id=5">Editar cliente</a></li>
                    <li><a href="AdminClientAccounts.jsp?id=5">Gestionar cuentas</a></li>
                  </ul>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </form>
  </t:adminwrapper>
</t:masterpage>