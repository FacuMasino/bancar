/**
 * Este archivo JavaScript contiene funciones generales de utilidad
 */

// Agrega la clase 'active' al elemento con el id provisto
// Util para indicar en el sidebar menu cual es el actual
const setActiveMenuItem = (itemId) => {
	let menuItem = document.getElementById(itemId);
	menuItem.classList.add('active');
}

// Copia momentaneamente el contenido del div en el body 
// de la página para abrir la ventana de impresión y realizarla
const printDiv = (divId) => {
    var printContents = document.getElementById(divId).innerHTML;
    var originalContents = document.body.innerHTML;

    document.body.innerHTML = printContents;

    window.print();

    document.body.innerHTML = originalContents;
}