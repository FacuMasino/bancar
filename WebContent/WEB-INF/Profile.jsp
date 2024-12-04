<%@page import="domainModel.Client"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="client" value="${requestScope.client}" />

<t:masterpage title="Mi Perfil" customNavbar="true">
  <t:clientwrapper activeMenuItem="myProfileMenu">
    <div class="container mx-auto p-4">
      <div class="max-w-7xl mx-auto px-4 py-8">
        <h1 class="text-2xl font-bold mb-8">Mi Perfil</h1>

        <div class="bg-white rounded-lg shadow p-6">
          <h2 class="text-lg font-semibold mb-6">Información
            Personal</h2>

          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <label
                class="block text-sm font-medium text-gray-700 mb-2">Nombre
                Completo</label> <input type="text" name="clientName"
                value="${client.firstName} ${client.lastName}" disabled
                class="input input-bordered w-full" />
            </div>

            <div>
              <label
                class="block text-sm font-medium text-gray-700 mb-2">Usuario</label>
              <input type="text" name="clientSurname" value="${client.username}"
                disabled class="input input-bordered w-full" />
            </div>

            <div>
              <label
                class="block text-sm font-medium text-gray-700 mb-2">DNI</label>
              <input type="text" name="clientDni" value="${client.dni}"
                disabled class="input input-bordered w-full" />
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">DNI</label>
              <input type="text" name="clientDni" value="${client.cuil}"
                disabled class="input input-bordered w-full" />
            </div>            

            <div>
              <label
                class="block text-sm font-medium text-gray-700 mb-2">Email</label>
              <input type="text" name="clientEmail"
                value="${client.email}" disabled
                class="input input-bordered w-full" />
            </div>

            <div>
              <label
                class="block text-sm font-medium text-gray-700 mb-2">Teléfono</label>
              <input type="text" name="clientPhone" value="${client.phone}"
                disabled class="input input-bordered w-full" />
            </div>

            <div>
              <label
                class="block text-sm font-medium text-gray-700 mb-2">Dirección</label>
              <input type="text" name="clientAddress"
                value="${client.address.streetName} ${client.address.streetNumber}"
                disabled class="input input-bordered w-full" />
            </div>

            <div>
              <label
                class="block text-sm font-medium text-gray-700 mb-2">Provincia</label>
              <input type="text" name="clientProvince" value="${client.address.province.name}"
                disabled class="input input-bordered w-full" />
            </div>

            <div>
              <label
                class="block text-sm font-medium text-gray-700 mb-2">Localidad</label>
              <input type="text" name="clientLocality"
                value="${client.address.city.name}" disabled
                class="input input-bordered w-full" />
            </div>
            
            <div>
              <label
                class="block text-sm font-medium text-gray-700 mb-2">Nacionalidad</label>
              <input type="text" name="clientLocality"
                value="${client.address.country.name}" disabled
                class="input input-bordered w-full" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </t:clientwrapper>
</t:masterpage>