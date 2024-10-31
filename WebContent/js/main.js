/**
 * Este archivo JavaScript contiene funciones generales de utilidad
 */

// Agrega la clase 'active' al elemento con el id provisto
// Util para indicar en el sidebar menu cual es el actual
const setActiveMenuItem = (itemId) => {
	let menuItem = document.getElementById(itemId);
	menuItem.classList.add('active');
}