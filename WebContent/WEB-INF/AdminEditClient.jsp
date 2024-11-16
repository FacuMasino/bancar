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
          <button class="btn mr-4 btn-neutral" type="submit" name="action"
            value="toggleActiveStatus">
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
            <input type="text" name="clientName" placeholder="Ingres&aacute; el nombre"
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
              placeholder="Ingres&aacute; el apellido"
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
            <input type="number" name="clientDNI"
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
                <span class="label-text font-bold">G&eacute;nero</span>
              </label>
            </div>
            <select name="clientSex" class="bg-white select select-bordered w-full" ${setIsDisabled}>
              <option value="Masulino"
                ${client.sex == 'Masculino' ? 'selected':''}>
                Masculino
              </option>
              <option value="Femenino"
                ${client.sex == 'Femenino' ? 'selected':''}>
                Femenino
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
              <label for="clientTelephone" class="label">
                <span class="label-text font-bold">Tel&eacute;fono (Sin 0 ni 15)</span>
              </label>
            </div>
            <input type="text" name="clientTelephone"
              placeholder="Tel&eacute;fono del cliente"
              class="input input-bordered w-full" value="${client.phone}"
              ${setIsDisabled} />
          </div>
        </div>

        <!-- Direccion -->
        <div class="flex gap-4">

          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientAdress" class="label">
                <span class="label-text font-bold">Domicilio</span>
              </label>
            </div>
            <input type="text" name="clientStreet"
              placeholder="Direcci&oacute;n del cliente (Calle)"
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
                placeholder="Numeraci&oacute;n Domicilio"
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

            <!-- Código postal -->
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
      </form>
    </div>
  </t:adminwrapper>
</t:masterpage>