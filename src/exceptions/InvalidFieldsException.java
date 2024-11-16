package exceptions;

import java.util.List;

/**
 * Excepción personalizada que indica errores de validación en uno o varios campos de entrada.
 */
public class InvalidFieldsException extends BusinessException {
	
    // Clase que contiene los campos inválidos y sus mensajes de error.
	private List<String> fields;
	
	private static final long serialVersionUID = 1L;
	
	public InvalidFieldsException(List<String> fields) {
		super("Verifique los campos ingresados");
		this.fields = fields;
	}

	public List<String> getInvalidFields() {
		return fields;
	}
}