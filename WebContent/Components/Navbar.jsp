<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<div class="navbar bg-white drop-shadow-sm">
  <div class="container mx-auto">
    <div class="flex-1">
      <a href="Home.jsp" class="btn btn-ghost font-normal text-xl text-red-600 gap-0">BANC<span class="font-bold">AR</span></a>
    </div>
    <div class="flex-none">
      <ul class="menu menu-horizontal px-1 gap-3">
        <li>
          <a href="Home.jsp" class="btn btn-tertiary bg-base-200">
            Quiero ser cliente
          </a>
        </li>
        <li>
          <a href="${pageContext.request.contextPath}/Login" class="btn btn-primary">Home Banking</a>
        </li>
      </ul>
    </div>
  </div>
</div>