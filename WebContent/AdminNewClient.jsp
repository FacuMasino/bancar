<%@taglib prefix="t" tagdir="/WEB-INF/tags/" %>

  <t:masterpage title="Admin - Alta Cliente" customNavbar="true">
    <t:adminwrapper activeMenuItem="adminClientsMenu">
      <div class="container md:max-w-[800px] mx-auto my-6 px-2">
        <p class="font-bold text-lg mb-3">Alta de cliente</p>

        <form action="AdminClients" method="post"
          class="bg-white rounded rounded-lg flex flex-col gap-4 p-8 w-full">

          <!-- Nombre y apellido -->

          <div class="flex gap-4">
            <div class="flex flex-col w-full">
              <div class="form-control w-full">
                <label for="clientFirstName" class="label">
                  <span class="label-text font-bold">Nombre</span>
                </label>
              </div>
              <input type="text" name="clientFirstName" placeholder="Nombre" class="input input-bordered w-full" />
            </div>
            <div class="flex flex-col w-full">
              <div class="form-control w-full">
                <label for="clientLastName" class="label">
                  <span class="label-text font-bold">Apellido</span>
                </label>
              </div>
              <input type="text" name="clientLastName" placeholder="Apellido" class="input input-bordered w-full" />
            </div>
          </div>
          <div class="flex gap-4">

            <!-- Sexo -->

            <div class="flex flex-col w-full">
              <div class="form-control w-full">
                <label for="clientSex" class="label">
                  <span class="label-text font-bold">Sexo</span>
                </label>
              </div>
              <input type="text" name="clientSex" placeholder="Mujer/Hombre/No informado"
                class="input input-bordered w-full" />
            </div>

            <!-- Fecha de nacimiento -->

            <div class="flex flex-col w-full">
              <div class="form-control w-full">
                <label for="clientBirth" class="label">
                  <span class="label-text font-bold">Fecha de nacimiento</span>
                </label>
              </div>
              <input type="text" name="clientBirth" placeholder="yyyy-mm-dd" class="input input-bordered w-full" />
            </div>
          </div>

          <!-- Email -->

          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientEmail" class="label">
                <span class="label-text font-bold">Email</span>
              </label>
            </div>
            <input type="email" name="clientEmail" placeholder="Email" class="input input-bordered w-full" />
          </div>

          <!-- Usuario y contraseña -->

          <div class="flex flex-col w-full gap-2">
            <div class="flex gap-4">
              <div class="flex flex-col w-full">
                <div class="form-control w-full">
                  <label for="clientUsername" class="label">
                    <span class="label-text font-bold">Usuario</span>
                  </label>
                </div>
                <input type="text" name="clientUsername" placeholder="Nombre de usuario"
                  class="input input-bordered w-full" />
              </div>
              <div class="flex flex-col w-full">
                <div class="form-control w-full">
                  <label for="clientPassword" class="label">
                    <span class="label-text font-bold">Contraseña</span>
                  </label>
                </div>
                <input type="password" name="clientPassword" placeholder="Contraseña"
                  class="input input-bordered w-full" />
              </div>
            </div>
            <p>* La contraseña debe contener al menos una mayúscula, una minúscula y un número.</p>
          </div>

          <!-- DNI -->

          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientDni" class="label">
                <span class="label-text font-bold">DNI</span>
              </label>
            </div>
            <input type="number" name="clientDni" placeholder="DNI" class="input input-bordered w-full" />
          </div>

          <!-- Cuil -->

          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientCuil" class="label">
                <span class="label-text font-bold">CUIL</span>
              </label>
            </div>
            <input type="number" name="clientCuil" placeholder="CUIL" class="input input-bordered w-full" />
          </div>

          <!-- Calle y número -->

          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientStreetName" class="label">
                <span class="label-text font-bold">Calle</span>
              </label>
            </div>
            <input type="text" name="clientStreetName" placeholder="Calle" class="input input-bordered w-full" />
          </div>

          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientStreetNumber" class="label">
                <span class="label-text font-bold">Número</span>
              </label>
            </div>
            <input type="text" name="clientStreetNumber" placeholder="Número" class="input input-bordered w-full" />
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
                <select class="bg-white select select-bordered w-full max-w-xs">
                  <option disabled selected></option>
                  <option>Buenos Aires</option>
                  <option>Mendoza</option>
                </select>
              </div>

              <!-- Localidad -->

              <div class="flex flex-col w-full">
                <div class="form-control w-full">
                  <label for="clientCity" class="label">
                    <span class="label-text font-bold">Localidad</span>
                  </label>
                </div>
                <select class="bg-white select select-bordered w-full max-w-xs">
                  <option disabled selected></option>
                  <option>Tigre</option>
                  <option>Pacheco</option>
                </select>
              </div>
            </div>
          </div>

          <!-- Telefono -->

          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientPhone" class="label">
                <span class="label-text font-bold">Teléfono</span>
              </label>
            </div>
            <input type="tel" name="clientPhone" placeholder="Teléfono de contacto" required pattern="[0-9]{10}"
              class="input input-bordered w-full" />
          </div>

          <!-- Divisor -->

          <div class="divider m-0"></div>
          <div class="flex justify-end w-full">
            <button class="btn btn-primary" type="submit" name="action" value="newClient">Dar de alta</button>
          </div>

        </form>

      </div>
    </t:adminwrapper>
  </t:masterpage>