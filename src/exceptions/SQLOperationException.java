package exceptions;

public class SQLOperationException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public SQLOperationException()
	{
		super("Ocurrió un error al realizar la operación en nuestra base de datos.");
	}
	
	public SQLOperationException(String message)
	{
		super("Ocurrió un error al realizar la operación en nuestra base de datos. " + message);
	}

}
