<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<t:masterpage title="Admin - Alta Cliente" customNavbar="true">
  <t:adminwrapper activeMenuItem="adminClientsMenu">
    <div class="container md:max-w-[800px] mx-auto my-6 px-2">
      <p class="font-bold text-lg mb-3">Alta de cliente</p>
      <form method="post" action="AdminClientsServlet"
        class="bg-white rounded rounded-lg flex flex-col gap-4 p-8 w-full">
        <!-- Nombre y Apellido -->
        <div class="flex gap-4">
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientName" class="label"> <span
                class="label-text font-bold">Nombre</span>
              </label>
            </div>
            <input type="text" name="clientName"
              placeholder="Ingres� el nombre"
              class="input input-bordered w-full" />
          </div>
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientSurname" class="label"> <span
                class="label-text font-bold">Apellido</span>
              </label>
            </div>
            <input type="text" name="clientSurname"
              placeholder="Ingres� el apellido"
              class="input input-bordered w-full" />
          </div>
        </div>
        <div class="flex gap-4">
          <!-- Genero -->
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientSex" class="label"> <span
                class="label-text font-bold">G�nero</span>
              </label>
            </div>
            <input type="text" name="clientSex"
              placeholder="Fem/Masc/No informado"
              class="input input-bordered w-full" />
          </div>
          <!-- Fecha de nacimiento -->
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientBirthdate" class="label"> <span
                class="label-text font-bold">Fecha de nacimiento</span>
              </label>
            </div>
            <input type="text" name="clientBirthdate"
              placeholder="yyyy-mm-dd"
              class="input input-bordered w-full" />
          </div>
        </div>
        <!-- Email -->
        <div class="flex flex-col w-full">
          <div class="form-control w-full">
            <label for="clientEmail" class="label"> <span
              class="label-text font-bold">Email</span>
            </label>
          </div>
          <input type="email" name="clientEmail"
            placeholder="Email del cliente"
            class="input input-bordered w-full" />
        </div>
        <!-- Usuario y Pass -->
        <div class="flex flex-col w-full gap-2">
          <div class="flex gap-4">
            <div class="flex flex-col w-full">
              <div class="form-control w-full">
                <label for="clientPass" class="label"> <span
                  class="label-text font-bold">Usuario</span>
                </label>
              </div>
              <input type="text" name="clientUser"
                placeholder="Nombre de usuario (sin espacios)"
                class="input input-bordered w-full" />
            </div>
            <div class="flex flex-col w-full">
              <div class="form-control w-full">
                <label for="clientPass2" class="label"> <span
                  class="label-text font-bold">Contrase�a</span>
                </label>
              </div>
              <input type="password" name="clientPass"
                placeholder="Ingres� una contrase�a"
                class="input input-bordered w-full" />
            </div>
          </div>
          <p>* La contrase�a debe contener al menos 1 may�scula, 1
            min�scula y 1 n�mero</p>
        </div>
        <!-- DNI -->
        <div class="flex flex-col w-full">
          <div class="form-control w-full">
            <label for="clientDNI" class="label"> <span
              class="label-text font-bold">DNI</span>
            </label>
          </div>
          <input type="number" name="clientDNI"
            placeholder="DNI del cliente (sin espacios ni puntos)"
            class="input input-bordered w-full" />
        </div>
        <!-- CUIL -->
        <div class="flex flex-col w-full">
          <div class="form-control w-full">
            <label for="clientDNI" class="label"> <span
              class="label-text font-bold">CUIL</span>
            </label>
          </div>
          <input type="number" name="clientCuil"
            placeholder="Cuil del cliente (sin espacios ni puntos)"
            class="input input-bordered w-full" />
        </div>
        <!-- DOMICILIO CALLE-->
        <div class="flex flex-col w-full">
          <div class="form-control w-full">
            <label for="clientAdress" class="label"> <span
              class="label-text font-bold">Domicilio (Calle)</span>
            </label>
          </div>
          <input type="text" name="clientStreetName"
            placeholder=" Calle " class="input input-bordered w-full" />
        </div>
        <!-- DOMICILIO NUMERO-->
        <div class="flex flex-col w-full">
          <div class="form-control w-full">
            <label for="clientAdress" class="label"> <span
              class="label-text font-bold">Domicilio (N�mero)</span>
            </label>
          </div>
          <input type="text" name="clientStreetNumber"
            placeholder=" N�mero " class="input input-bordered w-full" />
        </div>
        <!-- Provincia -->
        <div class="flex flex-col w-full gap-2">
          <div class="flex gap-4">
            <div class="flex flex-col w-full">
              <div class="form-control w-full">
                <label for="clientProvince" class="label"> <span
                  class="label-text font-bold">Provincia</span>
                </label>
              </div>
              <select
                class="bg-white select select-bordered w-full max-w-xs">
                <option disabled selected></option>
                <option>Buenos Aires</option>
                <option>Capital Federal</option>
              </select>
            </div>
            <!-- Localidad -->
            <div class="flex flex-col w-full">
              <div class="form-control w-full">
                <label for="clientCity" class="label"> <span
                  class="label-text font-bold">Localidad</span>
                </label>
              </div>
              <select
                class="bg-white select select-bordered w-full max-w-xs">
                <option disabled selected></option>
                <option>Tigre</option>
                <option>San Fernando</option>
              </select>
            </div>
          </div>
        </div>
        <!-- Telefono -->
        <div class="flex flex-col w-full">
          <div class="form-control w-full">
            <label for="clientTelephone" class="label"> <span
              class="label-text font-bold">Telefono</span>
            </label>
          </div>
          <input type="tel" name="clientPhone"
            placeholder="Tel�fono de contacto" required
            pattern="[0-9]{10}" class="input input-bordered w-full" />
        </div>
        <!-- Divisor -->
        <div class="divider m-0"></div>
        <div class="flex justify-end w-full">
          <button class="btn btn-primary" type="submit" name="action"
            value="newClient" >Dar de alta</button>
        </div>
      </form>
    </div>
  </t:adminwrapper>
</t:masterpage>