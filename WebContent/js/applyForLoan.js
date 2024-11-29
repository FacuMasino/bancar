// Crea el objeto Intl.NumberFormat inicializado con el formato de moneda local
let ARSCurrency = new Intl.NumberFormat('es-AR', {
  style: 'currency',
  currency: 'ARS',
});

// Recibe el valor seleccionado del range input
// Y lo muestra en el elemento que tenga el id que
// se pase en el parámetro targetId
const updateValues = (value, targetId, isNumeric) => {
  // Actualiza el valor del texto
  const text = document.getElementById(targetId);
  text.innerHTML = isNumeric ? ARSCurrency.format(value) : value;
  
  updateSummaryValues();
}

// Actualiza y calcula los valores del "Resúmen del préstamo"
const updateSummaryValues = () => {
	// Elementos HTML resúmen del préstamo
  const amountSummaryEl = document.getElementById('amountSummary');
  const quotaSummaryEl = document.getElementById('quotaSummary');
  const installmentsCostEl = document.getElementById('installmentsCost');
  const finalCostEl = document.getElementById('finalCost');
  
  // Elementos HTML Inputs
  const reqAmountInput = document.getElementById('requestedAmountInput');
  const installmentsInput = document.getElementById('installmentsInput');
  const interestRateInput = document.getElementById('interestRateInput');
  
  const reqAmountVal = parseInt(reqAmountInput.value); // Monto solicitado
  const installmentsVal = parseInt(installmentsInput.value); // Cantidad de cuotas
  const interestRateVal = parseFloat(interestRateInput.value); // Interés
  
  amountSummaryEl.innerHTML = ARSCurrency.format(reqAmountVal); // Monto solicitado
  quotaSummaryEl.innerHTML = ARSCurrency.format(installmentsVal); // Cantidad de cuotas
  const interestToPay = (((interestRateVal/12) * installmentsVal) / 100.00) * reqAmountVal;
  const finalCost = interestToPay + reqAmountVal;
  const installmentsCostValue =  finalCost / installmentsVal; // Cuota final
  installmentsCostEl.innerHTML = ARSCurrency.format(installmentsCostValue.toFixed(2));
  finalCostEl.innerHTML = ARSCurrency.format(finalCost.toFixed(2));
}

// Al cargar la página calcula y muestra los montos seleccionados por defecto
updateSummaryValues();