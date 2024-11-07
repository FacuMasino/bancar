package exceptions;

/**
 * Excepción personalizada que indica que un cliente ya existe en el sistema.
 */
public class ClientExistsException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public ClientExistsException(String dni) {
		super("Ya existe un cliente con el DNI " + dni);
	}

}