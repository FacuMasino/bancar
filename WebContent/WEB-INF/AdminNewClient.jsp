<%@page import="domainModel.Client"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="provinces" value="${requestScope.provinces != null 
                              ? requestScope.provinces : emptyList}" />

<c:set var="draftClient" value="${requestScope.draftClient != null 
                              ? requestScope.draftClient : null}" />

<t:masterpage title="Admin - Alta Cliente" customNavbar="true">
  <t:adminwrapper activeMenuItem="adminClientsMenu">
    <div class="container md:max-w-[800px] mx-auto my-6 px-2">
      <p class="font-bold text-lg mb-3">Alta de cliente</p>

      <form action="Clients" method="post"
        class="bg-white rounded rounded-lg flex flex-col gap-4 p-8 w-full">
    <!-- Usuario y contraseña -->

        <div class="flex flex-col w-full gap-2">
          <div class="flex gap-4">
            <div class="flex flex-col w-full">
              <div class="form-control w-full">
                <label for="clientUsername" class="label"> <span
                  class="label-text font-bold">Usuario</span>
                </label>
              </div>
              <input type="text" name="clientUsername"
                placeholder="Nombre de usuario"
                class="input input-bordered w-full" value="${draftClient.username}" />
            </div>
            <div class="flex flex-col w-full">
              <div class="form-control w-full">
                <label for="clientPassword" class="label"> <span
                  class="label-text font-bold">Contrase�a</span>
                </label>
              </div>
              <input type="password" name="clientPassword"
                placeholder="Contrase�a" class="input input-bordered w-full" 
                value="${draftClient.password}"/>
            </div>
          </div>
          <p>* La contrase�a debe contener al menos una may�scula, una
            min�scula y un n�mero.</p>
        </div>

        <!-- Nombre y apellido -->

        <div class="flex gap-4">
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientFirstName" class="label"> <span
                class="label-text font-bold">Nombre</span>
              </label>
            </div>
            <input type="text" name="clientFirstName" placeholder="Nombre"
              class="input input-bordered w-full"
              value="${draftClient.firstName}"/>
          </div>
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientLastName" class="label"> <span
                class="label-text font-bold">Apellido</span>
              </label>
            </div>
            <input type="text" name="clientLastName" placeholder="Apellido"
              class="input input-bordered w-full" 
              value="${draftClient.lastName}"/>
          </div>
        </div>
        
        
         <!-- DNI y CUIL -->

        <div class="flex gap-4  ">
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientDni" class="label"> <span
                class="label-text font-bold">DNI</span>
              </label>
            </div>
            <input type="number" name="clientDni" placeholder="DNI"
              class="input input-bordered w-full"
              value="${draftClient.dni}"/>
          </div>
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientCuil" class="label"> <span
                class="label-text font-bold">CUIL</span>
              </label>
            </div>
            <input type="number" name="clientCuil" placeholder="CUIL"
              class="input input-bordered w-full" 
              value="${draftClient.cuil}"/>
          </div>
        </div>

          <!-- Fecha de nacimiento -->
        <div class="flex gap-4">
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientBirth" class="label"> <span
                class="label-text font-bold">Fecha de nacimiento</span>
              </label>
            </div>
            <input type="date" name="clientBirth" placeholder="yyyy-mm-dd"
              class="input input-bordered w-full" required
              value="${draftClient.birthDate}"/>
          </div>

          <!-- Sexo -->

          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientSex" class="label"> <span
                class="label-text font-bold">Genero</span>
              </label>
            </div>
            <select name="clientSex" class="bg-white select select-bordered w-full">
              <option value="Masulino"
                ${client.sex == 'Masculino' ? 'selected':''}
                ${draftClient.sex == 'Femenino' ? 'selected':''}>
                Masculino
              </option>
              <option value="Femenino"
                ${client.sex == 'Femenino' ? 'selected':''}
                ${draftClient.sex == 'Femenino' ? 'selected':''}>
                Femenino
              </option>
            </select>
          </div>
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientNationality" class="label"> <span
                class="label-text font-bold">Nacionalidad </span>
              </label>
            </div>
            <input type="text" name="clientNationality"
              placeholder="Nacionalidad" class="input input-bordered w-full"
              value="${draftClient.nationality.name}"/>
          </div>
        </div>

        <!-- Telefono -->
   <div class="flex gap-4  ">
        <div class="flex flex-col w-full">
          <div class="form-control w-full">
            <label for="clientPhone" class="label"> <span
              class="label-text font-bold">Tel�fono (Sin 0 ni 15)</span>
            </label>
          </div>
          <input type="text" name="clientPhone"
            placeholder="Tel�fono de contacto" required
            class="input input-bordered w-full"
            value="${draftClient.phone}" />
        </div>

        <!-- Email -->

        <div class="flex flex-col w-full">
          <div class="form-control w-full">
            <label for="clientEmail" class="label"> <span
              class="label-text font-bold">Email</span>
            </label>
          </div>
          <input type="email" name="clientEmail" placeholder="Email"
            class="input input-bordered w-full"
            value="${draftClient.email}"/>
        </div> </div>

        <!-- Calle y n�mero -->
        <div class="flex w-full gap-4 ">
          <div class="flex flex-col w-3/4">

            <div class="form-control w-full">
              <label for="clientStreetName" class="label"> <span
                class="label-text font-bold">Calle</span>
              </label>
            </div>
            <input type="text" name="clientStreetName" placeholder="Calle"
              class="input input-bordered w-full"
              value="${draftClient.address.streetName}"/>
          </div>
          <div class="flex flex-col w-1/4">
            <div class="form-control w-full">
              <label for="clientStreetNumber" class="label"> <span
                class="label-text font-bold">N�mero</span>
              </label>
            </div>
            <input type="text" name="clientStreetNumber" placeholder="N�mero"
              class="input input-bordered w-full"
              value="${draftClient.address.streetNumber}"/>
          </div>
        </div>

        <!-- Depto/Piso  - Detalles  -->
        <div class="flex gap-4">
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientAddressFlat" class="label"> <span
                class="label-text font-bold">Piso/Depto </span>
              </label>
            </div>
            <input type="text" name="clientAddressFlat" placeholder="Piso-Depto"
              class="input input-bordered w-full"
              value="${draftClient.address.flat}"/>
          </div>
          <div class="flex flex-col w-full">
            <div class="form-control w-full">
              <label for="clientAddressDetails" class="label"> <span
                class="label-text font-bold">Observaciones </span>
              </label>
            </div>
            <input type="text" name="clientAddressDetails"
              placeholder="Observaciones del domicilio"
              class="input input-bordered w-full"
              value="${draftClient.address.details}"/>
          </div>
        </div>

        <!-- Provincia -->

        <div class="flex flex-col w-full gap-2">
          <div class="flex gap-4">
            <div class="flex flex-col w-full">
              <div class="form-control w-full">
                <label for="clientProvinceId" class="label"> <span
                  class="label-text font-bold">Provincia</span>
                </label>
              </div>
              <select name="clientProvinceId"
                class="bg-white select select-bordered w-full max-w-xs">
                <c:choose>
                  <c:when test="${empty provinces}">
                    <!-- Mostrar mensaje si provinces est� vac�a -->
                    <option disabled selected>Error: No hay provincias para mostrar</option>
                  </c:when>
                  <c:otherwise>
                    <c:set var="draftProvinceId" value="${draftClient.address.province.id}" />
                    <c:forEach var="province" items="${provinces}">
                      <option value="${province.id}"
                        ${draftProvinceId == province.id ? 'selected':''}>
                        ${province.name}
                      </option>
                    </c:forEach>
                  </c:otherwise>
                </c:choose>
              </select>

            </div>

            <!-- Localidad -->

            <div class="flex flex-col w-full">
              <div class="form-control w-full">
                <label for="clientCity" class="label"> <span
                  class="label-text font-bold">Localidad</span>
                </label>
              </div>
              <input type="text" name="clientCity"
                placeholder="Ingrese Ciudad"
                class="input input-bordered w-full"
                value="" value="${draftClient.address.city.name}"/>
            </div>
          </div>
        </div>

        <!-- Divisor -->

        <div class="divider m-0"></div>
        <div class="flex justify-end w-full">
          <button class="btn btn-primary" type="submit" name="action"
            value="newClient">Dar de alta</button>
        </div>
      </form>
    </div>
  </t:adminwrapper>
</t:masterpage>