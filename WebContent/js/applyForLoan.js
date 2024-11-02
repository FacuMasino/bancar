// Recibe el valor seleccionado del range input
// Y lo muestra en el elemento que tenga el id que
// se pase en el parámetro targetId
const updateValues = (value, targetId) => {
  // Actualiza el valor del texto
  const text = document.getElementById(targetId);
  text.innerHTML = value;
  
  updateSummaryValues();
}

// Actualiza y calcula los valores del "Resúmen del préstamo"
const updateSummaryValues = () => {
  const amountSummaryEl = document.getElementById('amountSummary');
  const quotaSummaryEl = document.getElementById('quotaSummary');
  const actualAmountEl = document.getElementById('requestedAmountInput');
  const actualQuotaEl = document.getElementById('quotaAmountInput');
  const finalQuotaEl = document.getElementById('finalQuota');
  
  amountSummaryEl.innerHTML = actualAmountEl.value;
  quotaSummaryEl.innerHTML = actualQuotaEl.value;
  const finalQuotaValue =  actualAmountEl.value / actualQuotaEl.value;
  finalQuotaEl.innerHTML = finalQuotaValue.toFixed(2);
}

// Al cargar la página calcula y muestra los montos seleccionados por defecto
updateSummaryValues();