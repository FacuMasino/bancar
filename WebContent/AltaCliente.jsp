<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:masterpage title="Alta de cliente">
  <div class="container md:max-w-[800px] mx-auto mt-6 px-2">
    <div class="mx-auto">
      <p class="font-bold text-lg mb-3">Alta de cliente</p>
      <form method="post" action="#" class="bg-white rounded rounded-lg flex flex-col gap-4 p-8 w-full">
        <!-- Nombre y Apellido -->
        <div class="flex w-full gap-4">
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientName" class="label">
                <span class="label-text font-bold">Nombre</span> 
              </label>
            </div>
            <input type="text" name="clientName" placeholder="Tu Nombre" class="input input-bordered w-full" />
          </div>
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientSurname" class="label">
                <span class="label-text font-bold">Apellido</span>
              </label>
            </div>
            <input type="text" name="clientSurname" placeholder="Tu Apellido" class="input input-bordered w-full" />
          </div>
        </div>
        <!-- Email -->
        <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientEmail" class="label">
                <span class="label-text font-bold">Email</span> 
              </label>
            </div>
            <input type="email" name="clientEmail" placeholder="Tu Email" class="input input-bordered w-full" />
        </div>
        <!-- Contraseña -->
        <div class="flex flex-col w-full gap-2">
          <div class="flex gap-4">
            <div class="flex flex-col w-full">
              <div class="form-control w-full">
                <label for="clientPass" class="label">
                  <span class="label-text font-bold">Contraseña</span> 
                </label>
              </div>
              <input type="password" name="clientPass" placeholder="Tu contraseña" class="input input-bordered w-full" />
            </div>
            <div class="flex flex-col w-full">
              <div class="form-control w-full">
                <label for="clientPass2" class="label">
                  <span class="label-text font-bold">Confirmar Contraseña</span>
                </label>
              </div>
              <input type="password" name="clientPass2" placeholder="Repetir contraseña" class="input input-bordered w-full" />
            </div>
          </div>
          <p>* Tu contraseña debe contener al menos 1 mayúscula, 1 minúscula y 1 número</p>
        </div>
         <!-- DNI -->
        <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientDNI" class="label">
                <span class="label-text font-bold">DNI</span> 
              </label>
            </div>
            <input type="number" name="clientDNI" placeholder="Tu DNI (sin espacios ni puntos)" class="input input-bordered w-full" />
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
            <input type="number" name="clientTelephone" placeholder="Tu teléfono (sin guiones)" class="input input-bordered w-full" />
        </div>
        <!-- Divisor -->
        <div class="divider m-0"></div>
        <div class="flex justify-end w-full">
          <button class="btn btn-primary" type="submit">Registrarme</button>
        </div>
      </form>
    </div>
  </div>
</t:masterpage>