package business.api.exceptions;

public class AlreadyExistConnectionException extends ApiException {

	private static final long serialVersionUID = -1344640670884805385L;

	public static final String DESCRIPTION = "Ya existe la conexion";

	public static final int CODE = 1;

	public AlreadyExistConnectionException() {
		this("");
	}

	public AlreadyExistConnectionException(String detail) {
		super(DESCRIPTION + ". " + detail, CODE);
	}

}
