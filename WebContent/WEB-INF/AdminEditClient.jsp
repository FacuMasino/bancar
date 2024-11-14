<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="client" value="${requestScope.client}" />
<c:set var="setIsDisabled" value="${!client.activeStatus ? 'disabled':''}" />

<t:masterpage title="Admin - Editar Cliente" customNavbar="true">
  <t:adminwrapper activeMenuItem="adminClientsMenu">
    <div class="container md:max-w-[1400px] mx-auto my-6 px-2">
      <p class="font-bold text-xl mb-6">Editar información del cliente</p>
      <form method="post" action="Clients"
        class="bg-white rounded rounded-lg flex flex-col gap-4 p-8 w-full">
        <div class="flex justify-between">
          <div>
            <p class="font-bold">Editando cliente ID ${client.clientId}</p>
            <c:set var="statusClass" value="${client.activeStatus ? 
              'border-green-600 text-green-600 text-green-600' 
              : 'border-red-600 text-red-600'}" />
            <span
              class="flex flex-col items-center w-fit px-2.5 rounded-full border font-semibold ${statusClass}">
              ${client.activeStatus ? 'Activo' : 'Baja'}
            </span>
          </div>
          <button class="btn mr-4 btn-neutral" type="submit" name="action"
            value="cancelClient" ${setIsDisabled}>
            Dar de baja
          </button>
        </div>

        <!-- Nombre y Apellido -->
        <div class="flex gap-4">
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientName" class="label">
                <span class="label-text font-bold">Nombre</span>
              </label>
            </div>
            <input type="text" name="clientName" placeholder="Ingresá el nombre"
              class="input input-bordered w-full" value="${client.firstName}" 
              ${setIsDisabled} />
          </div>
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientSurname" class="label">
                <span class="label-text font-bold">Apellido</span>
              </label>
            </div>
            <input type="text" name="clientSurname"
              placeholder="Ingresá el apellido"
              class="input input-bordered w-full" value="${client.lastName}" 
              ${setIsDisabled} />
          </div>
        </div>
        <!-- DNI y CUIL -->
        <div class="flex gap-4">
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientDNI" class="label">
                <span class="label-text font-bold">DNI</span>
              </label>
            </div>
            <input type="text" name="clientDNI"
              placeholder="DNI del cliente (sin espacios ni puntos)"
              class="input input-bordered w-full" value="${client.dni}" 
              ${setIsDisabled} />
          </div>
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientCuil" class="label">
                <span class="label-text font-bold">CUIL</span>
              </label>
            </div>
            <input type="text" name="clientCuil"
              placeholder="Cuil del cliente (sin espacios ni puntos)"
              class="input input-bordered w-full" value="${client.cuil}"
              ${setIsDisabled} />
          </div>
        </div>
        <!-- Genero y Nacionalidad -->
        <div class="flex gap-4">
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientSex" class="label">
                <span class="label-text font-bold">Género</span>
              </label>
            </div>
            <input type="text" name="clientSex" placeholder="Genero del cliente"
              class="input input-bordered w-full" value="${client.sex}"
              ${setIsDisabled} />
          </div>
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientNationality" class="label">
                <span class="label-text font-bold">Nacionalidad</span>
              </label>
            </div>
            <input type="text" name="clientNationality"
              placeholder="Nacionalidad del cliente"
              class="input input-bordered w-full"
              value="${client.nationality.name}"
              ${setIsDisabled} />
          </div>
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientBirthDate" class="label"> 
                <span class="label-text font-bold">
                  Fecha de Nacimiento
                </span>
              </label>
            </div>
            <input type="text" name="clientBirthDate"
              placeholder="Fecha de nacimiento"
              class="input input-bordered w-full" value="${client.birthDate}"
              ${setIsDisabled} />
          </div>
        </div>

        <!-- Email y Teléfono -->
        <div class="flex gap-4">
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientEmail" class="label">
                <span class="label-text font-bold">Email</span>
              </label>
            </div>
            <input type="text" name="clientEmail"
              placeholder="Email del cliente"
              class="input input-bordered w-full" value="${client.email}"
              ${setIsDisabled} />
          </div>
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientTelephone" class="label">
                <span class="label-text font-bold">Teléfono</span>
              </label>
            </div>
            <input type="text" name="clientTelephone"
              placeholder="Telefono del cliente"
              class="input input-bordered w-full" value="${client.phone}"
              ${setIsDisabled} />
          </div>
        </div>

        <!-- Dirección -->
        <div class="flex gap-4">

          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientAdress" class="label">
                <span class="label-text font-bold">Domicilio</span>
              </label>
            </div>
            <input type="text" name="clientStreet"
              placeholder="Dirección del cliente (Calle)"
              class="input input-bordered w-full"
              value="${client.address.streetName}"
              ${setIsDisabled} />
          </div>
          <div class="flex gap-4">
            <div class="flex flex-col w-full">
              <div class="form-control w-full">
                <label for="clientAdressNumber" class="label">
                  <span class="label-text font-bold">Nro</span>
                </label>
              </div>
              <input type="text" name="clientAdressNumber"
                placeholder="Numeración Domicilio"
                class="input input-bordered w-full"
                value="${client.address.streetNumber}"
                ${setIsDisabled} />
            </div>
            <div class="flex flex-col w-full">
              <div class="form-control w-full">
                <label for="clientAdressFlat" class="label">
                  <span class="label-text font-bold">Piso/Depto</span>
                </label>
              </div>
              <input type="text" name="clientAdressFlat"
                placeholder="Piso/Departamento"
                class="input input-bordered w-full"
                value="${client.address.flat}"
                ${setIsDisabled} />
            </div>
          </div>
        </div>
        <!-- Detalles, Localidad y Provincia   -->
        <div class="flex flex-col w-full gap-2">
          <div class="flex gap-4">

            <!-- Detalles -->
            <div class="flex flex-col w-full">
              <div class="form-control w-full">
                <label for="clientDetails" class="label">
                  <span class="label-text font-bold">Obervaciones</span>
                </label>
              </div>
              <input type="text" name="clientDetails"
                placeholder="Detalles del domicilio del cliente"
                class="input input-bordered w-full"
                value="${client.address.details}"
                ${setIsDisabled} />
            </div>

            <!-- Localidad -->
            <div class="flex flex-col w-full">
              <div class="form-control w-full">
                <label for="clientLocality" class="label">
                  <span class="label-text font-bold">Localidad</span>
                </label>
              </div>
              <select class="bg-white select select-bordered w-full" ${setIsDisabled}>
                <option disabled selected></option>
                <option>Tigre</option>
                <option>San Fernando</option>
              </select>
            </div>

            <!-- Provincia -->
            <div class="flex flex-col w-full">
              <div class="form-control w-full">
                <label for="clientProvince" class="label"> <span
                  class="label-text font-bold">Provincia</span>
                </label>
              </div>
              <select class="bg-white select select-bordered w-full" ${setIsDisabled}>
                <option disabled selected></option>
                <option>client.address.province.name</option>
                <option>Capital Federal</option>
              </select>
            </div>
          </div>
        </div>

        <!-- Divisor -->
        <div class="divider m-0"></div>
        <div class="flex justify-end w-full">
          <a href="Clients?clientId=${client.clientId}&action=view"
            class="btn mr-4 btn-neutral">
            ${client.activeStatus ? 'Cancelar':'Volver'}
          </a>
          <button class="btn btn-primary" type="submit" name="action"
            value="saveClient" ${setIsDisabled}>
            Guardar cambios
          </button>
        </div>
      </form>
    </div>
  </t:adminwrapper>
</t:masterpage>