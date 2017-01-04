package business.api.exceptions;

public class NotFoundConnectionException extends ApiException {
	private static final long serialVersionUID = -1344640670884805385L;

	public static final String DESCRIPTION = "La conexion no existe";

	public static final int CODE = 1;

	public NotFoundConnectionException() {
		this("");
	}

	public NotFoundConnectionException(String detail) {
		super(DESCRIPTION + ". " + detail, CODE);
	}

}
