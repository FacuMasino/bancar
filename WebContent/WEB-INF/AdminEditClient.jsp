<%@page import="domainModel.Client"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="client" value="${requestScope.client}" />
<c:set var="provinces" value="${requestScope.provinces != null 
                              ? requestScope.provinces : emptyList}" />
<c:set var="setIsDisabled" value="${!client.activeStatus ? 'disabled':''}" />

<t:masterpage title="Admin - Editar Cliente" customNavbar="true">
  <t:adminwrapper activeMenuItem="adminClientsMenu">
    <div class="container md:max-w-[1400px] mx-auto my-6 px-2">
      <p class="font-bold text-xl mb-6">Editar informaci&oacute;n del cliente</p>
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
          <input type="hidden" name="clientId" value="${client.getClientId()}" />
          <button class="btn mr-4 btn-neutral" type="button" onclick="modal_cancel_client.showModal()">
            Dar de baja/alta
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
            <input type="text" name="clientFirstName" placeholder="Ingres&aacute; el nombre"
              class="input input-bordered w-full" value="${client.firstName}" 
              ${setIsDisabled} />
          </div>
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientSurname" class="label">
                <span class="label-text font-bold">Apellido</span>
              </label>
            </div>
            <input type="text" name="clientLastName"
              placeholder="Ingres&aacute; el apellido"
              class="input input-bordered w-full" value="${client.lastName}" 
              ${setIsDisabled} />
          </div>
        </div>
        <!-- DNI y CUIL -->
        <div class="flex gap-4">
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientDni" class="label">
                <span class="label-text font-bold">DNI</span>
              </label>
            </div>
            <input type="number" name="clientDni"
              placeholder="DNI del cliente (sin espacios ni puntos)"
              class="input input-bordered w-full" value="${client.dni}" 
              ${setIsDisabled} min="1000000" max="99999999"/>
          </div>
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientCuil" class="label">
                <span class="label-text font-bold">CUIL</span>
              </label>
            </div>
            <input type="number" name="clientCuil"
              placeholder="Cuil del cliente (sin espacios ni puntos)"
              class="input input-bordered w-full" value="${client.cuil}"
              ${setIsDisabled} min="20000000000" max="28999999999"/>
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
            <select name="clientSex" class="bg-white select select-bordered w-full" ${setIsDisabled}>
              <option value="Masculino"
                ${client.sex == 'Masculino' ? 'selected':''}>
                Masculino
              </option>
              <option value="Femenino"
                ${client.sex == 'Femenino' ? 'selected':''}>
                Femenino
              </option>
              <option value="No informa"
                ${client.sex == null ? 'selected':''}>
                No informado
              </option>
            </select>
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
            <input type="date" name="clientBirthDate"
              placeholder="Fecha de nacimiento"
              class="input input-bordered w-full" value="${client.birthDate}"
              ${setIsDisabled} required/>
          </div>
        </div>

        <!-- Email y Telefono -->
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
              <label for="clientPhone" class="label">
                <span class="label-text font-bold">Teléfono (Sin 0 ni 15)</span>
              </label>
            </div>
            <input type="number" name="clientPhone"
              placeholder="Tel&eacute;fono del cliente"
              class="input input-bordered w-full" value="${client.phone}"
              ${setIsDisabled} />
          </div>
        </div>

        <!-- Direccion -->
        <div class="flex gap-4">
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientAddress" class="label">
                <span class="label-text font-bold">Domicilio</span>
              </label>
            </div>
            <input type="text" name="clientStreetName"
              placeholder="Direcci&oacute;n del cliente (Calle)"
              class="input input-bordered w-full"
              value="${client.address.streetName}"
              ${setIsDisabled} />
          </div>
          <div class="flex gap-4">
            <div class="flex flex-col w-full">
              <div class="form-control w-full">
                <label for="clientStreetNumber" class="label">
                  <span class="label-text font-bold">Nro</span>
                </label>
              </div>
              <input type="number" name="clientStreetNumber"
                placeholder="Numeraci&oacute;n Domicilio"
                class="input input-bordered w-full"
                value="${client.address.streetNumber}"
                ${setIsDisabled} min="1" max="999999" />
            </div>
            <div class="flex flex-col w-full">
              <div class="form-control w-full">
                <label for="clientFlat" class="label">
                  <span class="label-text font-bold">Piso/Depto</span>
                </label>
              </div>
              <input type="text" name="clientFlat"
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
                  <span class="label-text font-bold">Observaciones</span>
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
                <label for="clientCity" class="label">
                  <span class="label-text font-bold">Localidad</span>
                </label>
              </div>
              <input type="text" name="clientCity"
                placeholder="Ciudad"
                class="input input-bordered w-full"
                value="${client.address.city.name}"
                ${setIsDisabled} />
            </div>

            <!-- CÃ³digo postal -->
            <div class="flex flex-col w-full">
              <div class="form-control w-full">
                <label for="clientZipCode" class="label">
                  <span class="label-text font-bold">CP</span>
                </label>
              </div>
              <input type="text" name="clientZipCode"
                placeholder="CP"
                class="input input-bordered w-full"
                value="${client.address.city.zipCode}"
                ${setIsDisabled} />
            </div>

            <!-- Provincia -->
            <div class="flex flex-col w-full">
              <div class="form-control w-full">
                <label for="clientProvinceId" class="label"> <span
                  class="label-text font-bold">Provincia</span>
                </label>
              </div>
              <select name="clientProvinceId" class="bg-white select select-bordered w-full" ${setIsDisabled}>
                <c:choose>
                  <c:when test="${empty provinces}">
                    <!-- Mostrar mensaje si provinces esta vacia -->
                    <option disabled selected>Error: No hay provincias para mostrar</option>
                  </c:when>
                  <c:otherwise>
                    <c:set var="clientProvinceId" value="${client.address.province.id}" />
                    <c:forEach var="province" items="${provinces}">
                      <option value="${province.id}"
                        ${clientProvinceId == province.id ? 'selected':''}>
                        ${province.name}
                      </option>
                    </c:forEach>
                  </c:otherwise>
                </c:choose>
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
        
        <!-- MODAL Confirmación de baja/alta -->
        <dialog id="modal_cancel_client" class="modal">
          <div class="modal-box bg-white">
            <div class="sm:flex sm:items-start">
              <div class="mx-auto flex size-12 shrink-0 items-center justify-center rounded-full bg-red-100 sm:mx-0 sm:size-10">
                <svg class="size-6 text-red-600" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true" data-slot="icon">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126ZM12 15.75h.007v.008H12v-.008Z"></path>
                </svg>
              </div>
              <div class="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left">
                <h3 class="text-base font-semibold text-gray-900" id="modal-title">Confirmación</h3>
                <div class="my-2">
                  <p class="text-gray-500">
                    Está seguro que desea 
                    <span class="font-semibold">dar de ${client.activeStatus ? 'baja':'alta'}</span>
                    al cliente?
                  </p>
                </div>
                <c:if test="${client.activeStatus}">
                    <ul class="list-disc list-inside text-sm">
                      <li>
                        Se eliminarán todas las cuentas
                      </li>
                      <li>
                        Se rechazarán los préstamos en revisión
                      </li>
                      <li>
                        El cliente ya no podrá acceder al Home Banking
                      </li>
                    </ul>
                </c:if>
              </div>
            </div>
            <div class="modal-action">
                <button type="submit" class="btn btn-primary" name="action" 
                  value="toggleActiveStatus">
                  Confirmar
                </button>
                <button type="button" class="btn" 
                  onclick="modal_cancel_client.close()">
                  Cancelar
                </button>
            </div>
          </div>
        </dialog>
      </form>
    </div>
  </t:adminwrapper>
</t:masterpage>