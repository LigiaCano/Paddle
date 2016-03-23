package business.api.exceptions;

public class NotFoundPlayerInTraining extends ApiException{
	private static final long serialVersionUID = -1344640670884805385L;

	public static final String DESCRIPTION = "No se encuentra en el entrenamiento el jugador";

	public static final int CODE = 333;
	
	public NotFoundPlayerInTraining(){
		this("");
	}
	
	public NotFoundPlayerInTraining(String detail){
		super(DESCRIPTION + ". " + detail, CODE);
	}
}
