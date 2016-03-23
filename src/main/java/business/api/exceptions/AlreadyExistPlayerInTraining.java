package business.api.exceptions;

public class AlreadyExistPlayerInTraining extends ApiException{
	private static final long serialVersionUID = -1344640670884805385L;

	public static final String DESCRIPTION = "Jugador ya existe en el entrenamiento";

	public static final int CODE = 333;
	
	public AlreadyExistPlayerInTraining(){
		this ("");
	}
	
	public AlreadyExistPlayerInTraining(String detail){
		super(DESCRIPTION + ". " + detail, CODE);
	}
}
