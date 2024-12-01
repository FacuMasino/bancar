package exceptions;

/**
 * Excepción personalizada que indica que un cliente no posee cuentas activas.
 */
public class NoActiveAccountsException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public NoActiveAccountsException() {
		super("El cliente no posee cuentas activas");
	}

}