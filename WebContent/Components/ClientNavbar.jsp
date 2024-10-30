<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<div class="navbar bg-white drop-shadow-sm">
  <div class="container mx-auto">
    <div class="flex-none">
      <div class="drawer">
        <input id="client-drawer" type="checkbox" class="drawer-toggle" />
        <div class="drawer-content">
          <label for="client-drawer" class="btn btn-ghost drawer-button">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              class="inline-block h-5 w-5 stroke-current">
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M4 6h16M4 12h16M4 18h16"></path>
            </svg>
          </label>
        </div>
        <div class="drawer-side text-white">
          <label for="client-drawer" aria-label="close sidebar" class="drawer-overlay"></label>
          <div class="bg-red-600 min-h-full w-80 p-4">
            <!-- Sidebar content here -->
            <div class="flex mb-8 pt-2 ps-2">
              <span class="text-xl font-bold">Menu</span>
            </div>
            <ul class="menu text-lg gap-4">
              <li>
                <a>
                  <i data-lucide="layout-dashboard"></i>
                  Inicio
                </a>
              </li>
              <li>
                <a>
                  <i data-lucide="arrow-left-right"></i>
                  Transferir
                </a>
              </li>
              <li>
                <a>
                  <i data-lucide="wallet-minimal"></i>
                  Préstamos
                </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <div class="flex-1">
      <span class="font-normal text-xl text-red-600">BANC<span class="font-bold">AR</span></span>
    </div>    
    <div class="flex-none">
      <ul class="menu menu-horizontal px-1 gap-3">
        <li>
          <a href="#" class="btn btn-ghost">
            <i data-lucide="user"></i>
            Mi Perfil
          </a>
        </li>
      </ul>
    </div>
  </div>
</div>