package business.api.exceptions;

public class AlreadyFullTrainingException  extends ApiException{
	
	private static final long serialVersionUID = -1344640670884805385L;

	public static final String DESCRIPTION = "Jugadores completos para el entrenamietno";

	public static final int CODE = 333;
	
	public AlreadyFullTrainingException(){
		this ("");
	}
	
	public AlreadyFullTrainingException(String detail){
		super(DESCRIPTION + ". " + detail, CODE);
	}

}
