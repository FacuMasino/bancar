<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:masterpage title="Alta de cliente" customNavbar="true">
  <jsp:include page="/Componentes/NavbarAdmin.jsp" />
  <div class="container md:max-w-[800px] mx-auto my-6 px-2">
    <p class="font-bold text-lg mb-3">Alta de cliente</p>
    <form method="post" action="#" class="bg-white rounded rounded-lg flex flex-col gap-4 p-8 w-full">
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
      <!-- Email -->
      <div class="flex flex-col w-full">
          <div class="form-control w-full">
            <label for="clientEmail" class="label">
              <span class="label-text font-bold">Email</span> 
            </label>
          </div>
          <input type="email" name="clientEmail" placeholder="Email del cliente" class="input input-bordered w-full" />
      </div>
      <!-- Usuario y Pass -->
      <div class="flex flex-col w-full gap-2">
        <div class="flex gap-4">
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientPass" class="label">
                <span class="label-text font-bold">Usuario</span> 
              </label>
            </div>
            <input type="text" name="clientName" placeholder="Nombre de usuario (sin espacios)" class="input input-bordered w-full" />
          </div>
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientPass2" class="label">
                <span class="label-text font-bold">Contraseña</span>
              </label>
            </div>
            <input type="password" name="clientPass" placeholder="Ingresá una contraseña" class="input input-bordered w-full" />
          </div>
        </div>
        <p>* La contraseña debe contener al menos 1 mayúscula, 1 minúscula y 1 número</p>
      </div>
       <!-- DNI -->
      <div class="flex flex-col w-full">
          <div class="form-control w-full">
            <label for="clientDNI" class="label">
              <span class="label-text font-bold">DNI</span> 
            </label>
          </div>
          <input type="number" name="clientDNI" placeholder="DNI del cliente (sin espacios ni puntos)" class="input input-bordered w-full" />
      </div>
          <!-- DOMICILIO -->
      <div class="flex flex-col w-full">
          <div class="form-control w-full">
            <label for="clientAdress" class="label">
              <span class="label-text font-bold">Domicilio</span> 
            </label>
          </div>
          <input type="email" name="clientAdress" placeholder="Tu Domicilio (Calle y número)" class="input input-bordered w-full" />
      </div>
         <!-- Provincia -->
      <div class="flex flex-col w-full gap-2">
        <div class="flex gap-4">
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientProvince" class="label">
                <span class="label-text font-bold">Provincia</span> 
              </label>
            </div>
            <input type="text" name="clientProvince" placeholder="Provincia" class="input input-bordered w-full" />
          </div>
             <!-- Localidad -->
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientPass2" class="label">
                <span class="label-text font-bold">Localidad</span>
              </label>
            </div>
            <input type="text" name="clientLocality" placeholder="Localidad" class="input input-bordered w-full" />
          </div>
        </div>
           </div>
             <!-- Telefono -->
      <div class="flex flex-col w-full">
          <div class="form-control w-full">
            <label for="clientTelephone" class="label">
              <span class="label-text font-bold">Telefono</span> 
            </label>
          </div>
          <input type="number" name="clientPhone" placeholder="Teléfono de contacto (sin guiones)" class="input input-bordered w-full" />
      </div>
      <!-- Divisor -->
      <div class="divider m-0"></div>
      <div class="flex justify-end w-full">
        <button class="btn btn-primary" type="submit">Dar de alta</button>
      </div>
    </form>
  </div>
</t:masterpage>