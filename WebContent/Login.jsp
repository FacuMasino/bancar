<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:masterpage title="Iniciar sesi�n">
 <div class="flex flex-col px-6  lg:px-8">
  <div class="sm:mx-auto sm:w-full sm:max-w-sm">
    <h2 class="mt-10 text-center text-2xl/9 font-bold tracking-tight text-gray-900">Inicio de sesi�n</h2>
  </div>
  <div class="mt-10 p-6 sm:mx-auto sm:w-full sm:max-w-sm bg-white drop-shadow-sm rounded-lg">
    <form class="space-y-6" action="Login" method="POST">
      <div>
      <h3 class="font-bold">Usuario</h3>
        <label class="input input-bordered flex items-center gap-2">
 		 <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-user"><path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
 		 <input type="text" name="username" class="grow" placeholder="Ingresa tu usuario" required/>
		</label>
      </div>

      <div>
      <h3 class="font-bold">Contrase�a</h3>
         <label class="input input-bordered flex items-center gap-2">
           <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-lock"><rect width="18" height="11" x="3" y="11" rx="2" ry="2"/><path d="M7 11V7a5 5 0 0 1 10 0v4"/></svg>
 		   <input type="password" name="password" class="grow" placeholder="Ingresa tu contrase�a" required/>
		 </label>
      </div>
	  <hr>
      <div class="flex flex-col w-full">
        <button value="login" name="action" type="submit" class="btn btn-primary">Ingresar</button>
      </div>
    </form>
  </div>
</div>
</t:masterpage>