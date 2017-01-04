package business.api.exceptions;

public class InvalidCodeFieldException extends ApiException{

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "Campo de Code vacio o inexistente";

    public static final int CODE = 1;
    
    public InvalidCodeFieldException(){
    	this("");
    }

	public InvalidCodeFieldException(String detail) {
		   super(DESCRIPTION + ". " + detail, CODE);
	}

}
