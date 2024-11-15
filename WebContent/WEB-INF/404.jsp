<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:masterpage title="Home">
  <section class="flex items-center h-full p-16 text-gray-800">
  	<div class="container flex flex-col items-center justify-center px-5 mx-auto my-8">
      <div class="max-w-md text-center">
        <h2 class="mb-8 font-extrabold text-9xl text-black">
    	  <span class="sr-only">Error</span>404
    	</h2>
          <p class="text-2xl font-semibold md:text-3xl">Lo sentimos, no pudimos encontrar lo que buscabas.</p>
          <p class="mt-4 mb-8 text-gray-600">Puedes volver a la home o iniciar sesión para ir a tu área de cliente :)</p>
          <a href="${pageContext.request.contextPath}" class="px-8 py-3 bg-red-600 text-white font-semibold rounded">Volver a la home</a>
    	</div>
  	</div>
  </section>
</t:masterpage>