// Crea el objeto Intl.NumberFormat inicializado con el formato de moneda local
let ARSCurrency = new Intl.NumberFormat('es-AR', {
  style: 'currency',
  currency: 'ARS',
});

// Formateo de numeros en base a la configuración es-AR
let ARNumber = new Intl.NumberFormat('es-AR');

// Recibe el valor seleccionado del range input
// Y lo muestra en el elemento que tenga el id que
// se pase en el parámetro targetId
const updateValues = (value, targetId, isNumeric) => {
  // Actualiza y formatea el valor del texto
  const text = document.getElementById(targetId);
  text.value = isNumeric ? ARNumber.format(value) : value;
  
  updateSummaryValues();
}

const removeDots = () => {
	const amountInput = document.getElementById('requestedAmount');
	amountInput.value = amountInput.value.replaceAll('.','');
}

const updateRangeInput = (event) => {
	const amountRangeInput = document.getElementById('requestedAmountRange');
	amountRangeInput.value = event.target.value;
	updateSummaryValues();
}

const formatAmountInput = (event) => {
	// TODO: Por ahora no funciona, hace que se triggee el mensaje de "Ingrese un monto válido"
	//event.target.value = ARNumber.format(event.target.value);
}

// Filtrado de inputs en base a una función dada
// Tomado de https://stackoverflow.com/a/469362
const setInputFilter = (textbox, inputFilter, errMsg) => {
  [ "input", "keydown", "keyup", "mousedown", "mouseup", "select", "contextmenu", "drop", "focusout" ].forEach(function(event) {
    textbox.addEventListener(event, function(e) {
      if (inputFilter(this.value)) {
        // Accepted value.
        if ([ "keydown", "mousedown", "focusout" ].indexOf(e.type) >= 0){
          this.classList.remove("input-error");
          this.setCustomValidity("");
        }

        this.oldValue = this.value;
        this.oldSelectionStart = this.selectionStart;
        this.oldSelectionEnd = this.selectionEnd;
      }
      else if (this.hasOwnProperty("oldValue")) {
        // Rejected value: restore the previous one.
        this.classList.add("input-error");
        this.setCustomValidity(errMsg);
        this.reportValidity();
        this.value = this.oldValue;
        this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
      }
      else {
        // Rejected value: nothing to restore.
        this.value = "";
      }
    });
  });
}

// Actualiza y calcula los valores del "Resumen del préstamo"
const updateSummaryValues = () => {
	// Elementos HTML resumen del préstamo
  const amountSummaryEl = document.getElementById('amountSummary');
  const quotaSummaryEl = document.getElementById('quotaSummary');
  const installmentsCostEl = document.getElementById('installmentsCost');
  const finalCostEl = document.getElementById('finalCost');
  
  // Elementos HTML Inputs
  const reqAmountInput = document.getElementById('requestedAmountRange');
  const installmentsInput = document.getElementById('installmentsInput');
  const interestRateInput = document.getElementById('interestRateInput');
  
  const reqAmountVal = parseInt(reqAmountInput.value); // Monto solicitado
  const installmentsVal = parseInt(installmentsInput.value); // Cantidad de cuotas
  const interestRateVal = parseFloat(interestRateInput.value); // Interés
  
  amountSummaryEl.innerHTML = ARSCurrency.format(reqAmountVal); // Monto solicitado
  quotaSummaryEl.innerHTML = installmentsVal; // Cantidad de cuotas
  const interestToPay = (((interestRateVal/12) * installmentsVal) / 100.00) * reqAmountVal;
  const finalCost = interestToPay + reqAmountVal;
  const installmentsCostValue =  finalCost / installmentsVal; // Cuota final
  installmentsCostEl.innerHTML = ARSCurrency.format(installmentsCostValue.toFixed(2));
  finalCostEl.innerHTML = ARSCurrency.format(finalCost.toFixed(2));
}

// Al cargar la página calcula y muestra los montos seleccionados por defecto
updateSummaryValues();

setInputFilter(document.getElementById("requestedAmount"), function(value) {
  return /^\d+(?:[.,]\d*)?$|^\d*$/.test(value); // Solo números, comas y puntos
}, "Ingresa un monto válido");