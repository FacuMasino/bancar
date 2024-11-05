<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<t:masterpage title="Admin - Edit Client" customNavbar="true">
  <t:adminwrapper activeMenuItem="adminClientsMenu">
    <div class="container md:max-w-[1400px] mx-auto my-6 px-2">
    <p class="font-bold text-xl mb-6">Editar información del cliente</p>
    <form method="post" action="#" class="bg-white rounded rounded-lg flex flex-col gap-4 p-8 w-full">
      <div class="flex justify-between">
    	<p class="font-bold">Editando cliente ID 1</p>
    	<button class="btn mr-4 btn-neutral" type="submit">Dar de baja</button>
      </div>
      <!-- Nombre y Apellido -->
      <div class="flex gap-4">
        <div class="flex flex-col w-full">
          <div class="form-control w-full">
            <label for="clientName" class="label">
              <span class="label-text font-bold">Nombre</span> 
            </label>
          </div>
          <input type="text" name="clientName" placeholder="Ingresá el nombre" class="input input-bordered w-full" />
        </div>
        <div class="flex flex-col w-full">
          <div class="form-control w-full">
            <label for="clientSurname" class="label">
              <span class="label-text font-bold">Apellido</span>
            </label>
          </div>
          <input type="text" name="clientSurname" placeholder="Ingresá el apellido" class="input input-bordered w-full" />
        </div>
      </div>
      <!-- DNI y Email -->
      <div class="flex gap-4">
        <div class="flex flex-col w-full">
          <div class="form-control w-full">
            <label for="clientDNI" class="label">
              <span class="label-text font-bold">DNI</span> 
            </label>
          </div>
          <input type="text" name="clientDNI" placeholder="DNI del cliente (sin espacios ni puntos)" class="input input-bordered w-full" />
        </div>
        <div class="flex flex-col w-full">
          <div class="form-control w-full">
            <label for="clientEmail" class="label">
              <span class="label-text font-bold">Email</span>
            </label>
          </div>
          <input type="text" name="clientEmail" placeholder="Email del cliente" class="input input-bordered w-full" />
        </div>
      </div>
      <!-- Teléfono y dirección -->
      <div class="flex gap-4">
        <div class="flex flex-col w-full">
          <div class="form-control w-full">
            <label for="clientTelephone" class="label">
              <span class="label-text font-bold">Teléfono</span> 
            </label>
          </div>
          <input type="text" name="clientTelephone" placeholder="DNI del cliente (sin espacios ni puntos)" class="input input-bordered w-full" />
        </div>
        <div class="flex flex-col w-full">
          <div class="form-control w-full">
            <label for="clientAdress" class="label">
              <span class="label-text font-bold">Dirección</span>
            </label>
          </div>
          <input type="text" name="clientAdress" placeholder="Email del cliente" class="input input-bordered w-full" />
        </div>
      </div>
         <!-- Provincia y localidad -->
      <div class="flex flex-col w-full gap-2">
        <div class="flex gap-4">
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientProvince" class="label">
                <span class="label-text font-bold">Provincia</span> 
              </label>
            </div>
           <select class ="bg-white select select-bordered w-full">
  			<option disabled selected></option>
 			 <option>Buenos Aires</option>
 			 <option>Capital Federal</option>
			</select>
          </div>
             <!-- Localidad -->
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientPass2" class="label">
                <span class="label-text font-bold">Localidad</span>
              </label>
            </div>
            <select class="bg-white select select-bordered w-full">
  			<option disabled selected></option>
 			 <option>Tigre</option>
 			 <option>San Fernando</option>
			</select>
          </div>
         </div>
        </div>

      <!-- Divisor -->
      <div class="divider m-0"></div>
      <div class="flex justify-end w-full">
      	<button class="btn mr-4 btn-neutral" type="submit">Cancelar</button>
        <button class="btn btn-primary" type="submit">Guardar cambios</button>
      </div>
    </form>
  </div>
  </t:adminwrapper>
</t:masterpage>