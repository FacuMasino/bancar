<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:masterpage title="Home">
  <div class="container flex flex-col gap-4 mx-auto p-4 max-w-7xl mb-8">
    <div class="grid grid-cols-12 bg-white rounded p-4">
      <div class="col-span-8 flex flex-col p-6">
        <div class="flex flex-col">
          <h1 class="text-3xl text-red-600 font-semibold">Gracias por tu interés en venir a BANC<span class="font-bold">AR</span></h1>
          <p class="text-lg mt-2">
            Estamos trabajando en nuestro formulario de solicitud en línea
          </p>
        </div>
        <div class="flex flex-col h-100 mt-6 gap-3">
          <div class="max-w-md text-justify">
            Mientras tanto, puedes enviarnos un correo electrónico con tus datos y 
            nos pondremos en contacto contigo para gestionar el alta de tu cuenta:
          </div>
          <ul class="list-disc list-inside">
            <li>Nombre y apellido</li>
            <li>DNI y CUIL</li>
            <li>Email</li>
            <li>Domicilio y Localidad</li>
            <li>Nacionalidad</li>
            <li>Número de celular</li>
          </ul>
          <a href="mailto:bancar.sys.info@gmail.com" class="btn btn-primary w-fit">
            <i data-lucide="mail"></i>
            Enviar correo electrónico
          </a>
        </div>
      </div>  
      <div class="col-span-4">
        <img src="images/account-rafiki.svg" alt="alta de cuenta" class="w-100"/>
      </div>
    </div>
  </div>
</t:masterpage>