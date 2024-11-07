package exceptions;

/**
 *  Excepción base utilizada para los errores originados en la capa de negocio
 */
public class BusinessException extends Exception{

	private static final long serialVersionUID = 1L;

	public BusinessException(String message) {
		super(message); // Llama al constructor de la clase base Exception
						// Equivalente a hacer new Exception("mensaje");
	}

}
