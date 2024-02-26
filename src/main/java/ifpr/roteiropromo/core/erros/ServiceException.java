package ifpr.roteiropromo.core.erros;

public class ServiceException extends RuntimeException{
    public ServiceException (String message){
        super(message);
    }
}
