package business.api.exceptions;

public class InvalidTrainingCreateException extends ApiException  {
	
	private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "Creación de entrenamiento invalido";

    public static final int CODE = 1;
    
    public InvalidTrainingCreateException(){
    	this("");
    }

    public InvalidTrainingCreateException(String detail){
    	 super(DESCRIPTION + ". " + detail, CODE);
    }
}
