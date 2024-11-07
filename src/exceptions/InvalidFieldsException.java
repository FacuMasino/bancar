package exceptions;

import domainModel.InvalidFields;

/**
 * Excepción personalizada que indica errores de validación en uno o varios campos de entrada.
 */
public class InvalidFieldsException extends BusinessException {
	
    // Clase que contiene los nombres de los campos inválidos y sus mensajes de error.
	private InvalidFields fields;
	
	private static final long serialVersionUID = 1L;
	
	public InvalidFieldsException(InvalidFields fields) {
		super("Verifique los campos ingresados");
		this.fields = fields;
	}

	public InvalidFields getInvalidFields() {
		return fields;
	}
}